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

// The Repository layer manages data access and API calls.
// In MVVM, the ViewModel calls this class to fetch or update data.
public class MovieRepository {

    // LiveData list that holds the movies fetched from the API
    private final MutableLiveData<List<Movie>> movieListLiveData = new MutableLiveData<>();

    // This allows the ViewModel to observe changes to the movie list
    public LiveData<List<Movie>> getMovieList() {
        return movieListLiveData;
    }

    // This function is called when the user searches for a movie
    public void searchMovies(String query) {
        // Create an instance of the API service using Retrofit
        OmdbApiService apiService = RetrofitClient.getRetrofitInstance().create(OmdbApiService.class);

        // Build the actual API call to search movies using the query + API key
        Call<MovieSearchResponse> call = apiService.searchMovies(Constants.API_KEY, query);

        // Make the network call asynchronously (on a background thread)
        call.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                // If the response was successful and contains movie results
                if (response.isSuccessful() && response.body() != null && response.body().getSearch() != null) {
                    // Update the LiveData so the ViewModel and UI can react
                    movieListLiveData.postValue(response.body().getSearch());
                } else {
                    // If the response is empty or something is wrong, log it
                    Log.e("MovieRepository", "Empty or failed response: " + response.code());
                    movieListLiveData.postValue(null); // Clear the movie list in UI
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                // If the API call failed entirely (e.g., no internet), log the error
                Log.e("MovieRepository", "API call failed: " + t.getMessage());
                movieListLiveData.postValue(null); // Clear the movie list in UI
            }
        });
    }
}
