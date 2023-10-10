/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.tcpserver;


/**
 *
 * @author taipa
 */
public class MessageObject {

    private String version;
    private String diskSerialNumber;
    private String userEmail;

    public MessageObject() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDiskSerialNumber() {
        return diskSerialNumber;
    }

    public void setDiskSerialNumber(String diskSerialNumber) {
        this.diskSerialNumber = diskSerialNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    
}
