package com.royan.twincongress.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by szamani on 7/27/2017.
 */

public class Company extends RealmObject {
    public Integer id;
    public Integer type; // 0 --> Sponsor (SP), 1 --> Ordinary (OR)
    public String name;
    public String website;
    public String logo;
    public String location;
    public String phone;
    public String address;
    public RealmList<RealmString> pics;
}
