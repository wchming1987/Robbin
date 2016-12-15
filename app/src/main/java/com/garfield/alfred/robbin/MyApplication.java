package com.garfield.alfred.robbin;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.Setting;


/**
 * Created by wangcm on 2016/11/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {

        Fresco.initialize(getApplicationContext());

        // 预先申请的APPID，申请地址：http://open.voicecloud.cn
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=58329319");
        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);

        super.onCreate();
    }
}


