package com.royan.twincongress.services;

import org.json.JSONObject;

import co.ronash.pushe.PusheListenerService;

/**
 * Created by szamani on 8/6/2017.
 */

public class MyPushListener extends PusheListenerService {
    @Override
    public void onMessageReceived(JSONObject json, JSONObject messageContent) {
        System.out.println(json.toString());
        System.out.println(messageContent.toString());
    }
}
