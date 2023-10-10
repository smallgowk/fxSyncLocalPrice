/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.authen;

/**
 *
 * @author PhanDuy
 */
public class License {
    
    public static final int TYPE_BRONZE = 1;
    public static final int TYPE_SILVER = 2;
    public static final int TYPE_GOLD = 3;
    public static final int TYPE_PLATINUM = 4;
    public static final int TYPE_VIP = 5;
    public static final int TYPE_SUPPER_VIP = 6;
    
    private String licenseCode;
    private String type;
    private int period;

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
    
    public static String getType(int type) {
        switch(type) {
            case TYPE_BRONZE:
                return "BRONZE";
                case TYPE_SILVER:
                return "SILVER";
                case TYPE_GOLD:
                return "GOLD";
                case TYPE_PLATINUM:
                return "PLATINUM";
                case TYPE_VIP:
                return "VIP";
                case TYPE_SUPPER_VIP:
                return "SUPER_VIP";
        }
        
        return null;
    }
}
