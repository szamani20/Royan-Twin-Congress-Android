package com.royan.twincongress.models;

/**
 * Created by szamani on 8/4/2017.
 */

public class FetchBodyRequest {
    public String model_type;
    public Integer start_id;
    public Integer end_id;

    public FetchBodyRequest(String model_type, Integer start_id, Integer end_id) {
        this.model_type = model_type;
        this.start_id = start_id;
        this.end_id = end_id;
    }
}
