package com.example.movietitledisplay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movietitledisplay.model.Movie;
import com.example.movietitledisplay.repository.MovieRepository;

import java.util.List;

// ViewModel acts as the bridge between UI and data (repository)
// Helps the activity stay clean and survive screen rotations
public class MovieViewModel extends ViewModel {

    // Repository handles the actual data fetching
    private final MovieRepository repository = new MovieRepository();

    // This returns live data that activities/fragments can observe
    public LiveData<List<Movie>> getMovies() {
        return repository.getMovieList();
    }

    // Called by the UI when a user wants to search for a movie
    public void searchMovies(String query) {
        repository.searchMovies(query); // Delegate the work to repository
    }
}