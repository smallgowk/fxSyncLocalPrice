package com.servicetool.server.process;

public class TraderMakerIOLogin {
    public String userKey;

    public TraderMakerIOLogin(String userKey) {
        this.userKey = userKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
