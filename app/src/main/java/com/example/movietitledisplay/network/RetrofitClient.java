package com.example.movietitledisplay.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// This class is used to build a Retrofit client to make network calls
public class RetrofitClient {

    // Holds the Retrofit instance so we don’t keep recreating it
    private static Retrofit retrofit;

    // Returns the Retrofit instance, building it if it's not already created
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.omdbapi.com/") // Base URL of the OMDB API
                    .addConverterFactory(GsonConverterFactory.create()) // Converts JSON to Java objects
                    .build();
        }
        return retrofit;
    }
}
