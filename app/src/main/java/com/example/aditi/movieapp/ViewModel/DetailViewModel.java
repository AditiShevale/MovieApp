package com.example.aditi.movieapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.aditi.movieapp.Model.Movies.MoviesResult;
import com.example.aditi.movieapp.Model.Movies.Reviews.ReviewResult;
import com.example.aditi.movieapp.Model.Movies.Trailer.TrailerResult;
import com.example.aditi.movieapp.Repository;

import java.util.List;

public class DetailViewModel extends ViewModel {

    LiveData<List<MoviesResult>> mData;
    LiveData<List<ReviewResult>> mReviewData;
    LiveData<List<TrailerResult>> mTrailerData;

    private Repository mRespository;

    public DetailViewModel(int id, Context context) {
        mRespository = new Repository(id, context);

    }

    public void insert(MoviesResult result) {
        mRespository.insert(result);
    }

    public LiveData<List<MoviesResult>> getAllFav() {
        return mData;
    }

    public LiveData<List<ReviewResult>> getAllReviews() {
        mReviewData = mRespository.mReviewLiveData();
        return mReviewData;
    }

    public LiveData<List<TrailerResult>> getAllTrailers() {
        mTrailerData = mRespository.mTrailerLiveData();
        return mTrailerData;
    }


}

