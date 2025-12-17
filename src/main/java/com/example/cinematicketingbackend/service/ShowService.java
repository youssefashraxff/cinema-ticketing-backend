package com.example.cinematicketingbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.exception.InvalidHallStatusException;
import com.example.cinematicketingbackend.exception.MaxShowsPerHallException;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.repository.FacadeRepository;

@Service
public class ShowService {

    private final FacadeRepository facade;

    public ShowService(FacadeRepository facade) {
        this.facade = facade;
    }

    public Show createShow(String startTime, String finishTime) {

        int newShowId = facade.shows().findAll().stream()
                .mapToInt(Show::getShowId)
                .max()
                .orElse(0) + 1;

        Show show = new Show(newShowId, startTime, finishTime);
        return facade.shows().save(show);
    }

    public void assignShowToHall(int showId, int hallId) {

        Show show = facade.shows()
                .findById(showId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Show not found: " + showId));

        Hall hall = facade.halls()
                .findById(hallId)
                .orElseThrow(() ->
                        new InvalidHallStatusException(
                                "Hall not found: " + hallId));

        if (!"ACTIVE".equalsIgnoreCase(hall.getHallStatus())) {
            throw new InvalidHallStatusException(
                    "Hall " + hallId + " is not ACTIVE");
        }

        List<Show> hallShows = facade.shows().findAll().stream()
                .filter(s -> s.getHallId() == hallId)
                .collect(Collectors.toList());

        if (hallShows.size() >= hall.MaxNumOfShowsPerHall) {
            throw new MaxShowsPerHallException(
                    hallId, hall.MaxNumOfShowsPerHall);
        }

        for (Show existingShow : hallShows) {
            if (com.example.cinematicketingbackend.util.TimeUtils.isOverlapping(
                    show.getStartTime(),
                    show.getFinishTime(),
                    existingShow.getStartTime(),
                    existingShow.getFinishTime())) {

                throw new com.example.cinematicketingbackend.exception.ShowOverlapException(
                        "Show overlaps with an existing show in this hall");
            }
        }

        show.setHallId(hallId);
        facade.shows().save(show);
    }

    public void assignMovieToShow(int showId, int movieId) {

        Show show = facade.shows()
                .findById(showId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Show not found: " + showId));

        facade.movies()
                .findById(movieId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Movie not found: " + movieId));

        show.setMovieId(movieId);
        facade.shows().save(show);
    }

    public void deleteShow(int showId) {
        facade.shows().delete(showId);
    }

    public List<Show> getAllShows() {
        return facade.shows().findAll();
    }

    public List<Show> getShowsByMovie(int MovieId){
        return facade.shows().findAll().stream()
                .filter(s -> s.getMovieId() == MovieId)
                .collect(Collectors.toList());
    }

    public List<Show> getShowsByHall(int hallId) {
        return facade.shows().findAll().stream()
                .filter(s -> s.getHallId() == hallId)
                .collect(Collectors.toList());
    }
}
