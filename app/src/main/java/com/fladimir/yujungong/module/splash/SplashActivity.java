package com.fladimir.yujungong.module.splash;

import android.content.Intent;
import android.graphics.Typeface;

import com.fladimir.jutils.tools.SystyemTools;
import com.fladimir.yujungong.R;
import com.fladimir.yujungong.base.BaseBindActivity;
import com.fladimir.yujungong.databinding.ActivitySplashBinding;
import com.fladimir.yujungong.module.audio.AutioMainActivity;

/**
 * Created by NingJiang on 2017/6/23.
 * Class Note:SplashActivity
 */

public class SplashActivity extends BaseBindActivity<ActivitySplashBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mBinding.splashAppname.setText(
                R.string.app_name + "\n" + SystyemTools.getVersionName(this));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/逐浪硬行体.otf");

        mBinding.splashAppname.setTypeface(tf);
        mBinding.splashTx.setTypeface(tf);
        mBinding.splashAppname.setText(
                getString(R.string.app_name) + "  v." + SystyemTools.getVersionName(this));

        mBinding.splashAppname.postDelayed(() ->
                startActivity(new Intent(SplashActivity.this, AutioMainActivity.class)), 1500);
                //startActivity(new Intent(SplashActivity.this, MainActivity.class)), 1500);

    }
}
