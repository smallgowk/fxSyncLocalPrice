/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.api;

import com.google.gson.Gson;
import com.servicetool.interfaces.CallApiInterface;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Admin
 */
public class ApiBase {

    private static ApiBase apiBase;

    public static ApiBase getInstance() {
        if (apiBase == null) {
            apiBase = new ApiBase();
            apiBase.init();
        }

        return apiBase;
    }

    public void init() {
        gson = new Gson();
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            client = HttpClientBuilder.create().setSSLContext(sslContext).build();
        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(ApiBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    HttpClient client;
    Gson gson;

    final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    }};
    SSLContext sslContext;

//    static {
//        try {
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustAllCerts,
//                    new java.security.SecureRandom());
//            client = HttpClientBuilder.create().setSSLContext(sslContext).build();
//        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
//            Logger.getLogger(ApiBase.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public <T> T sendGet(String url, Class<T> c) {

        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);
            String responseStr = EntityUtils.toString(response.getEntity());
            return gson.fromJson(responseStr, c);
        } catch (IOException e) {
            return null;
        }
    }

    public String sendGetWithStrResponse(String url) {
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            return null;
        }
    }

    public void sendPostStringEntity(String url, StringEntity entity, CallApiInterface callApiInterface) {
//        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer AAAAAqgUJuk:APA91bEG86G14we0MDvpJ6OSC1P42G8tt0wf9IKdaj-6WGuoqH6y8QCTmYw1fl-s2_bsbLXA6167piy7ee5RXs3Q91o-HQB83FFDklHRGRGsa1bfkEdq_J4-sx6dOUyGQLZ-Q0ncd3ln");
        post.setEntity(entity);

        try {
            HttpResponse response = client.execute(post);
            String responseStr = EntityUtils.toString(response.getEntity());
//            System.out.println("" + responseStr);
            if (callApiInterface != null) {
                callApiInterface.onSuccess(responseStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (callApiInterface != null) {
                callApiInterface.onFailure(e);
            }
        }
    }

    public String sendPostStringEntity(String url, StringEntity entity, String[][] headers, CallApiInterface callApiInterface) {
        HttpPost post = new HttpPost(url);

        if (headers
                != null) {
            for (String[] header : headers) {
                if (header[0] != null && header[1] != null) {
                    post.setHeader(header[0], header[1]);
                }
            }
        }

        post.setEntity(entity);

        try {
//            long start = System.currentTimeMillis();
            HttpResponse response = client.execute(post);
//            long end = System.currentTimeMillis();

//            System.out.println("Time Request: " + (end - start));
            String responseStr = EntityUtils.toString(response.getEntity());
            if (callApiInterface != null) {
                callApiInterface.onSuccess(responseStr);
            }
            return responseStr;
        } catch (IOException e) {
            e.printStackTrace();

            if (callApiInterface != null) {
                callApiInterface.onFailure(e);
            }
            return null;
        }
    }
}
