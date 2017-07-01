package com.fladimir.yujungong.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.InflateException;

import com.fladimir.jutils.tools.ActivityTools;

/**
 * Created by NingJiang on 2017/6/23.
 * Class Note:
 */

public abstract class BaseBindActivity<VB extends ViewDataBinding> extends AppCompatActivity {
    protected VB mBinding;
    protected Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());

        if (mBinding == null) {
            if (getLayoutId() == 0) {
                throw new InflateException("布局没填充");
            } else {
                throw new NullPointerException("mBinding 不能为空");
            }
        }
        initView();
        ActivityTools.getInstance().addActivity(this);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityTools.getInstance().removeActivity(this);
    }
}
