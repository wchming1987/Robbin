package com.garfield.alfred.robbin.iflytek.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;


/**
 * 语义理解组件
 *
 * Created by 汪长鸣 on 2016/12/15.
 */

public class UnderstandUtil {

    public static void understand(final Context context) {
        // 1.创建语音语义理解对象
        SpeechUnderstander understander = SpeechUnderstander.createUnderstander(context, new InitListener() {
            // 初始化对象
            @Override
            public void onInit(int code) {
                Log.d("DEBUG", "speechUnderstanderListener init() code = " + code);
                if (code != ErrorCode.SUCCESS) {
                    Log.d("ERROR", "初始化失败,错误码：" + code);
                }
            }
        });

        // 2.设置参数，语义场景设置请登录 http://osp.voicecloud.cn/

        // 设置语言及语言区域
        understander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        understander.setParameter(SpeechConstant.ACCENT, "zh_cn");
        /*String lang = mSharedPreferences.getString("understander_language_preference", "mandarin");
        if (lang.equals("en_us")) {
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "en_us");
        }else {
            mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            mSpeechUnderstander.setParameter(SpeechConstant.ACCENT, lang);
        }*/
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        understander.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        understander.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号，默认：1（有标点）
        understander.setParameter(SpeechConstant.ASR_PTT, "1");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        //understander.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        //understander.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/sud.wav");

        //3.开始语义理解
        understander.startUnderstanding(new SpeechUnderstanderListener(){
            //开始录音
            public void onBeginOfSpeech() {}
            //音量值0~30
            public void onVolumeChanged(int volume, byte[] bytes){}
            //结束录音
            public void onEndOfSpeech() {}
            //扩展用接口
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {}
            //会话发生错误回调接口
            public void onError(SpeechError error) {}
            // XmlParser为结果解析类，见SpeechDemo
            public void onResult(UnderstanderResult result) {
                String text = result.getResultString();
            }
        });
    }
}
