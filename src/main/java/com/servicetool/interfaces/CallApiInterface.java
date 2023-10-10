/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.interfaces;

/**
 *
 * @author Admin
 */
public interface CallApiInterface {
    public void onSuccess(String response);
    public <T> void onSuccessNew(T object);
    public void onFailure(Exception ex);
}
