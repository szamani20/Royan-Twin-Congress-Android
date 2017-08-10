package com.royan.twincongress.models;


import io.realm.RealmObject;

/**
 * Created by szamani on 7/27/2017.
 */

public class Abstract extends RealmObject {
    public String background;
    public String objective;
    public String method;
    public String result;
    public String conclusion;
    public String keyword;

    public Abstract(String background, String objective, String method, String result, String conclusion, String keyword) {
        this.background = background;
        this.objective = objective;
        this.method = method;
        this.result = result;
        this.conclusion = conclusion;
        this.keyword = keyword;
    }

    public Abstract() {
    }
}