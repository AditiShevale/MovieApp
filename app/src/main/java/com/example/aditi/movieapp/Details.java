package com.example.aditi.movieapp;

import android.app.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aditi.movieapp.Adapter.Movie;
import com.squareup.picasso.Picasso;

public class Details extends Activity{
    private TextView txt_Title;
    private TextView txt_Plot;
    private TextView txt_Rating;
    private TextView txt_Release;
    private ImageView img_Poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        txt_Title = findViewById(R.id.title);
        txt_Plot = findViewById(R.id.plot);
        txt_Rating =findViewById(R.id.rating);
        txt_Release=findViewById(R.id.release);
        img_Poster=findViewById(R.id.imageView);




        getActionBar().setDisplayHomeAsUpEnabled(true);


      Movie movie =getIntent().getParcelableExtra("data");

       txt_Title.setText(movie.getTitle());
        txt_Plot.setText(movie.getOverview());
        txt_Rating.setText(movie.getVoteAverage() + "/10");
        txt_Release.setText(movie.getReleaseDate());
        Picasso.with(img_Poster.getContext()).load("https://image.tmdb.org/t/p/w500"
                + movie.getImage()).into(img_Poster);


    }
}
