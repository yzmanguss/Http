package com.example.xrealcool.httpclient;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private Button send;
    private TextView tv_site;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        if (msg.obj.equals("T")){
                            Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = URLEncoder.encode(et_username.getText().toString());
                String password = URLEncoder.encode(et_password.getText().toString());
                String url = "http://192.168.43.143:8080/Http/servlet/RegisterServlet?name="+name+"&password="+password;
                Log.d("123",url);
                ConByGetHttp conByGetHttp = new ConByGetHttp(MainActivity.this,url, handler);
                conByGetHttp.start();
            }
        });
    }

    /**
     * 获取控件
     */
    private void getViews() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        send = findViewById(R.id.send);
        tv_site = findViewById(R.id.tv_site);

    }

    public void show(final String view){
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               tv_site.append(view);
           }
       });
    }
}
