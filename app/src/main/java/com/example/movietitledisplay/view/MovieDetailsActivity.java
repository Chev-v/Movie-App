package com.example.movietitledisplay.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movietitledisplay.R;
import com.example.movietitledisplay.model.Movie;
import com.example.movietitledisplay.network.OmdbApiService;
import com.example.movietitledisplay.network.RetrofitClient;
import com.example.movietitledisplay.utils.Constants;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// This screen shows full details about a selected movie
public class MovieDetailsActivity extends AppCompatActivity {

    // UI elements to show poster and movie info
    ImageView posterImage;
    TextView titleText, yearText, typeText, ratedText, runtimeText, genreText, plotText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Connect XML views to Java variables
        posterImage = findViewById(R.id.posterImage);
        titleText = findViewById(R.id.titleText);
        yearText = findViewById(R.id.yearText);
        typeText = findViewById(R.id.typeText);
        ratedText = findViewById(R.id.ratedText);
        runtimeText = findViewById(R.id.runtimeText);
        genreText = findViewById(R.id.genreText);
        plotText = findViewById(R.id.plotText);

        // Get the IMDb ID from the previous screen
        String imdbId = getIntent().getStringExtra("imdbID");

        if (imdbId != null) {
            // Make the API call to load full details for this movie
            fetchMovieDetails(imdbId);
        } else {
            // Show error if no ID was passed
            Toast.makeText(this, "Missing movie ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Calls OMDB API to get detailed movie data
    private void fetchMovieDetails(String imdbId) {
        OmdbApiService apiService = RetrofitClient.getRetrofitInstance().create(OmdbApiService.class);
        Call<Movie> call = apiService.getMovieDetails(Constants.API_KEY, imdbId);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();

                    // Set all the views using the data from API
                    titleText.setText(movie.getTitle());
                    yearText.setText("Year: " + movie.getYear());
                    typeText.setText("Type: " + movie.getType());
                    ratedText.setText("Rated: " + movie.getRated());
                    runtimeText.setText("Runtime: " + movie.getRuntime());
                    genreText.setText("Genre: " + movie.getGenre());
                    plotText.setText("Overview: " + movie.getPlot());

                    // Load the poster using Picasso
                    Picasso.get().load(movie.getPoster()).into(posterImage);
                } else {
                    Toast.makeText(MovieDetailsActivity.this, "Failed to load details", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                // If something went wrong with the API call
                Toast.makeText(MovieDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
