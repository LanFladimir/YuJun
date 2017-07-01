package com.fladimir.yujungong.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fladimir.jutils.tools.ActivityTools;

/**
 * Created by NingJiang on 2017/6/23.
 * Class Note:基类Activity
 */

public class BaseActivity extends AppCompatActivity {
    public ViewDataBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTools.getInstance().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityTools.getInstance().removeActivity(this);
    }
}
