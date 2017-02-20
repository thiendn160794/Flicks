package com.thiendn.coderschool.flicks.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thiendn on 16/02/2017.
 */

public class NowPlaying {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
