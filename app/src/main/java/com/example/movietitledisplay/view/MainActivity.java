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

    // ViewModel to connect UI to data (follows MVVM pattern)
    private MovieViewModel movieViewModel;

    // Adapter to bind movie data to the RecyclerView
    private MovieAdapter movieAdapter;

    // RecyclerView to display list of movies
    private RecyclerView recyclerView;

    // EditText where user types the movie they want to search
    private EditText editSearch;

    // Search button to trigger the movie search
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connecting layout elements with code
        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);

        // Set up adapter and what happens when a movie is clicked
        movieAdapter = new MovieAdapter(new ArrayList<>(), movie -> {
            // When a movie is clicked, open the details screen
            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
            // Send the movie's IMDb ID so we can load full details on the next screen
            intent.putExtra("imdbID", movie.getImdbID());
            startActivity(intent);
        });

        // Attach layout manager and adapter to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

        // Set up the ViewModel to observe data changes
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Watch for changes in movie data, and update UI when new data arrives
        movieViewModel.getMovies().observe(this, movies -> {
            if (movies != null && !movies.isEmpty()) {
                movieAdapter.updateMovies(movies); // update UI with new movies
            } else {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
                movieAdapter.updateMovies(new ArrayList<>()); // show empty state
            }
        });

        // When search button is clicked
        btnSearch.setOnClickListener(v -> {
            String query = editSearch.getText().toString().trim(); // get what user typed
            if (!query.isEmpty()) {
                movieViewModel.searchMovies(query); // start the search
            } else {
                Toast.makeText(MainActivity.this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
