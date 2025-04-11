package com.example.movietitledisplay.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movietitledisplay.model.Movie;
import com.example.movietitledisplay.repository.MovieRepository;

import java.util.List;

// ViewModel sits between the UI and the Repository.
// Its job is to provide data to the UI in a way that's lifecycle-safe.
public class MovieViewModel extends ViewModel {

    // The Repository is responsible for talking to the OMDB API
    private final MovieRepository repository = new MovieRepository();

    // This method returns LiveData that holds a list of movies
    // The UI can observe this and auto-update when new data is available
    public LiveData<List<Movie>> getMovies() {
        return repository.getMovieList();
    }

    // This method gets called when the user performs a search
    // It passes the search query to the Repository to fetch results
    public void searchMovies(String query) {
        repository.searchMovies(query); // Delegate actual API call to the repo
    }
}
