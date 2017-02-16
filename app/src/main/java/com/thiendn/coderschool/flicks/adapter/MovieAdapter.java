package com.thiendn.coderschool.flicks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thiendn.coderschool.flicks.R;
import com.thiendn.coderschool.flicks.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thiendn on 16/02/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie>{

    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> list){
        super(context, -1);
        this.movies = list;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.ivCover);
            viewHolder.tvOverView = (TextView) convertView.findViewById(R.id.tvOverView);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        }else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tvOverView.setText(movies.get(position).getOverview());
        viewHolder.tvTitle.setText(movies.get(position).getTitle());
        Glide.with(getContext())
                .load("https://image.tmdb.org/t/p/w342" + movies.get(position).getPosterPath())
                .into(viewHolder.ivCover);
        return convertView;
    }

    private class ViewHolder{
        public TextView tvTitle;
        public TextView tvOverView;
        public ImageView ivCover;
    }
}
