package com.servicetool.api.fcm;

import com.google.firebase.messaging.Message;
import com.servicetool.api.fcm.request.SendFcmRequest;

public class FcmUrlFactory {
    private static final String PROJECT_ID = "forexservice-9df84";
    private static final String DOMAIN = "https://fcm.googleapis.com/v1/";

    public static String getSendFcmUrl() {
        return DOMAIN + "projects/" + PROJECT_ID + "/messages:send";
    }

    public static SendFcmRequest buildSendFcmRequest(Message message) {
        SendFcmRequest sendFcmRequest = new SendFcmRequest();
        sendFcmRequest.setMessage(message);
        return sendFcmRequest;
    }
}
