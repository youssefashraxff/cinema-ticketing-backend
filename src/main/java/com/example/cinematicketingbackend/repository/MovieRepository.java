package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.Movie;
import com.example.cinematicketingbackend.util.FileHandlerManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepository {
    private static MovieRepository instance;
    private List<Movie> movies;
    private final FileHandlerManager fileHandler;
    private final Path moviesFilePath;

    private MovieRepository() {
        this.fileHandler = FileHandlerManager.getInstance();
        this.moviesFilePath = Paths.get("src/main/resources/data/movies.json");
        loadMoviesFromJson();
    }

    public static synchronized MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private void loadMoviesFromJson() {
        try {
            // Use TypeReference to properly read List<Movie>
            movies = fileHandler.readList(moviesFilePath, new TypeReference<List<Movie>>() {});
        } catch (Exception e) {
            System.err.println("Error loading movies from file: " + e.getMessage());
            movies = new ArrayList<>();
        }
    }

    public List<Movie> findAll() {
        return new ArrayList<>(movies);
    }

    public Optional<Movie> findById(int movieId) {
        return movies.stream()
                .filter(movie -> movie.getMovieId() == movieId)
                .findFirst();
    }

    public boolean existsById(int movieId) {
        return movies.stream().anyMatch(movie -> movie.getMovieId() == movieId);
    }

    public List<Movie> findByLanguage(String language) {
        if (language == null || language.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (language.trim().equalsIgnoreCase(movie.getLanguage())) {
                result.add(movie);
            }
        }
        return result;
    }

    public List<Movie> findByRatingBetween(double minRating, double maxRating) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getRating() >= minRating && movie.getRating() <= maxRating) {
                result.add(movie);
            }
        }
        return result;
    }

    public List<Movie> findByDurationBetween(int minDuration, int maxDuration) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getDuration() >= minDuration && movie.getDuration() <= maxDuration) {
                result.add(movie);
            }
        }
        return result;
    }

    public long count() {
        return movies.size();
    }

    // FIXED: Save method that preserves existing data
    public void save(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }

        // Check if movie already exists
        Optional<Movie> existingMovie = findById(movie.getMovieId());
        
        if (existingMovie.isPresent()) {
            // Update existing movie
            Movie toUpdate = existingMovie.get();
            toUpdate.setName(movie.getName());
            toUpdate.setDuration(movie.getDuration());
            toUpdate.setLanguage(movie.getLanguage());
            toUpdate.setRating(movie.getRating());
            toUpdate.setMovieCategory(movie.getMovieCategory());
            toUpdate.setTrailerUrl(movie.getTrailerUrl());
            toUpdate.setVerticalPoster(movie.getVerticalPoster());
            toUpdate.setHorizontalPoster(movie.getHorizontalPoster());
            toUpdate.setShows(movie.getShows());
        } else {
            // Add new movie
            movies.add(movie);
        }
        
        saveAllToFile();
    }

    // FIXED: Proper saveAll method that doesn't delete existing data
    public void saveAll(List<Movie> moviesToSave) {
        if (moviesToSave == null || moviesToSave.isEmpty()) {
            return;
        }

        for (Movie movie : moviesToSave) {
            // Remove existing movie with same ID if it exists
            movies.removeIf(m -> m.getMovieId() == movie.getMovieId());
            // Add the movie
            movies.add(movie);
        }
        
        saveAllToFile();
    }

    // FIXED: Separate method for writing to file
    private void saveAllToFile() {
        try {
            fileHandler.write(moviesFilePath, movies);
        } catch (Exception e) {
            System.err.println("Error saving movies to file: " + e.getMessage());
            throw new RuntimeException("Failed to save movies", e);
        }
    }

    public boolean deleteById(int movieId) {
        boolean removed = movies.removeIf(movie -> movie.getMovieId() == movieId);
        if (removed) {
            saveAllToFile();
        }
        return removed;
    }
}