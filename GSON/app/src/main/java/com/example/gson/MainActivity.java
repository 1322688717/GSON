package com.example.gson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import static com.example.gson.GetRequest.get;

public class MainActivity extends AppCompatActivity {
    private TextView text1, text2, text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = findViewById(R.id.tv_1);
        text2 = findViewById(R.id.tv_2);
        text3 = findViewById(R.id.tv_3);
        findViewById(R.id.btn_parse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseByGson();
            }
        });
    }

    private void parseByGson() {
        Gson gson = new Gson();
        //将对象变成json字符串
        Bean bean = new Bean("ok", "zh_CN", "metric", "Asia/Shanghai");
        String str = gson.toJson(bean);
        Log.e("TAG", "将Bean对象变成字符串：" + str);
        //将json字符串变成对象
        Bean msg = gson.fromJson(str, Bean.class);
        Log.e("TAG", "" + msg);
        Log.e("TAG", "状态=" + msg.getStatus() + "       lang值=" + msg.getLang()
                + "       单位=" + msg.getUnit() + "     时区" + msg.getTimezone());

        //================解析一个实际的字符串  在这里我是解析彩云的json字符串
        new Thread() {
            @Override
            public void run() {
                super.run();
                //运行get方法得到彩云的json对象
                String json = get();
                Msg msg1 = gson.fromJson(json, Msg.class);
                Log.e("TAG", "==================================");
                Log.e("TAG", "这里是json字符串转换成Msg对象:" + msg1);
                Log.e("TAG", "其中状态值：" + msg1.getStatus());
            }
        }.start();

    }
}