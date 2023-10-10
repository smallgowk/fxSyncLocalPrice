package com.servicetool.api.fcm;

import com.google.firebase.messaging.Message;
import com.google.gson.Gson;
import com.servicetool.api.ApiBase;
import com.servicetool.api.fcm.request.SendFcmRequest;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class FcmApiCall {
    public static void sendFcmMessage(Message message) throws UnsupportedEncodingException {
        String url = FcmUrlFactory.getSendFcmUrl();
        SendFcmRequest request = FcmUrlFactory.buildSendFcmRequest(message);
        Gson gson = new Gson();
        String body = gson.toJson(request);
        ApiBase.getInstance().sendPostStringEntity(url, new StringEntity(body), null);
    }
}
