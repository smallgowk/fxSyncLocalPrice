package com.servicetool.server;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.servicetool.db.DBFcmUtil;
import com.servicetool.db.GetModelInterface;
import com.servicetool.model.Signal;
import com.servicetool.utils.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FirebaseManager {

    public static HashMap<String, String> datas = new HashMap<>();
    public static Signal testSignal;
    public static ArrayList<String> topics;
    static {
        testSignal = new Signal();
        testSignal.setSignalType("BUY");
        testSignal.setCurrency("BTCUSD");
        testSignal.setEntry(2.0f);
        testSignal.setTakeProfit(3.0f);
        testSignal.setStopLoss(1.0f);
        testSignal.setTimeStamp(System.currentTimeMillis());
    }

    private static FirebaseManager firebaseManager;
    public static FirebaseManager getInstance() {
        if (firebaseManager == null) {
            firebaseManager = new FirebaseManager();
        }
        return firebaseManager;
    }

    public void init() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("etc/forexservice-9df84-firebase-adminsdk-fwdmx-7410c3470a.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

//        AndroidNotification androidNotification = AndroidNotification.builder()
//                .setTitle("Android Title")
//                .setBody("Android Body")
//                .build();
//        AndroidConfig androidConfig = AndroidConfig.builder()
//                .setDirectBootOk(true)
//                .setNotification(androidNotification)
//                .build()
//                ;
//
//        Notification notification = Notification.builder()
//                .setBody("Test body")
//                .setTitle("Test title")
//                .setImage("Test Url")
//                .build();
//        Message message = Message.builder()
//                .setAndroidConfig(androidConfig)
//                .setNotification(notification)
//                .setToken("Duyuno")
//                .build();
//
//        Message message1 = Message.builder()
//                .putAllData(datas)
//                .setAndroidConfig(androidConfig)
//                .setNotification(notification)
//                .setTopic("signal")
//                .build();
//        try {
//            String response = FirebaseMessaging.getInstance().send(message1);
//        } catch (FirebaseMessagingException e) {
//            e.printStackTrace();
//        }
    }

    public static void initTopics() {

    }

    public void addTokenToTopic(String token, String topic) {
        List<String> registrationTokens = Arrays.asList(token);
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(registrationTokens, topic);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public void addTokensToTopic(List<String> tokens, String topic) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(tokens, topic);
            System.out.println("Success count " + response.getSuccessCount());
            System.out.println("Failure count " + response.getFailureCount());
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendAndroidMessage(Signal signal, String topic) {
        AndroidNotification androidNotification = AndroidNotification.builder()
                .setTitle("TVI Trading")
                .setBody(signal.getBodyContent())
                .build();
        AndroidConfig androidConfig = AndroidConfig.builder()
                .setDirectBootOk(true)
                .setNotification(androidNotification)
                .build();

        Message message = Message.builder()
                .putAllData(signal.getFcmDatas())
                .setAndroidConfig(androidConfig)
                .setTopic(topic)
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendAndroidMessage(Signal signal) {
        DBFcmUtil.getListAvaiFcmTokensByPairAgent(
                Constants.Agent.ANDROID,
                signal.currency,
                new GetModelInterface() {
                    @Override
                    public void onResult(Object object) {
                        if (object == null) {
                            System.out.println("No result!");
                            return;
                        }
                        ArrayList<String> listTokens = (ArrayList<String>)object;
                        System.out.println("listTokens size: " + listTokens.size());
                        FirebaseManager.getInstance().addTokensToTopic(listTokens, signal.currency);
                        sendAndroidMessage(signal, signal.currency);
                    }
                });
    }

    public void test() {
        sendAndroidMessage(testSignal);
    }
//
//    public void test(String topic) {
//        sendAndroidMessage(testSignal, topic);
//    }
}
