package com.royan.twincongress.models;

import java.util.Objects;

import io.realm.RealmObject;

/**
 * Created by szamani on 7/27/2017.
 */

public class Speaker extends RealmObject {
    public Integer id;
    public Integer congress; // 0 --> SCC, 1 --> RBC, 2 --> Nurse
    public Integer type; // 0 --> IS, 1 --> OP, 2 --> Poster
    public String name;
    public String email;
    public String country;
    public String avatar;
    public String affiliation;
    public String topic;
    public String time;
    public String venue;
    public String sortCriteria;
    public Boolean bookmarked; // excluded when GSONing
    public Abstract aabstract;

    public Speaker() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Speaker))
            return false;
        Speaker other = (Speaker) obj;
        return Objects.equals(other.id, this.id) &&
                Objects.equals(other.type, this.type) &&
                Objects.equals(other.congress, this.congress) &&
                Objects.equals(other.name, this.name);
    }
}


