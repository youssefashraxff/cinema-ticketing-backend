package com.example.cinematicketingbackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cinematicketingbackend.dto.MovieRequestDTO;
import com.example.cinematicketingbackend.model.Movie;
import com.example.cinematicketingbackend.service.MovieService;



@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }   

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{movieId}")
    public Movie getMovie(@PathVariable int movieId) {
        return movieService.getMovie(movieId);
    }

   @PostMapping
public Movie createMovie(@RequestBody MovieRequestDTO dto) {

    return movieService.createMovie(
            dto.getName(),
            dto.getDuration(),
            dto.getLanguage(),
            dto.getRating(),
            dto.getCategoryType(),
            dto.getMovieDescription(),
            dto.getTrailerURL(),
            dto.getVerticalPoster(),
            dto.getHorizontalPoster(),
            dto.getAgeRestriction()
    );
}

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable int movieId) {
        movieService.deleteMovie(movieId);
    }

    @GetMapping("/category")
    public List<Movie> getMoviesByCategory(@RequestParam String type) {
        return movieService.getMoviesByCategory(type);
    }

    @GetMapping("/search/name")
    public Movie getMoviesByName(@RequestParam String name) {
        return movieService.getMovieByName(name);
    }

    @GetMapping("/search/language")
    public List<Movie> searchByLanguage(@RequestParam String language) {
        return movieService.searchMoviesByLanguage(language);
    }

    @GetMapping("/search/duration")
    public List<Movie> searchByDuration(
            @RequestParam int min,
            @RequestParam int max) {

        return movieService.searchMoviesByDuration(min, max);
    }

    @GetMapping("/search/rating")
    public List<Movie> searchByRating(
            @RequestParam double min,
            @RequestParam double max) {

        return movieService.searchMoviesByRating(min, max);
    }
}