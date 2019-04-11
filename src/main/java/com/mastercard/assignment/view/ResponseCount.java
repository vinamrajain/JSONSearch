package com.mastercard.assignment.view;


import java.util.List;

/**
 * View class for output response of the search controller. Jackson library will automatically
 * convert the Plain Old Java Object to JSON.
 *
 */
public class ResponseCount {

    String searchTerm;
    List<String> movies;
    long count;

    public ResponseCount() {

    }

    public ResponseCount(String searchTerm, List<String> movies, long count) {
        this.searchTerm = searchTerm;
        this.movies = movies;
        this.count = count;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<String> getMovies() {
        return movies;
    }

    public void setMovies(List<String> movies) {
        this.movies = movies;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "ResponseCount{" +
                "searchTerm='" + searchTerm + '\'' +
                ", movies=" + movies +
                ", count=" + count +
                '}';
    }
}
