package com.example.movietitledisplay.network;

import com.example.movietitledisplay.model.Movie;
import com.example.movietitledisplay.model.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// This interface defines how we talk to the OMDB API
public interface OmdbApiService {

    // This endpoint searches for movies by a keyword
    @GET("/")
    Call<MovieSearchResponse> searchMovies(
            @Query("apikey") String apiKey,
            @Query("s") String searchQuery
    );

    // This endpoint gets full details of a specific movie by IMDb ID
    @GET("/")
    Call<Movie> getMovieDetails(
            @Query("apikey") String apiKey,
            @Query("i") String imdbId
    );
}
