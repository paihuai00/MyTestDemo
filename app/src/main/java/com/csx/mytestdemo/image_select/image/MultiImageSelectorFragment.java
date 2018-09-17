package com.csx.mytestdemo.image_select.image;


import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;


import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.csx.mytestdemo.R;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 图片选择器fragment
 * Created by lixu on 2017/1/25.
 *  返回的是 ArrayList<String> imageLists = (ArrayList<String>) data.getSerializableExtra("select_result");
 */

public class MultiImageSelectorFragment extends AppCompatActivity {

    public static final String TAG = "MultiImageSelectorFragment";

    private static final String KEY_TEMP_FILE = "key_temp_file";
    /**
     * 最大图片选择次数，int类型
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择模式，int类型
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 是否显示相机，boolean类型
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 选择结果，返回为 ArrayList<String> 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择的数据集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_result";
    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;
    // 不同loader定义
    private static final int LOADER_ALL = 0;
    private static final int LOADER_CATEGORY = 1;
    // 请求加载系统照相机
    private static final int REQUEST_CAMERA = 100;

    // 结果数据
    private ArrayList<String> resultList = new ArrayList<>();
    // 文件夹数据
    private ArrayList<ImageSelectorFolder> mResultImageSelectorFolder = new ArrayList<>();

    // 图片Grid
    @BindView(R.id.grid)
    GridView mGridView;

    private ImageGridAdapter mImageAdapter;
    private FolderAdapter mFolderAdapter;

    private ListPopupWindow mFolderPopupWindow;

    private int mMode;
    private int resultCode;

    // 头部View
    @BindView(R.id.bar)
    RelativeLayout mRlBar;
    @BindView(R.id.ll_popup)
    LinearLayout mLlPopup;
    @BindView(R.id.tv_folder)
    TextView mTvFolder;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.tv_finish)
    TextView mTvFinish;
    @BindView(R.id.iv_back)
    ImageView mIvBack;

    private Unbinder mUnbinder;

    private int mDesireImageCount;
    private boolean hasFolderGened = false;
    private boolean mIsShowCamera = false;

    private File mTmpFile;

    public static void openForResult(Activity activity, int maxSelectCount, int resultCode, int mode, boolean isShow, ArrayList<String> resultList) {
        Intent mIntent = new Intent(activity, MultiImageSelectorFragment.class);
        mIntent.putExtra("resultCode", resultCode);
        mIntent.putExtra(EXTRA_SELECT_COUNT, maxSelectCount);
        mIntent.putExtra(EXTRA_SELECT_MODE, mode);
        mIntent.putExtra(EXTRA_SHOW_CAMERA, isShow);
        mIntent.putExtra(EXTRA_DEFAULT_SELECTED_LIST, resultList);
        activity.startActivityForResult(mIntent, resultCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_multi_image);
        mUnbinder = ButterKnife.bind(this);
        // 选择图片数量
        mDesireImageCount = getIntent().getIntExtra(EXTRA_SELECT_COUNT, 9);
        // 图片选择模式
        mMode = getIntent().getIntExtra(EXTRA_SELECT_MODE, 0);
        // 默认选择
        if (mMode == MODE_MULTI) {
            ArrayList<String> tmp = (ArrayList<String>) getIntent().getSerializableExtra(EXTRA_DEFAULT_SELECTED_LIST);
            if (tmp != null && tmp.size() > 0) {
                resultList = tmp;
            }
        }
        // 是否显示照相机
        mIsShowCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        resultCode = getIntent().getIntExtra("resultCode", 13579);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mImageAdapter = new ImageGridAdapter(this, mIsShowCamera, 4);
        // 是否显示选择指示器
        mImageAdapter.showSelectIndicator(mMode == MODE_MULTI);

        // 初始化，加载所有图片
        mTvFolder.setText("相机胶卷");
        mLlPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFolderPopupWindow == null) {
                    createPopupFolderList();
                }
                if (mFolderPopupWindow.isShowing()) {
                    mFolderPopupWindow.dismiss();
                } else {
                    mIvArrow.animate().rotationX(180f).setDuration(500).start();
                    mFolderPopupWindow.show();
                    int index = mFolderAdapter.getSelectIndex();
                    index = index == 0 ? index : index - 1;
                    mFolderPopupWindow.getListView().setSelection(index);
                }
            }
        });

        mGridView.setAdapter(mImageAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mImageAdapter.isShowCamera()) {
                    // 如果显示照相机，则第一个Grid显示为照相机，处理特殊逻辑
                    if (i == 0) {
                        showCameraAction();
                    } else {
                        // 正常操作
                        ImageSelectorImage imageSelectorImage = (ImageSelectorImage) adapterView.getAdapter().getItem(i);
                        selectImageFromGrid(imageSelectorImage, mMode);
                    }
                } else {
                    // 正常操作
                    ImageSelectorImage imageSelectorImage = (ImageSelectorImage) adapterView.getAdapter().getItem(i);
                    selectImageFromGrid(imageSelectorImage, mMode);
                }
            }
        });

        mFolderAdapter = new FolderAdapter(this);

        mTvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                MultiImageSelectorFragment.this.setResult(resultCode, data);
                finish();
            }
        });


        // 首次加载所有图片
        getLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_TEMP_FILE, mTmpFile);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 相机拍照完成后，返回图片路径
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                if (mTmpFile != null) {
                    onCameraShot(mTmpFile);
                }
            } else {
                while (mTmpFile != null && mTmpFile.exists()) {
                    boolean success = mTmpFile.delete();
                    if (success) {
                        mTmpFile = null;
                    }
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (mFolderPopupWindow != null) {
            if (mFolderPopupWindow.isShowing()) {
                mFolderPopupWindow.dismiss();
            }
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 创建弹出的ListView
     */
    private void createPopupFolderList() {
        Point point = getScreenSize(this);
        int width = point.x;
        int height = (int) (point.y * (4.5f / 8.0f));
        mFolderPopupWindow = new ListPopupWindow(this);
        mFolderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mFolderPopupWindow.setAdapter(mFolderAdapter);
        mFolderPopupWindow.setContentWidth(width);
        mFolderPopupWindow.setWidth(width);
        mFolderPopupWindow.setHeight(height);
        mFolderPopupWindow.setAnchorView(mRlBar);
        mFolderPopupWindow.setModal(true);
        mFolderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mFolderAdapter.setSelectIndex(i);

                final int index = i;
                final AdapterView v = adapterView;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFolderPopupWindow.dismiss();

                        if (index == 0) {
                            MultiImageSelectorFragment.this.getLoaderManager().restartLoader(LOADER_ALL, null,  mLoaderCallback);
                            mTvFolder.setText("相机胶卷");
                            if (mIsShowCamera) {
                                mImageAdapter.setShowCamera(true);
                            } else {
                                mImageAdapter.setShowCamera(false);
                            }
                        } else {
                            ImageSelectorFolder imageSelectorFolder = (ImageSelectorFolder) v.getAdapter().getItem(index);
                            if (null != imageSelectorFolder) {
                                mImageAdapter.setData(imageSelectorFolder.imageSelectorImages);
                                mTvFolder.setText(imageSelectorFolder.name);
                                // 设定默认选择
                                if (resultList != null && resultList.size() > 0) {
                                    mImageAdapter.setDefaultSelected(resultList);
                                }
                            }
                            mImageAdapter.setShowCamera(false);
                        }

                        // 滑动到最初始位置
                        mGridView.smoothScrollToPosition(0);
                    }
                }, 100);

            }
        });
        mFolderPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvArrow.animate().rotationX(0f).setDuration(500).start();
            }
        });
    }

    /**
     * 选择相机
     */
    private void showCameraAction() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            try {
                mTmpFile = FileUtils.createTmpFile(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Uri photouri = FileProvider.getUriForFile(getContext(), "com.xyauto.carcenter.fileProvider", mTmpFile);
            if (mTmpFile != null && mTmpFile.exists()) {
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
//                startActivityForResult(cameraIntent, REQUEST_CAMERA);
                /*获取当前系统的android版本号*/
                int currentapiVersion = Build.VERSION.SDK_INT;
                if (currentapiVersion < 24) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                    startActivityForResult(cameraIntent, REQUEST_CAMERA);
                } else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, mTmpFile.getAbsolutePath());
                    Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(cameraIntent, REQUEST_CAMERA);
                }
            } else {
                Toast.makeText(this, "图片错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "没有系统相机", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 选择图片操作
     *
     * @param imageSelectorImage
     */
    private void selectImageFromGrid(ImageSelectorImage imageSelectorImage, int mode) {
        if (imageSelectorImage != null) {
            // 多选模式
            if (mode == MODE_MULTI) {
                if (resultList.contains(imageSelectorImage.path)) {
                    resultList.remove(imageSelectorImage.path);
                    onImageUnselected(imageSelectorImage.path);
                } else {
                    // 判断选择数量问题
                    if (mDesireImageCount == resultList.size()) {
                        Toast.makeText(this, "已经达到最高选择数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    resultList.add(imageSelectorImage.path);
                    onImageSelected(imageSelectorImage.path);
                }
                mImageAdapter.select(imageSelectorImage);
            } else if (mode == MODE_SINGLE) {
                // 单选模式
                onSingleImageSelected(imageSelectorImage.path);
            }
        }
    }

    // 读取照片
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};

                @Override
                public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    if (id == LOADER_ALL) {
                        CursorLoader cursorLoader = new CursorLoader(MultiImageSelectorFragment.this,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                                IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR " + IMAGE_PROJECTION[3] + "=? ",
                                new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
                        return cursorLoader;
                    } else if (id == LOADER_CATEGORY) {
                        CursorLoader cursorLoader = new CursorLoader(MultiImageSelectorFragment.this,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                                IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'",
                                null, IMAGE_PROJECTION[2] + " DESC");
                        return cursorLoader;
                    }

                    return null;
                }

        @Override
        public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                if (data.getCount() > 0) {
                    List<ImageSelectorImage> imageSelectorImages = new ArrayList<>();
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        ImageSelectorImage imageSelectorImage = null;
                        if (fileExist(path)) {
                            imageSelectorImage = new ImageSelectorImage(path, name, dateTime);
                            imageSelectorImages.add(imageSelectorImage);
                        }
                        if (!hasFolderGened) {
                            // 获取文件夹名称
                            File folderFile = new File(path).getParentFile();
                            if (folderFile != null && folderFile.exists()) {
                                String fp = folderFile.getAbsolutePath();
                                ImageSelectorFolder f = getFolderByPath(fp);
                                if (f == null) {
                                    ImageSelectorFolder imageSelectorFolder = new ImageSelectorFolder();
                                    imageSelectorFolder.name = folderFile.getName();
                                    imageSelectorFolder.path = fp;
                                    imageSelectorFolder.cover = imageSelectorImage;
                                    List<ImageSelectorImage> imageSelectorImageList = new ArrayList<>();
                                    imageSelectorImageList.add(imageSelectorImage);
                                    imageSelectorFolder.imageSelectorImages = imageSelectorImageList;
                                    mResultImageSelectorFolder.add(imageSelectorFolder);
                                } else {
                                    f.imageSelectorImages.add(imageSelectorImage);
                                }
                            }
                        }

                    } while (data.moveToNext());

                    mImageAdapter.setData(imageSelectorImages);
                    // 设定默认选择
                    if (resultList != null && resultList.size() > 0) {
                        mImageAdapter.setDefaultSelected(resultList);
                    }

                    if (!hasFolderGened) {
                        mFolderAdapter.setData(mResultImageSelectorFolder);
                        hasFolderGened = true;
                    }

                }
            }
        }

        @Override
        public void onLoaderReset(android.content.Loader<Cursor> loader) {

        }

        private boolean fileExist(String path) {
            if (!TextUtils.isEmpty(path)) {
                return new File(path).exists();
            }
            return false;
        }


    };

    private ImageSelectorFolder getFolderByPath(String path) {
        if (mResultImageSelectorFolder != null) {
            for (ImageSelectorFolder imageSelectorFolder : mResultImageSelectorFolder) {
                if (TextUtils.equals(imageSelectorFolder.path, path)) {
                    return imageSelectorFolder;
                }
            }
        }
        return null;
    }

    private void onCameraShot(File imageFile) {
        if (imageFile != null) {
            // notify system
            MultiImageSelectorFragment.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            MultiImageSelectorFragment.this.setResult(resultCode, data);
            finish();
        }
    }

    private void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        MultiImageSelectorFragment.this.setResult(resultCode, data);
        finish();
    }

    private void onImageSelected(String path) {
        if (!resultList.contains(path)) {
            resultList.add(path);
        }
        // 有图片之后，改变按钮状态
        if (resultList.size() > 0) {
            updateDoneText();
            if (!mTvFinish.isEnabled()) {
                mTvFinish.setEnabled(true);
            }
        }
    }

    private void onImageUnselected(String path) {
        if (resultList.contains(path)) {
            resultList.remove(path);
        }
        updateDoneText();
        // 当为选择图片时候的状态
        if (resultList.size() == 0) {
            mTvFinish.setText("完成");
            mTvFinish.setEnabled(false);
        }
    }

    private void updateDoneText() {
        mTvFinish.setText(String.format("%s(%d/%d)",
                "完成", resultList.size(), mDesireImageCount));
    }

    private Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point out = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(out);
        } else {
            int width = display.getWidth();
            int height = display.getHeight();
            out.set(width, height);
        }
        return out;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }
}
