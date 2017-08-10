package com.royan.twincongress.models;

import io.realm.RealmObject;

/**
 * Created by szamani on 8/6/2017.
 */

public class RealmString extends RealmObject {
    public String pic;

    public RealmString() {
    }

    public RealmString(String pic) {
        this.pic = pic;
    }

    public String getValue() {
        return pic;
    }

    public void setValue(String value) {
        this.pic = value;
    }
}
