package com.fladimir.yujungong.module.audio;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fladimir.jutils.ui.toast.Toasty;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NingJiang on 2017/6/29.
 * Class Note:
 */

public class FileActivity extends AppCompatActivity {
    Button holdButtom;
    TextView audioInfo;
    LinearLayout mLayout;
    ExecutorService mService;
    MediaRecorder mRecorder;
    File mAudioFile;
    long startTime;
    long stopTime;
    Handler mMainThreadHandler;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUi();
    }

    private void setUi() {
        //录音JNI函数不具备线程安全性，所以要用单线程
        mService = Executors.newSingleThreadExecutor();
        mMainThreadHandler = new Handler(Looper.getMainLooper());

        mLayout = new LinearLayout(this);
        mLayout.setOrientation(LinearLayout.VERTICAL);

        holdButtom = new Button(this);
        holdButtom.setText("Hold To Speak");

        audioInfo = new TextView(this);
        audioInfo.setText("~~~~~");

        mLayout.addView(holdButtom);
        mLayout.addView(audioInfo);

        setContentView(mLayout);

        holdButtom.setOnTouchListener((view, motionEvent) -> {
            //TouchAction
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    holding();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    cancle();
                    break;
                default:
            }
            //触发Touch事件
            return true;
        });
    }

    /**
     * 按住说话
     */
    private void holding() {
        holdButtom.setText("Speaking...");

        mService.submit(new Runnable() {
            @Override
            public void run() {
                //释放之前录音的recorder
                releaseRecorder();
                //执行录音，失败要提示
                if (!doStart()) {
                    recordFail();
                }
            }
        });
    }

    /**
     * 取消录音
     */
    private void cancle() {
        holdButtom.setText("Hold To Speak");

        mService.submit(new Runnable() {
            @Override
            public void run() {
                //停止录音，失败提示
                if (!doStop()) {
                    recordFail();
                }
                releaseRecorder();
            }


        });
    }

    //释放录音文件MediaRecord
    private void releaseRecorder() {
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    //启动录音逻辑
    private boolean doStart() {
        try {
            //创建MediaRecorder
            mRecorder = new MediaRecorder();
            //创建录音文件
            mAudioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/YuJun/" + System.currentTimeMillis() + ".m4a");
            mAudioFile.getParentFile().mkdir();
            mAudioFile.createNewFile();

            //配置 MediaRecorder
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//使用麦克风
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//保存格式
            mRecorder.setAudioSamplingRate(44100);//采样频率
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//AAC编码
            mRecorder.setAudioEncodingBitRate(96000);//频率(音质)
            mRecorder.setOutputFile(mAudioFile.getAbsolutePath());//设置录音位置
            //开始录音
            mRecorder.prepare();
            mRecorder.start();
            //记录时间
            startTime = System.currentTimeMillis();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            //录音失败 返回false
            return false;
        }
        return true;
    }

    //停止录音
    private boolean doStop() {
        //停止
        try {
            mRecorder.stop();
            //时间
            stopTime = System.currentTimeMillis();
            int time = (int) ((stopTime - startTime) / 1000);
            if (time > 3) {
                mMainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        audioInfo.setText(audioInfo.getText() + "\n录音成功" + time + "秒");
                    }
                });
            }
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    /**
     * 出现错误
     */
    private void recordFail() {
        mAudioFile = null;
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.nomal(FileActivity.this, "录音失败!");
            }
        });*/
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                holdButtom.setText("Hold To Speak");
                Toasty.nomal(FileActivity.this, "录音失败!");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止后台业务
        mService.shutdownNow();
        releaseRecorder();
    }
}
