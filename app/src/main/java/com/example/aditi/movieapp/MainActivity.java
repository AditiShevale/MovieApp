package com.example.aditi.movieapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.aditi.movieapp.Adapter.Movie;
import com.example.aditi.movieapp.Adapter.RecyclerMovie;

import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private RecyclerMovie mRecyclerMovie;

    private ProgressBar mProgressBar;

    private static final String POPULAR_KEY="popular";
    public static final String TOP_RATED_KEY="top_rated";


    private final static String MENUSelected = "selected";
    private int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressBar);


        RecyclerView.LayoutManager mLayoutManager = new
                GridLayoutManager(MainActivity.this, 2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (isOnline()){
            build(POPULAR_KEY);
        }else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }


        if (savedInstanceState != null) {
            selected = savedInstanceState.getInt(MENUSelected);

            if (selected == -1) {
                build(POPULAR_KEY);
            } else if (selected == R.id.highest_Rated) {
                build(TOP_RATED_KEY);
            } else {
                build(POPULAR_KEY);
            }

        }
    }

    public class MovieDBQueryTask extends AsyncTask<URL, Void, List<Movie>>  {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(URL... urls) {

            List<Movie> result = NetworkUtils.fetchMovieData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {


            mProgressBar.setVisibility(View.INVISIBLE);
           mRecyclerMovie = new RecyclerMovie(MainActivity.this,movies,
                   new RecyclerMovie.ListItemClickListener(){
                       @Override

                       public void onListItemClick(Movie movie) {
                           Intent intent = new Intent(MainActivity.this,
                                   Details.class);
                           intent.putExtra("data",movie);
                           startActivity(intent);


                       }
                   });
            mRecyclerView.setAdapter(mRecyclerMovie);
            mRecyclerMovie.notifyDataSetChanged();


        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENUSelected, selected);
        super.onSaveInstanceState(outState);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.highest_Rated:
                build(TOP_RATED_KEY);
                selected = id;

                break;

            case R.id.most_popular:
                build(POPULAR_KEY);
                selected = id;
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private URL build(String sort) {
        URL final_Url = NetworkUtils.buildURl(sort);
        new MovieDBQueryTask().execute(final_Url);
        return final_Url;
    }
    // Function for checking NetworkUtils connection

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
