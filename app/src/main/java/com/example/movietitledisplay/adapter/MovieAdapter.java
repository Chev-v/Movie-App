package com.example.movietitledisplay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietitledisplay.R;
import com.example.movietitledisplay.model.Movie;

import java.util.List;

// Adapter that helps display a list of movies in the RecyclerView
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    // List of movie data to show in the UI
    private List<Movie> movieList;

    // A custom listener that tells us when a user taps on a movie
    private final OnItemClickListener listener;

    // Constructor that sets up the data list and the click listener
    public MovieAdapter(List<Movie> movieList, OnItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    // Interface that lets MainActivity know which movie was tapped
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    // This method creates each list item layout (movie row) from XML
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the movie_title.xml layout for each row
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_title, parent, false);
        return new MovieViewHolder(view);
    }

    // This method binds actual movie data into each row of the list
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position); // Get movie at current row
        holder.bind(movie, listener); // Send it to the ViewHolder to display
    }

    // Tells RecyclerView how many movie rows to show
    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    // Call this method to refresh the list of movies being shown
    public void updateMovies(List<Movie> newList) {
        this.movieList = newList;
        notifyDataSetChanged(); // Tells RecyclerView to redraw everything
    }

    // This class holds the views for each movie row: title, year, type
    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        // UI elements inside the movie row
        TextView textTitle, textYear, textType;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            // Hook up the XML views with Java variables
            textTitle = itemView.findViewById(R.id.textTitle);
            textYear = itemView.findViewById(R.id.textYear);
            textType = itemView.findViewById(R.id.textType);
        }

        // Bind the movie data to the views, and set the click listener
        public void bind(final Movie movie, final OnItemClickListener listener) {
            textTitle.setText(movie.getTitle());       // Movie title (e.g., Inception)
            textYear.setText(movie.getYear());         // Release year (e.g., 2010)
            textType.setText("Type: " + movie.getType()); // Type (e.g., movie, series)

            // When user taps this row, trigger the callback to MainActivity
            itemView.setOnClickListener(v -> listener.onItemClick(movie));
        }
    }
}
