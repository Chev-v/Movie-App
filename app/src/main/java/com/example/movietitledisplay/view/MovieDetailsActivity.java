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

// This screen shows detailed info about one selected movie
public class MovieDetailsActivity extends AppCompatActivity {

    // UI elements to display movie info and poster
    ImageView posterImage;
    TextView titleText, yearText, typeText, ratedText, runtimeText, genreText, plotText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Link the layout views to variables so we can fill them later
        posterImage = findViewById(R.id.posterImage);
        titleText = findViewById(R.id.titleText);
        yearText = findViewById(R.id.yearText);
        typeText = findViewById(R.id.typeText);
        ratedText = findViewById(R.id.ratedText);
        runtimeText = findViewById(R.id.runtimeText);
        genreText = findViewById(R.id.genreText);
        plotText = findViewById(R.id.plotText);

        // Get the IMDb ID from the Intent that opened this screen
        String imdbId = getIntent().getStringExtra("imdbID");

        if (imdbId != null) {
            // If we have a movie ID, go fetch the full movie details
            fetchMovieDetails(imdbId);
        } else {
            // If no ID was passed, show error and close screen
            Toast.makeText(this, "Missing movie ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Call OMDB API to get detailed data for the selected movie
    private void fetchMovieDetails(String imdbId) {
        // Create API service instance
        OmdbApiService apiService = RetrofitClient.getRetrofitInstance().create(OmdbApiService.class);

        // Build API call to fetch details by IMDb ID
        Call<Movie> call = apiService.getMovieDetails(Constants.API_KEY, imdbId);

        // Make the call in the background
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                // If we got data back successfully
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();

                    // Plug all the movie data into the screen
                    titleText.setText(movie.getTitle());
                    yearText.setText("Year: " + movie.getYear());
                    typeText.setText("Type: " + movie.getType());
                    ratedText.setText("Rated: " + movie.getRated());
                    runtimeText.setText("Runtime: " + movie.getRuntime());
                    genreText.setText("Genre: " + movie.getGenre());
                    plotText.setText("Overview: " + movie.getPlot());

                    // Load the movie poster into the ImageView using Picasso
                    Picasso.get().load(movie.getPoster()).into(posterImage);
                } else {
                    // Something went wrong with the response
                    Toast.makeText(MovieDetailsActivity.this, "Failed to load details", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                // Total failure (maybe no internet or bad request)
                Toast.makeText(MovieDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
