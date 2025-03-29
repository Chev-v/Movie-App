package com.example.movietitledisplay.model;

import java.util.List;

// This class matches the structure of the OMDB API's search response
public class MovieSearchResponse {

    // OMDB returns a field called "Search" which contains a list of movie objects
    public List<Movie> Search;

    // Getter used to access the movie list in the repository/viewmodel
    public List<Movie> getSearch() {
        return Search;
    }
}
