package com.fladimir.yujungong.module.audio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by NingJiang on 2017/6/29.
 * Class Note:
 */

public class AutioMainActivity extends AppCompatActivity {
    Button fileStyle;
    Button StreamStyle;
    LinearLayout mLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUi();
    }

    private void setUi() {
        mLayout = new LinearLayout(this);
        mLayout.setOrientation(LinearLayout.VERTICAL);

        fileStyle = new Button(this);
        fileStyle.setText("文件模式");

        StreamStyle = new Button(this);
        StreamStyle.setText("字节流模式");

        mLayout.addView(fileStyle);
        mLayout.addView(StreamStyle);

        setContentView(mLayout);

        fileStyle.setOnClickListener(view -> startActivity(new Intent(this,FileActivity.class)));
        StreamStyle.setOnClickListener(view -> startActivity(new Intent(this,StreamActivity.class)));
    }
}
