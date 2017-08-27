package com.royan.twincongress.models;

import io.realm.RealmObject;

/**
 * Created by szamani on 7/27/2017.
 */

public class Event extends RealmObject {
    public Integer id;
    public String name;
    public String topic;
    public String time;
    public String venue;
    public String sortCriteria;

    public Event(Integer id, String name, String topic, String time, String venue) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.time = time;
        this.venue = venue;
    }

    public Event() {
    }
}
