package com.example.cinematicketingbackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.cinematicketingbackend.model.Movie;
import com.example.cinematicketingbackend.dto.MovieDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.cinematicketingbackend.patterns.MovieCategoryFlyweigth;
import com.example.cinematicketingbackend.model.MovieCategoryFlyweight;

@Repository
public class MovieRepository {

    private static final String FILE_PATH = "data/movies.json";

    private final FileManager fileManager;

    public MovieRepository() {
        this.fileManager = FileManager.getInstance();
    }

    private List<Movie> loadMovies() {
        try {
            List<MovieDTO> dtos = fileManager.read(
                    FILE_PATH,
                    new TypeReference<List<MovieDTO>>() {}
            );

            List<Movie> movies = new ArrayList<>();

            if (dtos != null) {
                for (MovieDTO dto : dtos) {
                    MovieCategoryFlyweight category =
                            MovieCategoryFlyweigth.getMovieCategory(
                                    dto.categoryType,
                                    dto.ageRestriction
                            );

                    Movie movie = new Movie.Builder()
                            .movieId(dto.movieId)
                            .name(dto.name)
                            .duration(dto.duration)
                            .language(dto.language)
                            .rating(dto.rating)
                            .description(dto.movieDescription)
                            .trailerURL(dto.trailerURL)
                            .verticalPoster(dto.verticalPoster)
                            .horizontalPoster(dto.horizontalPoster)
                            .category(category)
                            .build();

                    movies.add(movie);
                }
            }

            return movies;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveMovies(List<Movie> movies) {
        fileManager.write(FILE_PATH, movies);
    }

    public List<Movie> findAll() {
        return new ArrayList<>(loadMovies());
    }

    public Optional<Movie> findById(int id) {
        return loadMovies().stream()
                .filter(movie -> movie.getMovieId() == id)
                .findFirst();
    }

    public Movie save(Movie movie) {
        List<Movie> movies = loadMovies();

        Optional<Movie> existingMovie = movies.stream()
                .filter(m -> m.getMovieId() == movie.getMovieId())
                .findFirst();

        if (existingMovie.isPresent()) {
            int index = movies.indexOf(existingMovie.get());
            movies.set(index, movie);
        } else {
            movies.add(movie);
        }

        saveMovies(movies);
        return movie;
    }

    public void deleteById(int id) {
        List<Movie> movies = loadMovies();
        boolean removed = movies.removeIf(movie -> movie.getMovieId() == id);
        if (removed) {
            saveMovies(movies);
        }
    }
}