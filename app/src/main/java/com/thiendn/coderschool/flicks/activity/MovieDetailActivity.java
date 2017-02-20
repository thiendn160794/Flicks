package com.thiendn.coderschool.flicks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RatingBar;
import android.widget.TextView;
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
 * Created by thiendn on 17/02/2017.
 */

public class MovieDetailActivity extends YouTubeBaseActivity{

    @BindView(R.id.player)
    YouTubePlayerView youTubePlayerView;
    @BindView(R.id.rtbMovie)
    RatingBar rtbMovie;
    @BindView(R.id.detailTitle)
    TextView title;
    @BindView(R.id.detailReleaseDate)
    TextView releaseDate;
    @BindView(R.id.tvDetailOverview)
    TextView overview;
    @BindView(R.id.tvRating)
    TextView rating;
    MovieAPI movieAPI;
    String youtube_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        ButterKnife.bind(this);

        Movie currentMovie = (Movie) getIntent().getSerializableExtra("Movie");

        rtbMovie.setRating(currentMovie.getRating());
        rating.setText("      " + currentMovie.getRating() + "/10");
        title.setText(currentMovie.getTitle());
        releaseDate.setText("Release Date: " + currentMovie.getReleaseDate());
        overview.setText(currentMovie.getOverview());

        loadToYoutube(currentMovie);
    }

    private void loadToYoutube(final Movie movie){

        youTubePlayerView.initialize(getString(R.string.YOUTUBE_TOKEN), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                movieAPI = RetrofitUtils.get(getString(R.string.api_key)).create(MovieAPI.class);
                movieAPI.getTrailer(movie.getId()).enqueue(new Callback<ListTrailer>() {
                    @Override
                    public void onResponse(Call<ListTrailer> call, Response<ListTrailer> response) {
                        youtube_key = response.body().getList().get(0).getKey();
                        youTubePlayer.cueVideo(youtube_key);
                    }

                    @Override
                    public void onFailure(Call<ListTrailer> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Fail to get video key!", Toast.LENGTH_SHORT).show();
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
