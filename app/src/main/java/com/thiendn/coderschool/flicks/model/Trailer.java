package com.thiendn.coderschool.flicks.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thiendn on 17/02/2017.
 */

public class Trailer {
    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }
}
