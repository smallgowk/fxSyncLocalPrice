package com.servicetool.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Subscription {
    public String name;
    public String description;
    public int period;
    public float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", period=" + period +
                ", price=" + price +
                '}';
    }

    public static ArrayList<Subscription> fromResultSet(ResultSet resultSet) {
        ArrayList<Subscription> list = null;
        try {
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                Subscription subscription = new Subscription();
                subscription.setName(resultSet.getString(1));
                subscription.setDescription(resultSet.getString(2));
                subscription.setPeriod(resultSet.getInt(3));
                subscription.setPrice(resultSet.getFloat(4));
                list.add(subscription);
            }
        } catch (SQLException throwables) {

        }
        return list;
    }
}
