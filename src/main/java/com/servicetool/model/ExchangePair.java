package com.servicetool.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExchangePair {
    public String symbol;
    public String src_symbol;
    public String des_symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSrc_symbol() {
        return src_symbol;
    }

    public void setSrc_symbol(String src_symbol) {
        this.src_symbol = src_symbol;
    }

    public String getDes_symbol() {
        return des_symbol;
    }

    public void setDes_symbol(String des_symbol) {
        this.des_symbol = des_symbol;
    }

    public String getInstruments(String linkSymbol) {
        return src_symbol + linkSymbol + des_symbol;
    }

    @Override
    public String toString() {
        return "ExchangePair{" +
                "symbol='" + symbol + '\'' +
                ", src_symbol='" + src_symbol + '\'' +
                ", des_symbol='" + des_symbol + '\'' +
                '}';
    }

    public static ArrayList<ExchangePair> fromResultSet(ResultSet resultSet) {
        ArrayList<ExchangePair> list = null;
        try {
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                ExchangePair exchangePair = new ExchangePair();
                exchangePair.setSymbol(resultSet.getString(1));
                exchangePair.setSrc_symbol(resultSet.getString(2));
                exchangePair.setDes_symbol(resultSet.getString(3));
                list.add(exchangePair);
            }
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
        return list;
    }
}
