/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.control;

import com.servicetool.tcpserver.http.income.BaseIncomeRequestObject;

/**
 *
 * @author PhanDuy
 */
public class ProcessInputControl {

    public static void processInput(BaseIncomeRequestObject bio, ProcessInputLister processInputLister) {
        ProcessAuthenSvs.processAuthenData(bio, processInputLister);
    }

}
