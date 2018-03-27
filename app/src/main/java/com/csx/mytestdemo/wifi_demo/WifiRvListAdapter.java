package com.csx.mytestdemo.wifi_demo;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csx.mytestdemo.R;
import com.csx.mytestdemo.wifi_demo.wifi_utils.WifiInfoBean;

import java.util.List;

/**
 * @author cuishuxiang
 * @date 2017/12/21.
 *
 * wifi 列表adapter
 */

public class WifiRvListAdapter extends RecyclerView.Adapter<WifiRvListAdapter.ViewHolder> {

    private Context context;
    private List<WifiInfoBean> mWifiInfoBeanList;
    private WifiInfo connectedWifiInfo;

    public void setConnectWifiInfo(WifiInfo wifiInfo) {
        this.connectedWifiInfo = wifiInfo;
        notifyDataSetChanged();
    }

    public WifiRvListAdapter(Context context, List<WifiInfoBean> wifiInfoBeans) {
        this.context = context;
        this.mWifiInfoBeanList = wifiInfoBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        View view = null;
        if (view == null || viewHolder == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_wifi_list, parent, false);
            viewHolder = new ViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScanResult scanResult = mWifiInfoBeanList.get(position).getScanResult();
        int security_type = mWifiInfoBeanList.get(position).getSecurityType();
        int level = mWifiInfoBeanList.get(position).getLevel();

        holder.wifi_name.setText(scanResult.SSID);

        //首先判断加密方式   0 为没有密码
        if (0 == security_type) {
            switch (level) {
                case 0:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_1);
                    break;
                case 1:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_1);
                    break;
                case 2:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_2);
                    break;
                case 3:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_3);
                    break;
                case 4:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_4);
                    break;
            }
        }

        //有密码的情况
        if (0 != security_type) {
            switch (level) {
                case 0:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_lock0);
                    break;
                case 1:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_lock1);
                    break;
                case 2:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_lock2);
                    break;
                case 3:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_lock3);
                    break;
                case 4:
                    holder.wifi_img.setImageResource(R.drawable.ic_wifi_lock3);
                    break;
            }
        }

        if (connectedWifiInfo != null && connectedWifiInfo.getSSID().equals("\""+scanResult.SSID+"\"")) {
            holder.connect_state.setText("已连接");
        }else {
            holder.connect_state.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mWifiInfoBeanList == null ? 0 : mWifiInfoBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView wifi_img;
        TextView wifi_name,connect_state;
        public ViewHolder(View itemView) {
            super(itemView);
            wifi_img = itemView.findViewById(R.id.wifi_img);
            wifi_name = itemView.findViewById(R.id.wifi_name);
            connect_state=itemView.findViewById(R.id.connect_state);
            if (onRvItemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRvItemClickListener.onItemClickListener(v, getAdapterPosition());
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onRvItemClickListener.onLongItemClickListener(view, getAdapterPosition());
                        return false;
                    }
                });
            }

        }
    }

    private OnRvItemClickListener onRvItemClickListener;

    public void setOnRvItemClickListener(OnRvItemClickListener onRvItemClickListener) {
        this.onRvItemClickListener = onRvItemClickListener;
    }

    interface OnRvItemClickListener {
        void onItemClickListener(View view, int position);

        void onLongItemClickListener(View view, int position);
    }
}

