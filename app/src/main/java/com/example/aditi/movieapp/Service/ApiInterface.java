package com.example.aditi.movieapp.Service;

import com.example.aditi.movieapp.Model.Movies.Movies;
import com.example.aditi.movieapp.Model.Movies.Reviews.Reviews;
import com.example.aditi.movieapp.Model.Movies.Trailer.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    // query for movies

    @GET("movie/{filter}")
    Call<Movies> getMovies(@Path("filter") String filter, @Query("api_key") String apiKey);


    // query for movie trailers

    @GET("movie/{id}/reviews")
    Call<Reviews> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);

    // query for movie trailers

    @GET("movie/{id}/videos")
    Call<Trailers> getMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey);


}
