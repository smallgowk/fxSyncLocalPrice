/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.utils;

/**
 *
 * @author duyuno
 */
public class Constants {
    public interface FrameHelper {
        public static final String START_MARK = "[";
        public static final String END_MARK = "]";
        public static final String SPLIT = ",";
//        public static final byte[] PACKAGE_START_MARK = "[START]".getBytes();
        public static final byte[] PACKAGE_START_MARK = START_MARK.getBytes();
        //        public static final byte[] PACKAGE_END_MARK = "[END]".getBytes();
        public static final byte[] PACKAGE_END_MARK = END_MARK.getBytes();
        
    }
    
    public interface ConfigPaths {
        public static final String LOG4J_PROPERTY = "../etc/log4j.properties";
        public static final String MTR_CORE_PROPERTY = "../etc/mtr_core.properties";
    }
    
    public interface DataType {
        public static final int STORE_COMMON = 1;
        public static final int STORE_PAGE = 2;
        public static final int STORE_FULL = 3;
    }
    public interface DataType_v2 {
        public static final String STORE_COMMON = "STORE_COMMON";
        public static final String STORE_PAGE = "STORE_PAGE";
    }
    
    public interface AuthenType {
        public static final int REGISTER = 1;
        public static final int UPDATE_LICENSE = 2;
    }
    
    public interface AuthenType_v2 {
        public static final String REGISTER = "REGISTER";
        public static final String UPDATE_LICENSE = "UPDATE_LICENSE";
    }
    
    public interface TCPAction {
        public static final String CRAWL_DATA = "CRAWL_DATA";
        public static final String GET_ACCOUNT = "GET_ACCOUNT";
        public static final String GET_CERT = "GET_CERT";
        public static final String UPDATE_INFO = "UPDATE_INFO";
        public static final String SEND_USER_INFO = "SEND_USER_INFO";
    }
    
    public interface TCPAction_v2 {
        public static final String CRAWL_DATA = "CRAWL_DATA";
        public static final String SIGNUP = "SIGNUP";
        public static final String SIGNIN = "SIGNIN";
        public static final String UPDATE_LICENSE = "UPDATE_LICENSE";
        public static final String CHECK_LICENSE = "CHECK_LICENSE";
        public static final String CHECK_SERIAL = "CHECK_SERIAL";
    }
    
    public interface MarketType {
        public static final String ALIEX = "aliex";
        public static final String BANGGOOD = "banggood";
    }

    public interface HttpAction {
        public static final String UPDATE_USER_INFO = "UPDATE_USER_INFO";
        public static final String SEND_FCM_TOKEN = "SEND_FCM_TOKEN";
        public static final String UPDATE_SUBSCRIPTION = "UPDATE_SUBSCRIPTION";
    }
    
    public interface ResultCode {
        
        public static final int SUCCESS = 1;
        
        //Authen
        public static final int AUTHEN_FAIL = -401;
        
        public static final int FAILURE = -1;
        public static final int FAILURE_ENCRYPT = -2;
        public static final int FAILURE_NO_STORE_INFO = -3;
        public static final int FAILURE_USER_EXISTED = -4;
        public static final int FAILURE_EMAIL_EXISTED = -5;
        public static final int FAILURE_LICENSE_USED = -6;
        
        public static final int VERSION_INVALID = -7;
        public static final int VERSION_OLD = -8;
        public static final int SERIAL_INVALID = -9;
    }

    public interface Agent {
        public static final String ANDROID = "ANDROID";
        public static final String IOS = "IOS";
    }
}
