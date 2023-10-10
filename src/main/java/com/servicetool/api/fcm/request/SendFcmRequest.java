package com.servicetool.api.fcm.request;

import com.google.firebase.messaging.Message;

public class SendFcmRequest {
    public boolean validate_only;
    public Message message;

    public boolean isValidate_only() {
        return validate_only;
    }

    public void setValidate_only(boolean validate_only) {
        this.validate_only = validate_only;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
