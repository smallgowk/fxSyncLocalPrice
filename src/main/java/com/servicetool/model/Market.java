package com.servicetool.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Market {
    public int id;
    public String name;
    public int openHour;
    public int closeHour;
    public String season;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        this.closeHour = closeHour;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public static ArrayList<Market> fromResultSet(ResultSet resultSet) {
        ArrayList<Market> list = null;
        try {
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                Market market = new Market();
                market.setId(resultSet.getInt(1));
                market.setName(resultSet.getString(2));
                market.setOpenHour(resultSet.getInt(3));
                market.setCloseHour(resultSet.getInt(4));
                market.setSeason(resultSet.getString(4));
                list.add(market);
            }
        } catch (SQLException throwables) {

        }
        return list;
    }
}
