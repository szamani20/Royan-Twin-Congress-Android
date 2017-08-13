package com.royan.twincongress.models;

import io.realm.RealmObject;

/**
 * Created by szamani on 7/27/2017.
 */

public class Winner extends RealmObject {
    public Integer id;
    public Integer type; // 0 --> international, 1 --> national
    public String name;
    public String email;
    public String country;
    public String avatar;
    public String affiliation;
    public String short_cv;
    public String award_time;
    public String award_venue;
    public Abstract aabstract;

    public Winner() {
    }
}
