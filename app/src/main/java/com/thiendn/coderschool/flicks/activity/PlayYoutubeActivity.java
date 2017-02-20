package com.thiendn.coderschool.flicks.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.thiendn.coderschool.flicks.R;
import com.thiendn.coderschool.flicks.api.MovieAPI;
import com.thiendn.coderschool.flicks.model.ListTrailer;
import com.thiendn.coderschool.flicks.model.Movie;
import com.thiendn.coderschool.flicks.utils.Constants;
import com.thiendn.coderschool.flicks.utils.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thiendn on 19/02/2017.
 */

public class PlayYoutubeActivity extends YouTubeBaseActivity{
    MovieAPI movieAPI;
    @BindView(R.id.player)
    YouTubePlayerView player;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.play_youtube);
        ButterKnife.bind(this);

        Movie currentMovie = (Movie) getIntent().getSerializableExtra("Movie");
        loadToYoutube(currentMovie);
    }

    private void loadToYoutube(final Movie movie){
        player.initialize(getString(R.string.YOUTUBE_TOKEN), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                movieAPI = RetrofitUtils.get(getString(R.string.api_key)).create(MovieAPI.class);
                movieAPI.getTrailer(movie.getId()).enqueue(new Callback<ListTrailer>() {
                    @Override
                    public void onResponse(Call<ListTrailer> call, Response<ListTrailer> response) {
                        String youtube_key = response.body().getList().get(0).getKey();
                        youTubePlayer.loadVideo(youtube_key);
                    }

                    @Override
                    public void onFailure(Call<ListTrailer> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getBaseContext(), "Fail to initialize youtube player!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
