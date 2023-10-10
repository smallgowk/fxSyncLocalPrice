package com.servicetool.model.fcm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FcmToken {
    public String token;
    public String agent;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }


    public static FcmToken modelFromResultSet(ResultSet resultSet){
        FcmToken fcmToken = null;
        try {
            if (resultSet.next()) {
                fcmToken = new FcmToken();
                fcmToken.setToken(resultSet.getString(1));
                fcmToken.setAgent(resultSet.getString(2));
            }
        } catch (SQLException throwables) {

        }
        return fcmToken;
    }

    public static ArrayList<FcmToken> listModelFromResultSet(ResultSet resultSet){
        ArrayList<FcmToken> list = null;
        try {
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                FcmToken fcmToken = new FcmToken();
                fcmToken.setToken(resultSet.getString(1));
                fcmToken.setAgent(resultSet.getString(2));
                list.add(fcmToken);
            }
        } catch (SQLException throwables) {

        }
        return list;
    }

    public static String tokenFromResultSet(ResultSet resultSet){
        String token = null;
        try {
            if (resultSet.next()) {
                token = resultSet.getString(1);
            }
        } catch (SQLException throwables) {

        }
        return token;
    }

    public static ArrayList<String> listTokenFromResultSet(ResultSet resultSet){
        ArrayList<String> list = null;
        try {
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(resultSet.getString(1));
            }
        } catch (SQLException throwables) {

        }
        return list;
    }
}
