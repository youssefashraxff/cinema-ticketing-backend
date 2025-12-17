package com.example.cinematicketingbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.exception.InvalidRatingException;
import com.example.cinematicketingbackend.exception.InvalidSearchCriteriaException;
import com.example.cinematicketingbackend.exception.MovieNotFoundException;
import com.example.cinematicketingbackend.model.Movie;
import com.example.cinematicketingbackend.model.MovieCategoryFlyweight;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.patterns.MovieCategoryFlyweigth;
import com.example.cinematicketingbackend.repository.FacadeRepository;
import com.example.cinematicketingbackend.util.TimeUtils;

@Service
public class MovieService {

    private final FacadeRepository facade;

    public MovieService(FacadeRepository facade) {
        this.facade = facade;
    }

    public Movie createMovie(
            String name,
            int duration,
            String language,
            double rating,
            String categoryType,
            String description,
            String trailerUrl,
            String verticalPoster,
            String horizontalPoster,
            int ageRestriction
    ) {

        if (rating < 0 || rating > 10) {
            throw new InvalidRatingException("Rating must be between 0 and 10");
        }

        int newMovieId = facade.movies().findAll().stream()
                .mapToInt(Movie::getMovieId)
                .max()
                .orElse(0) + 1;

        MovieCategoryFlyweight category =
                MovieCategoryFlyweigth.getMovieCategory(categoryType, ageRestriction);

        Movie movie = new Movie.Builder()
                .movieId(newMovieId)
                .name(name)
                .duration(duration)
                .language(language)
                .rating(rating)
                .description(description)
                .trailerURL(trailerUrl)
                .verticalPoster(verticalPoster)
                .horizontalPoster(horizontalPoster)
                .category(category)
                .build();
        return facade.movies().save(movie);
    }

    public void deleteMovie(int movieId) {
        Movie movie = facade.movies()
                .findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));

        facade.movies().deleteById(movieId);
    }

    public void addShow(int movieId, Show show) {

        facade.movies()
                .findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));

        if (!TimeUtils.validateTimeFormat(show.getStartTime()) ||
            !TimeUtils.validateTimeFormat(show.getFinishTime())) {
            throw new InvalidSearchCriteriaException("Invalid time format");
        }

        if (!TimeUtils.isTimeAfter(show.getFinishTime(), show.getStartTime())) {
            throw new InvalidSearchCriteriaException("Finish time must be after start time");
        }

        facade.shows().save(show);
    }


    public List<Movie> getAllMovies() {
        return facade.movies().findAll();
    }

    public Movie getMovie(int movieId) {
        return facade.movies()
                .findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    public List<Movie> getMoviesByCategory(String type) {
        if (type == null || type.isBlank()) return new ArrayList<>();

        return facade.movies().findAll().stream()
                .filter(m -> m.getMovieCategory() != null &&
                             type.equalsIgnoreCase(m.getMovieCategory().getType()))
                .collect(Collectors.toList());
    }

    public List<Movie> searchMoviesByLanguage(String language) {
        if (language == null || language.isBlank()) return new ArrayList<>();

        return facade.movies().findAll().stream()
                .filter(m -> language.equalsIgnoreCase(m.getLanguage()))
                .collect(Collectors.toList());
    }

    public List<Movie> searchMoviesByDuration(int min, int max) {
        if (min < 0 || max < 0 || min > max) {
            throw new InvalidSearchCriteriaException("Invalid duration range");
        }

        return facade.movies().findAll().stream()
                .filter(m -> m.getDuration() >= min && m.getDuration() <= max)
                .collect(Collectors.toList());
    }

    public List<Movie> searchMoviesByRating(double min, double max) {
        if (min < 0 || max > 10 || min > max) {
            throw new InvalidSearchCriteriaException("Invalid rating range");
        }

        return facade.movies().findAll().stream()
                .filter(m -> m.getRating() >= min && m.getRating() <= max)
                .collect(Collectors.toList());
    }
}
