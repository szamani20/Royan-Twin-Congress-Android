package com.royan.twincongress.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.royan.twincongress.models.RealmString;

import java.lang.reflect.Type;

import io.realm.RealmList;

/**
 * Created by szamani on 8/6/2017.
 */

public class StringRealmListConverter implements JsonSerializer<RealmList<RealmString>>,
        JsonDeserializer<RealmList<RealmString>> {
    @Override
    public RealmList<RealmString> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RealmList<RealmString> pics = new RealmList<>();
        JsonArray ja = json.getAsJsonArray();

        for (JsonElement je : ja)
            pics.add((RealmString) context.deserialize(je, RealmString.class));

        return pics;
    }

    @Override
    public JsonElement serialize(RealmList<RealmString> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray ja = new JsonArray();
        for (RealmString s : src)
            ja.add(context.serialize(s));

        return ja;
    }
}
