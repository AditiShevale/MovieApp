package com.example.aditi.movieapp;



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




import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mrecyclerView;

    private Re

   private ProgressBar mProgressBar;



   private final static String MENUSelected = "selected";
   private int selected = -1;
   MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mrecyclerView =findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressBar);



        RecyclerView.LayoutManager mLayoutManager = new
                GridLayoutManager(MainActivity.this,2);

        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());
        build("popularityDec");

        if(savedInstanceState !=null)
        {
            selected=savedInstanceState.getInt(MENUSelected);

            if(selected==-1)
            {
                build("popularity.desc");
            }
            else if (selected==R.id.highest_Rated)
            {
                build("vote_count.desc");
            }
            else
            {
                build("popularity.desc");
            }

        }
    }

    public  class  MovieDBQueryTask extends AsyncTask<URL,Void,List<Movie>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(URL... urls) {
            List<Movie> result = Network.fetchMovieData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mrecyclerView



        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENUSelected, selected);
        super.onSaveInstanceState(outState);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch (id) {
            case R.id.highest_Rated:
                build("vote_count.desc");
                selected=id;

                break;

            case R.id.most_popular:
                build("popularity.desc");
                selected=id;
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private URL build(String sort) {
        URL final_Url = Network.buildURl(sort);
        new MovieDBQueryTask().execute(final_Url);
        return final_Url;
    }
}
