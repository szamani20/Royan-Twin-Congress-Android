package com.royan.twincongress.connections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.royan.twincongress.models.FetchBodyRequest;
import com.royan.twincongress.models.Winner;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static com.royan.twincongress.connections.FetchHelper.getUrlString;
import static com.royan.twincongress.connections.FetchHelper.postUrlString;

/**
 * Created by szamani on 8/5/2017.
 */

public class FetchWinners {
    public List<Winner> getWinnerRange(int type, int start_id, int end_id) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Type collectionType = new TypeToken<Collection<Winner>>() {
            }.getType();
            String base_url = "https://royan.szamani.ir/akp/fetch/";

            String winnerType;
            switch (type) {
                case 0:
                    winnerType = "IW";
                    break;
                case 1:
                    winnerType = "NW";
                    break;
                default:
                    winnerType = "IW";
                    break;
            }

            FetchBodyRequest bodyRequest = new FetchBodyRequest(
                    winnerType, start_id, end_id);
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

    public List<Winner> getWinnerAll(int type) {  // 0 --> International, 1 --> National
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Type collectionType = new TypeToken<Collection<Winner>>() {
            }.getType();

            String base_url = setBaseUrl("https://royan.szamani.ir/", type);
            String result_string = getUrlString(base_url);
//            System.out.println(result_string);

            List<Winner> res = gson.fromJson(result_string, collectionType);
//            for (Winner w : res)
//                System.out.println(w.type + " " + w.name);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String setBaseUrl(String base_url, int type) {
        switch (type) {
            case 0:
                base_url += "akp/all_international_winners";
                break;
            case 1:
                base_url += "akp/all_national_winners";
                break;
        }
        return base_url;
    }
}
