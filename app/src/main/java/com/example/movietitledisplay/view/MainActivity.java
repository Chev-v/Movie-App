package com.example.movietitledisplay.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietitledisplay.R;
import com.example.movietitledisplay.adapter.MovieAdapter;
import com.example.movietitledisplay.model.Movie;
import com.example.movietitledisplay.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ViewModel follows MVVM - connects UI with the data and keeps it alive through rotations
    private MovieViewModel movieViewModel;

    // Adapter binds the list of movies to the RecyclerView UI
    private MovieAdapter movieAdapter;

    // RecyclerView is the UI component that shows the movie list in a scrollable view
    private RecyclerView recyclerView;

    // EditText for user to type their movie search
    private EditText editSearch;

    // Button that starts the search process
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI components to their XML counterparts
        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);

        // Set up the adapter and tell it what happens when a user clicks a movie
        movieAdapter = new MovieAdapter(new ArrayList<>(), movie -> {
            // When a movie is clicked, open a new screen (MovieDetailsActivity)
            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
            // Pass the selected movie's IMDb ID to the next screen so we can fetch details
            intent.putExtra("imdbID", movie.getImdbID());
            startActivity(intent);
        });

        // Connect the adapter and layout manager to the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

        // Set up the ViewModel - this is where LiveData and business logic live
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // When the LiveData list of movies changes (after search), update the adapter
        movieViewModel.getMovies().observe(this, movies -> {
            if (movies != null && !movies.isEmpty()) {
                // If results exist, show them
                movieAdapter.updateMovies(movies);
            } else {
                // Otherwise, show a toast message and clear the list visually
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
                movieAdapter.updateMovies(new ArrayList<>());
            }
        });

        // When user taps the Search button
        btnSearch.setOnClickListener(v -> {
            // Get the search term from the input box
            String query = editSearch.getText().toString().trim();

            if (!query.isEmpty()) {
                // Pass the search term to ViewModel, which handles the rest
                movieViewModel.searchMovies(query);
            } else {
                // Tell the user to actually enter something
                Toast.makeText(MainActivity.this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
