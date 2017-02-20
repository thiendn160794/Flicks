package com.thiendn.coderschool.flicks.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.thiendn.coderschool.flicks.R;
import com.thiendn.coderschool.flicks.model.Movie;
import com.thiendn.coderschool.flicks.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by thiendn on 16/02/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie>{

    List<Movie> movies;
    WeakReference<MovieCallback> movieCallback;

    public MovieAdapter(Context context, List<Movie> list, MovieCallback movieCallback){
        super(context, -1);
        this.movies = list;
        this.movieCallback = new WeakReference<>(movieCallback);
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).getRating() >= 7) return 1;
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public interface MovieCallback{
        void onLowRateImageClick(Movie movie);
        void onHighRateImageClick(Movie movie);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            if (getItemViewType(position) == 1){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_hight_rate_movie, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.ivCover);
                convertView.setTag(viewHolder);
            }
            else{
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_low_rate_movie, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.ivCover);
                viewHolder.tvOverView = (TextView) convertView.findViewById(R.id.tvOverView);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                convertView.setTag(viewHolder);
            }
        } else viewHolder = (ViewHolder) convertView.getTag();

        if (getItemViewType(position) == 1){
            Glide.with(getContext())
                    .load(Constants.BASE_URL_IMAGE + movies.get(position).getBackdropPath())
                    .into(viewHolder.ivCover);
            viewHolder.ivCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieCallback.get().onHighRateImageClick(movies.get(position));
                }
            });
        } else{
            viewHolder.tvOverView.setText(movies.get(position).getOverview());
            viewHolder.tvTitle.setText(movies.get(position).getTitle());
            Configuration configuration = getContext().getResources()
                    .getConfiguration();
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(getContext())
                        .load(Constants.BASE_URL_IMAGE + movies.get(position).getPosterPath())
                        .placeholder(R.drawable.placeholder)
                        .dontAnimate()
                        .dontTransform()
                        .centerCrop()
                        .into(viewHolder.ivCover);
            }else{
                Glide.with(getContext())
                        .load(Constants.BASE_URL_IMAGE + movies.get(position).getBackdropPath())
                        .into(viewHolder.ivCover);
            }

            viewHolder.ivCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieCallback.get().onLowRateImageClick(movies.get(position));
                }
            });
        }

        return convertView;
    }

    private class ViewHolder{
        public TextView tvTitle;
        public TextView tvOverView;
        public ImageView ivCover;
    }
}
