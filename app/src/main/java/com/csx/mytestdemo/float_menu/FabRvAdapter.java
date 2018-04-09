package com.csx.mytestdemo.float_menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csx.mytestdemo.R;

import java.util.List;

/**
 * @Created by cuishuxiang
 * @date 2018/4/9.
 * @description:
 */

public class FabRvAdapter extends RecyclerView.Adapter<FabRvAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mStringList;

    public FabRvAdapter(Context context, List<String> stringList) {
        mContext = context;
        mStringList = stringList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder = null;

        View view = null;

        if (viewHolder == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_string, parent, false);

            viewHolder = new ViewHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item_tv.setText(mStringList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStringList == null ? 0 : mStringList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_tv;

        public ViewHolder(View itemView) {
            super(itemView);

            item_tv = itemView.findViewById(R.id.item_tv);
        }
    }
}

