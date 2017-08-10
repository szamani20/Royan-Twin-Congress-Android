package com.royan.twincongress.connections;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.royan.twincongress.helpers.StringRealmListConverter;
import com.royan.twincongress.models.Company;
import com.royan.twincongress.models.FetchBodyRequest;
import com.royan.twincongress.models.RealmString;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

import static com.royan.twincongress.connections.FetchHelper.getUrlString;
import static com.royan.twincongress.connections.FetchHelper.postUrlString;

/**
 * Created by szamani on 8/5/2017.
 */

public class FetchCompany {
    public List<Company> getCompanyRange(int type, int start_id, int end_id) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            Type collectionType = new TypeToken<Collection<Company>>() {
            }.getType();
            String base_url = "https://royan.szamani.ir/company/fetch";

            String companyType;
            switch (type) {
                case 0:
                    companyType = "SP";
                    break;
                case 1:
                    companyType = "OR";
                    break;
                default:
                    companyType = "SP";
                    break;
            }

            FetchBodyRequest bodyRequest = new FetchBodyRequest(
                    companyType, start_id, end_id);

            String result_string = postUrlString(base_url, gson.toJson(bodyRequest));

            return gson.fromJson(result_string, collectionType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Company> getCompanyAll(int type) {  // 0 --> Sponsor, 1 --> Ordinary
        try {
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaredClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .registerTypeAdapter(
                            new TypeToken<RealmList<RealmString>>() {}.getType(),
                            new TypeAdapter<RealmList<RealmString>>() {
                                @Override
                                public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {

                                }

                                @Override
                                public RealmList<RealmString> read(JsonReader in) throws IOException {
                                    RealmList<RealmString> list = new RealmList<RealmString>();
                                    in.beginArray();
                                    while (in.hasNext())
                                        list.add(new RealmString(in.nextString()));
                                    in.endArray();
                                    return list;
                                }
                            }
                    )
                    .create();
            Type collectionType = new TypeToken<Collection<Company>>() {
            }.getType();

            String base_url = setBaseUrl("https://royan.szamani.ir/", type);
            String result_string = getUrlString(base_url);
            System.out.println(result_string);

            List<Company> res = gson.fromJson(result_string, collectionType);
            for (Company c : res)
                System.out.println(c.type + " " + c.name);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String setBaseUrl(String base_url, int type) {
        switch (type) {
            case 0:
                base_url += "company/all_sp_company";
                break;
            case 1:
                base_url += "company/all_or_company";
                break;
        }
        return base_url;
    }
}
