package com.csx.mytestdemo.loading_view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csx.mytestdemo.R;

/**
 * @Created by cuishuxiang
 * @date 2018/4/27.
 * @description: 加载动画
 */

public class LoadingUtils {
    public static Dialog loadingDialog;

    /**
     * 仿微博加载loading
     * @param context
     * @param tipString  如果为null 或者 "" 则提示信息为  加载中.....
     * @return
     */
    private static Dialog createLoadingDialog(Context context, String tipString) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.dialog_loading_view);// 加载布局
        if (!TextUtils.isEmpty(tipString)) {
            TextView tipTextView = (TextView) view.findViewById(R.id.tipTextView);// 提示文字
            tipTextView.setText(tipString);// 设置加载信息
        }


        //设置自己的弹框style
        Dialog loadingDialog = new Dialog(context, R.style.LoadingStyle);

        loadingDialog.setCancelable(true);//可以取消
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);

        return loadingDialog;
    }

    /**
     * 显示动画
     * @param context
     */
    public static void showLoading(Context context) {
        if (loadingDialog == null) {
            loadingDialog = createLoadingDialog(context, "");
        }
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 显示动画
     * @param context
     * @param tipString
     */
    public static void showLoading(Context context, String tipString) {
        if (loadingDialog == null) {
            loadingDialog = createLoadingDialog(context, tipString);
        }
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 停止动画
     */
    public static void stopLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.hide();
        }
    }

}
