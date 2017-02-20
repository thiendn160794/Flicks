package com.thiendn.coderschool.flicks.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.thiendn.coderschool.flicks.R;
import com.thiendn.coderschool.flicks.adapter.MovieAdapter;
import com.thiendn.coderschool.flicks.api.MovieAPI;
import com.thiendn.coderschool.flicks.model.ListTrailer;
import com.thiendn.coderschool.flicks.model.Movie;
import com.thiendn.coderschool.flicks.model.NowPlaying;
import com.thiendn.coderschool.flicks.utils.Constants;
import com.thiendn.coderschool.flicks.utils.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieCallback {

    @BindView(R.id.lvMovie) ListView lvMovie;
    @BindView(R.id.pbLoading) ProgressBar proLoading;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    private MovieAPI movieAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadContent();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContent();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void loadContent(){
        movieAPI = RetrofitUtils.get(getString(R.string.api_key)).create(MovieAPI.class);
        movieAPI.getNowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                fetchMovie(response);
                Toast.makeText(getBaseContext(), "Load success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Fail to get now_playing list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMovie(Response<NowPlaying> response){
        lvMovie.setAdapter(new MovieAdapter(getBaseContext(), response.body().getMovies(), this));
        proLoading.setVisibility(View.GONE);
    }

    @Override
    public void onLowRateImageClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }

    @Override
    public void onHighRateImageClick(final Movie movie) {
        Intent intent = new Intent(this, PlayYoutubeActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }
}
