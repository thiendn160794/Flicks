package com.thiendn.coderschool.flicks.api;

import com.thiendn.coderschool.flicks.model.ListTrailer;
import com.thiendn.coderschool.flicks.model.NowPlaying;
import com.thiendn.coderschool.flicks.model.Trailer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by thiendn on 16/02/2017.
 */

public interface MovieAPI {
    @GET("now_playing")
    Call<NowPlaying> getNowPlaying();

    @GET("{id}/videos")
    Call<ListTrailer> getTrailer(@Path("id") String id);
}
