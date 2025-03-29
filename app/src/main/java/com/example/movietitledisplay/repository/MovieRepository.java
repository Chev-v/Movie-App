package com.example.movietitledisplay.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movietitledisplay.model.Movie;
import com.example.movietitledisplay.model.MovieSearchResponse;
import com.example.movietitledisplay.network.OmdbApiService;
import com.example.movietitledisplay.network.RetrofitClient;
import com.example.movietitledisplay.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// This class is responsible for getting movie data from the OMDB API
// It sits between the ViewModel and the API, as part of the MVVM structure
public class MovieRepository {

    // Holds the live movie list we will update when data comes back from the API
    private final MutableLiveData<List<Movie>> movieListLiveData = new MutableLiveData<>();

    // Allows the ViewModel to observe changes to the movie list
    public LiveData<List<Movie>> getMovieList() {
        return movieListLiveData;
    }

    // This method performs the actual network call to the OMDB API
    public void searchMovies(String query) {
        // Creates an instance of the API interface
        OmdbApiService apiService = RetrofitClient.getRetrofitInstance().create(OmdbApiService.class);

        // Set up the call to search movies using the API key and query
        Call<MovieSearchResponse> call = apiService.searchMovies(Constants.API_KEY, query);

        // Make the request asynchronously
        call.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                // If successful and data exists, update the live data with the movie list
                if (response.isSuccessful() && response.body() != null && response.body().getSearch() != null) {
                    movieListLiveData.postValue(response.body().getSearch());
                } else {
                    // Otherwise, log the issue and clear the list
                    Log.e("MovieRepository", "Empty or failed response: " + response.code());
                    movieListLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                // If the network call completely fails, show an error and clear the list
                Log.e("MovieRepository", "API call failed: " + t.getMessage());
                movieListLiveData.postValue(null);
            }
        });
    }
}
