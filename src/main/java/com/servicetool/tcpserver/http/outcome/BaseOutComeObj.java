/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.tcpserver.http.outcome;

/**
 *
 * @author PhanDuy
 */
public class BaseOutComeObj {
    private int resultCode;
    private String message;
    private String data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseOutComeObj{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
