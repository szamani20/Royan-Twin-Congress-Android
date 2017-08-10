package com.royan.twincongress.models;

import io.realm.RealmModel;
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

//    public Winner(Integer type, String name, String email, String country, String avatar, String affiliation, String short_cv, String award_time, String award_venue, Abstract anAbstract) {
//        this.type = type;
//        this.name = name;
//        this.email = email;
//        this.country = country;
//        this.avatar = avatar;
//        this.affiliation = affiliation;
//        this.short_cv = short_cv;
//        this.award_time = award_time;
//        this.award_venue = award_venue;
//        this.anAbstract = anAbstract;
//    }

    public Winner() {
    }
}
