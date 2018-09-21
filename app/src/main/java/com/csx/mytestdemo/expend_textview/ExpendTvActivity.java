package com.csx.mytestdemo.expend_textview;

import android.widget.Toast;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/9/20
 * @description:
 */
public class ExpendTvActivity extends BaseActivity {


    @BindView(R.id.expend_etv)
    ExpandableTextView mExpendEtv;
    String yourText = "  我所认识的中国，强大、友好。@奥特曼 “一带一路”经济带带动了沿线国家的经济发展，促进我国与他国的友好往来和贸易发展，可谓“双赢”。http://www.baidu.com 自古以来，中国以和平、友好的面孔示人。汉武帝派张骞出使西域，开辟丝绸之路，增进与西域各国的友好往来。http://www.baidu.com 胡麻、胡豆、香料等食材也随之传入中国，汇集于中华美食。@RNG 漠漠古道，驼铃阵阵，这条路奠定了“一带一路”的基础，让世界认识了中国。";

    private ExpandableTextView.OnLinkClickListener linkClickListener = (type, content) -> {
        if (type.equals(ExpandableTextView.LinkType.LINK_TYPE)) {
            Toast.makeText(ExpendTvActivity.this, "你点击了链接 内容是：" + content, Toast.LENGTH_SHORT).show();
        } else if (type.equals(ExpandableTextView.LinkType.MENTION_TYPE)) {
            Toast.makeText(ExpendTvActivity.this, "你点击了@用户 内容是：" + content, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_expend_tv;
    }

    @Override
    public void initView() {
        mExpendEtv.setContent(yourText);
        mExpendEtv.setLinkClickListener(linkClickListener);
        mExpendEtv.setEndExpendContent("一小时以前");
    }

    @Override
    public void initData() {

    }
}
