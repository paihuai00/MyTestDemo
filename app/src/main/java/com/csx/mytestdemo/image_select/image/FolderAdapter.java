package com.csx.mytestdemo.image_select.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.csx.mytestdemo.R;
import com.csx.mytestdemo.glide4.GlideApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹Adapter
 * Created by lixu on 2017/1/25.
 */
public class FolderAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<ImageSelectorFolder> mImageSelectorFolders = new ArrayList<>();

    int mImageSize;

    int lastSelected = 0;

    public FolderAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.margin_brand);
    }

    /**
     * 设置数据集
     *
     * @param imageSelectorFolders
     */
    public void setData(List<ImageSelectorFolder> imageSelectorFolders) {
        if (imageSelectorFolders != null && imageSelectorFolders.size() > 0) {
            mImageSelectorFolders = imageSelectorFolders;
        } else {
            mImageSelectorFolders.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mImageSelectorFolders.size() + 1;
    }

    @Override
    public ImageSelectorFolder getItem(int i) {
        if (i == 0) return null;
        return mImageSelectorFolders.get(i - 1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item_folder, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (holder != null) {
            if (i == 0) {
                holder.name.setText("所有照片");
                holder.path.setText("/sdcard");
                holder.size.setText(String.format("%d%s",
                        getTotalImageSize(), "张"));
                if (mImageSelectorFolders.size() > 0) {
                    ImageSelectorFolder f = mImageSelectorFolders.get(0);
                    GlideApp.with(mContext)
                            .load(new File(f.cover.path))
                            .error(R.drawable.default_error)
                            .into(holder.cover);
                }
            } else {
                holder.bindData(getItem(i));
            }
            if (lastSelected == i) {
                holder.indicator.setVisibility(View.VISIBLE);
            } else {
                holder.indicator.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    private int getTotalImageSize() {
        int result = 0;
        if (mImageSelectorFolders != null && mImageSelectorFolders.size() > 0) {
            for (ImageSelectorFolder f : mImageSelectorFolders) {
                result += f.imageSelectorImages.size();
            }
        }
        return result;
    }

    public void setSelectIndex(int i) {
        if (lastSelected == i) return;

        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return lastSelected;
    }

    class ViewHolder {
        ImageView cover;
        TextView name;
        TextView path;
        TextView size;
        ImageView indicator;

        ViewHolder(View view) {
            cover = (ImageView) view.findViewById(R.id.cover);
            name = (TextView) view.findViewById(R.id.name);
            path = (TextView) view.findViewById(R.id.path);
            size = (TextView) view.findViewById(R.id.size);
            indicator = (ImageView) view.findViewById(R.id.indicator);
            view.setTag(this);
        }

        void bindData(ImageSelectorFolder data) {
            if (data == null) {
                return;
            }
            name.setText(data.name);
            path.setText(data.path);
            if (data.imageSelectorImages != null) {
                size.setText(String.format("%d%s", data.imageSelectorImages.size(), "张"));
            } else {
                size.setText("*" + "张");
            }
            // 显示图片
            if (data.cover != null) {
                GlideApp.with(mContext)
                        .load(new File(data.cover.path))
                        .error(R.drawable.default_error)
                        .placeholder(R.drawable.default_error)
                        .into(cover);
            } else {
                cover.setImageResource(R.drawable.default_error);
            }
        }
    }

}
