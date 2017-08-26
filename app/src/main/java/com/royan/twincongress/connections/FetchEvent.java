package com.royan.twincongress.connections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.royan.twincongress.models.Event;
import com.royan.twincongress.models.FetchBodyRequest;
import com.royan.twincongress.models.Winner;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static com.royan.twincongress.connections.FetchHelper.getUrlString;
import static com.royan.twincongress.connections.FetchHelper.postUrlString;

/**
 * Created by szamani on 8/15/2017.
 */

public class FetchEvent {
    public List<Event> getEventRange(int start_id, int end_id) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Type collectionType = new TypeToken<Collection<Event>>() {
            }.getType();
            String base_url = "https://royan.szamani.ir/agenda/fetch/";

            FetchBodyRequest bodyRequest = new FetchBodyRequest(
                    null, start_id, end_id);
//            System.out.println(base_url);
//            System.out.println(gson.toJson(bodyRequest));
            String result_string = postUrlString(base_url, gson.toJson(bodyRequest));
//            System.out.println(result_string);
            return gson.fromJson(result_string, collectionType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Event> getEventsAll() {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Type collectionType = new TypeToken<Collection<Event>>() {
            }.getType();

            String base_url = "https://royan.szamani.ir/agenda/all_events/";
            String result_string = getUrlString(base_url);
//            System.out.println(result_string);

            List<Event> res = gson.fromJson(result_string, collectionType);
//            for (Event e : res)
//                System.out.println(e.name + " " + e.topic);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
