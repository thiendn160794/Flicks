package com.thiendn.coderschool.flicks.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thiendn on 17/02/2017.
 */

public class ListTrailer {
    @SerializedName("results")
    private List<Trailer> list;

    public List<Trailer> getList() {
        return list;
    }
}
