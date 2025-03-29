package com.example.movietitledisplay.model;

// This class represents a single Movie object
public class Movie {

    // Basic info we show in the search results
    public String Title;
    public String Year;
    public String imdbID;
    public String Type;
    public String Poster;

    // Additional info shown in the details screen
    public String Rated;
    public String Runtime;
    public String Genre;
    public String Plot;

    // These getter methods are used to access the data elsewhere in the app
    public String getTitle() { return Title; }
    public String getYear() { return Year; }
    public String getImdbID() { return imdbID; }
    public String getType() { return Type; }
    public String getPoster() { return Poster; }

    public String getRated() { return Rated; }
    public String getRuntime() { return Runtime; }
    public String getGenre() { return Genre; }
    public String getPlot() { return Plot; }
}
