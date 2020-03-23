package com.example.xrealcool.httpclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username;
    private Button login;
    private Button register;
    private Handler handler;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1:
                        Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /**
     * 获取控件
     */
    private void getViews() {
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);

        login = findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        register = findViewById(R.id.create_account);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                requestToServer(true);
                break;
            case R.id.create_account:
                requestToServer(false);
                break;
        }
    }

    /**
     * 向服务器发起请求
     *
     * @param islogin 登录（true）   注册（false）
     */
    private void requestToServer(boolean islogin) {
        try {
            String name = URLEncoder.encode(et_username.getText().toString(), "utf-8");
            String password = URLEncoder.encode(et_password.getText().toString(), "utf-8");
            if (islogin) {
                String info = dataToJSON(name, password, islogin);
                if (info != null) {
                    String url = "http://192.168.43.143:7856/Http/servlet/RegisterServlet?info=" + info;
                    //注意：千万不要把“=”转义了----会报错（net.sf.json.JSONException: Missing value. at character 0 of）
                     String rurl= url.replace("\"","%22")
                            .replace("{","%7B").replace("}","%7D").replace("\\","%5C");
                    Log.d("123", url);
                    ConByGetHttp conByGetHttp = new ConByGetHttp(MainActivity.this, rurl, handler);
                    conByGetHttp.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 把要传输的数据封装成JSON格式
     *
     * @param name     用户名
     * @param password 密码
     * @param islogin  登录（注册）
     * @return 封装好的JSON数据
     */
    private String dataToJSON(String name, String password, boolean islogin) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("islogin", islogin);
            jsonObject.put("name", name);
            jsonObject.put("password", password);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
