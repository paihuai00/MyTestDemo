package com.csx.mytestdemo.my_butterknife.ioc;

import android.app.Activity;
import android.view.View; /**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/9
 * @description: View 的 findViewById 辅助类
 */
public class ViewFinder {
    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId) {

        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
