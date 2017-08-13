package com.royan.twincongress.connections;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.royan.twincongress.models.Speaker;
import com.royan.twincongress.models.FetchBodyRequest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static com.royan.twincongress.connections.FetchHelper.getUrlString;
import static com.royan.twincongress.connections.FetchHelper.postUrlString;
import static com.royan.twincongress.helpers.Constants.IS_SPEAKER;
import static com.royan.twincongress.helpers.Constants.NURSE_CONGRESS;
import static com.royan.twincongress.helpers.Constants.OP_SPEAKER;
import static com.royan.twincongress.helpers.Constants.POSTER_SPEAKER;
import static com.royan.twincongress.helpers.Constants.RBC_CONGRESS;
import static com.royan.twincongress.helpers.Constants.SCC_CONGRESS;

/**
 * Created by szamani on 8/4/2017.
 */

public class FetchCongress {
    public List<Speaker> getSpeakerRange(int congress, int type, int start_id, int end_id) {
        try {
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getName().toLowerCase().equals("bookmarked");
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();
            Type collectionType = new TypeToken<Collection<Speaker>>() {
            }.getType();
            String base_url = "https://royan.szamani.ir/";
            switch (congress) {
                case 0:
                    base_url += "scc";
                    break;
                case 1:
                    base_url += "rbc";
                    break;
                case 2:
                    base_url += "nc";
                    break;
            }
            base_url += "/fetch/";
            String speaker_type;
            switch (type) {
                case 0:
                    speaker_type = "IS";
                    break;
                case 1:
                    speaker_type = "OP";
                    break;
                case 2:
                    speaker_type = "Poster";
                    break;
                default:
                    speaker_type = "";
            }
            FetchBodyRequest bodyRequest = new FetchBodyRequest(
                    speaker_type, start_id, end_id);

            String result_string = postUrlString(base_url, gson.toJson(bodyRequest));
            System.out.println(result_string);
            return gson.fromJson(result_string, collectionType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Speaker> getSpeakerAll(int congress, int type) {
        try {
//            Gson gson = new GsonBuilder().serializeNulls().create();
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getName().toLowerCase().equals("bookmarked");
                        }
                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();
            Type collectionType = new TypeToken<Collection<Speaker>>() {
            }.getType();
            String base_url = setBaseUrl("https://royan.szamani.ir/", congress, type);
            String result_string = getUrlString(base_url);
            System.out.println(result_string);

            List<Speaker> res = gson.fromJson(result_string, collectionType);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String setBaseUrl(String base_url, int congress, int type) {
        switch (congress) {
            case SCC_CONGRESS:
                switch (type) {
                    case IS_SPEAKER:
                        base_url += "scc/all_is_speaker";
                        break;
                    case OP_SPEAKER:
                        base_url += "scc/all_op_speaker";
                        break;
                    case POSTER_SPEAKER:
                        base_url += "scc/all_poster_speaker";
                        break;
                }
                break;
            case RBC_CONGRESS:
                switch (type) {
                    case IS_SPEAKER:
                        base_url += "rbc/all_is_speaker";
                        break;
                    case OP_SPEAKER:
                        base_url += "rbc/all_op_speaker";
                        break;
                    case POSTER_SPEAKER:
                        base_url += "rbc/all_poster_speaker";
                        break;
                }

                break;
            case NURSE_CONGRESS:
                switch (type) {
                    case IS_SPEAKER:
                        base_url += "nc/all_is_speaker";
                        break;
                    case OP_SPEAKER:
                        base_url += "nc/all_op_speaker";
                        break;
                    case POSTER_SPEAKER:
                        base_url += "nc/all_poster_speaker";
                        break;
                }

                break;
        }

        return base_url;
    }
}
