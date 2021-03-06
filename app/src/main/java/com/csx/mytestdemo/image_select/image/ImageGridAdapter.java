package com.csx.mytestdemo.image_select.image;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.csx.mytestdemo.R;
import com.csx.mytestdemo.glide4.GlideApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片Adapter
 * Created by lixu on 2017/1/25.
 */
public class ImageGridAdapter extends BaseAdapter {

    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_NORMAL = 1;
    final int mGridWidth;
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean showCamera = true;
    private boolean showSelectIndicator = true;
    private List<ImageSelectorImage> mImageSelectorImages = new ArrayList<>();
    private List<ImageSelectorImage> mSelectedImageSelectorImages = new ArrayList<>();

    public ImageGridAdapter(Context context, boolean showCamera, int column) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showCamera = showCamera;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            width = size.x;
        } else {
            width = wm.getDefaultDisplay().getWidth();
        }
        mGridWidth = width / column;
    }

    /**
     * 显示选择指示器
     *
     * @param b
     */
    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    public void setShowCamera(boolean b) {
        if (showCamera == b) return;

        showCamera = b;
        notifyDataSetChanged();
    }

    /**
     * 选择某个图片，改变选择状态
     *
     * @param imageSelectorImage
     */
    public void select(ImageSelectorImage imageSelectorImage) {
        if (mSelectedImageSelectorImages.contains(imageSelectorImage)) {
            mSelectedImageSelectorImages.remove(imageSelectorImage);
        } else {
            mSelectedImageSelectorImages.add(imageSelectorImage);
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     *
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        for (String path : resultList) {
            ImageSelectorImage imageSelectorImage = getImageByPath(path);
            if (imageSelectorImage != null) {
                mSelectedImageSelectorImages.add(imageSelectorImage);
            }
        }
        if (mSelectedImageSelectorImages.size() > 0) {
            notifyDataSetChanged();
        }
    }

    private ImageSelectorImage getImageByPath(String path) {
        if (mImageSelectorImages != null && mImageSelectorImages.size() > 0) {
            for (ImageSelectorImage imageSelectorImage : mImageSelectorImages) {
                if (imageSelectorImage.path.equalsIgnoreCase(path)) {
                    return imageSelectorImage;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据集
     *
     * @param imageSelectorImages
     */
    public void setData(List<ImageSelectorImage> imageSelectorImages) {
        mSelectedImageSelectorImages.clear();

        if (imageSelectorImages != null && imageSelectorImages.size() > 0) {
            mImageSelectorImages = imageSelectorImages;
        } else {
            mImageSelectorImages.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (showCamera) {
            return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getCount() {
        return showCamera ? mImageSelectorImages.size() + 1 : mImageSelectorImages.size();
    }

    @Override
    public ImageSelectorImage getItem(int i) {
        if (showCamera) {
            if (i == 0) {
                return null;
            }
            return mImageSelectorImages.get(i - 1);
        } else {
            return mImageSelectorImages.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (isShowCamera()) {
            if (i == 0) {
                view = mInflater.inflate(R.layout.list_item_camera, viewGroup, false);
                return view;
            }
        }

        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item_image, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (holder != null) {
            holder.bindData(getItem(i));
        }

        return view;
    }

    class ViewHolder {
        ImageView image;
        ImageView indicator;
        View mask;

        ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.imageSelectorImage);
            indicator = (ImageView) view.findViewById(R.id.checkmark);
            mask = view.findViewById(R.id.mask);
            view.setTag(this);
        }

        void bindData(final ImageSelectorImage data) {
            if (data == null) return;
            // 处理单选和多选状态
            if (showSelectIndicator) {
                indicator.setVisibility(View.VISIBLE);
                if (mSelectedImageSelectorImages.contains(data)) {
                    // 设置选中状态
                    indicator.setImageResource(R.drawable.btn_selected);
                    mask.setVisibility(View.VISIBLE);
                } else {
                    // 未选择
                    indicator.setImageResource(R.drawable.btn_unselected);
                    mask.setVisibility(View.GONE);
                }
            } else {
                indicator.setVisibility(View.GONE);
            }
            File imageFile = new File(data.path);
            if (imageFile.exists()) {
                // 显示图片
                GlideApp.with(mContext)
                        .load(imageFile)
                        .error(R.drawable.default_error)
                        .placeholder(R.drawable.default_error)
                        .into(image);
            } else {
                image.setImageResource(R.drawable.default_error);
            }
        }
    }

}
