package com.example.xrealcool.httpclient;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConByGetHttp extends Thread {
    String urlString = null;
    private HttpURLConnection conn;
    MainActivity mainActivity;
    Handler handler;

    public ConByGetHttp(Context context, String url,Handler handler) {
        this.mainActivity = (MainActivity) context;
        this.urlString = url;
        this.handler = handler;
    }

    @Override
    public void run() {
        /**
         * 1.创建客户端对象
         * 2.创建连接对象
         * 3.发起连接
         * 4.获取服务器响应的数据
         */

        try {
            //1.创建客户端对象
            URL url = new URL(this.urlString);
            // 2.创建连接对象
            Log.d("123", "开始连接");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            //3.发起连接
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "GBK");
            conn.connect();
            Log.d("123", "连接成功");
            DataInputStream dis = new DataInputStream(conn.getInputStream());
             String contentInfo= dis.readUTF(dis);
            Log.d("123","数据："+contentInfo);
            Message message = new Message();
            message.what = 1;
            message.obj = contentInfo;
            handler.sendMessage(message);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

    }
}
