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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    // List of movies that will be displayed in the RecyclerView
    private List<Movie> movieList;

    // Interface to handle what happens when a movie is clicked
    private final OnItemClickListener listener;

    // Constructor to initialize movie list and click listener
    public MovieAdapter(List<Movie> movieList, OnItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    // This interface lets MainActivity respond when a user taps on a movie
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    // Creates the layout for each item in the RecyclerView
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_title, parent, false);
        return new MovieViewHolder(view);
    }

    // Binds data from each movie into the ViewHolder layout
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie, listener); // send the movie and listener to be used inside holder
    }

    // Returns how many movie items are in the list
    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    // Used to update the list of movies when data changes (like after a search)
    public void updateMovies(List<Movie> newList) {
        this.movieList = newList;
        notifyDataSetChanged(); // tells the RecyclerView to redraw the list
    }

    // This class holds the views for each movie item (title, year, type)
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textYear, textType;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textYear = itemView.findViewById(R.id.textYear);
            textType = itemView.findViewById(R.id.textType);
        }

        // Binds one movie's data into the layout, and sets up click event
        public void bind(final Movie movie, final OnItemClickListener listener) {
            textTitle.setText(movie.getTitle());
            textYear.setText(movie.getYear());
            textType.setText("Type: " + movie.getType());

            // When this movie row is clicked, trigger the listener
            itemView.setOnClickListener(v -> listener.onItemClick(movie));
        }
    }
}
