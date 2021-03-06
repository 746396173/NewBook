package com.thmub.newbook.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public class OkHttpUtils {

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
//            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            //请设置代理，否则会被小说网站ban的...量小没关系
//            .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("106.75.226.36", 808)))
            .build();

    /**
     * 同步获取html文件，默认编码utf-8
     */
    public static String getHtml(String url) throws IOException {
        return getHtml(url, "utf-8");
    }

    public static String getHtml(String url, String encodeType) throws IOException {
        return getHtml(url, null,encodeType);
    }

    public static String getHtml(String url, RequestBody requestBody, String encodeType) throws IOException {
        Request.Builder builder = new Request.Builder()
                .addHeader("accept", "*/*")
                .addHeader("connection", "Keep-Alive")
                .addHeader("Charsert", "utf-8")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");

        if (requestBody != null) {
            builder.post(requestBody);
        }
        Request request = builder
                .url(url)
                .build();
        Response response = okHttpClient
                .newCall(request)
                .execute();
        ResponseBody body=response.body();
        if (body == null) {
            return "";
        } else {
            return new String(body.bytes(), encodeType);
        }
    }
}
