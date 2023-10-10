/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicetool.control;

import com.servicetool.db.DBAuthenUtil;
import com.servicetool.tcpserver.http.income.BaseIncomeRequestObject;
import com.servicetool.utils.Constants;

/**
 *
 * @author PhanDuy
 */
public class ProcessAuthenSvs {
    public static void processAuthenData(BaseIncomeRequestObject bio, ProcessInputLister processInputLister) {

        switch (bio.getAction()) {
            case Constants.HttpAction.SEND_FCM_TOKEN:
                DBAuthenUtil.processSendRegistrationKey(bio, processInputLister);
                break;
            case Constants.HttpAction.UPDATE_USER_INFO:
                DBAuthenUtil.processUpdateUserInfo(bio, processInputLister);
                break;
            case Constants.HttpAction.UPDATE_SUBSCRIPTION:
                DBAuthenUtil.processUpdateUserSubscription(bio, processInputLister);
                break;
        }
    }
}
