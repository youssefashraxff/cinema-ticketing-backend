package com.example.cinematicketingbackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}