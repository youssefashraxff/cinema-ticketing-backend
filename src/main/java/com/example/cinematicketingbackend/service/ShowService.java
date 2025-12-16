package com.example.cinematicketingbackend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.cinematicketingbackend.exception.InvalidHallStatusException;
import com.example.cinematicketingbackend.exception.InvalidShowTimeException;
import com.example.cinematicketingbackend.exception.MaxShowsPerHallException;
import com.example.cinematicketingbackend.exception.MovieNotFoundException;
import com.example.cinematicketingbackend.exception.ShowOverlapException;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Movie;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.util.TimeUtils;

public class ShowService {
    private Map<Hall, List<Show>> hallShowsMap; // Efficient hall-based show lookups
    private MovieService movieService; // Reference to MovieService
    private HallService hallService; // Reference to HallService for status checks

    public ShowService() {
        this.hallShowsMap = new HashMap<>();
    }


    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void setHallService(HallService hallService) {
        this.hallService = hallService;
    }

    public void createShow(String startTime, String finishTime, Hall hall) {
        // Check hall status - only ACTIVE halls can have shows scheduled
        if (hallService != null && !hallService.isHallAvailable(hall.getHallId())) {
            throw new InvalidHallStatusException(
                    "Cannot schedule show in hall " + hall.getHallId() +
                            ". Hall status is: " + hall.getHallStatus() +
                            ". Only ACTIVE halls can have shows scheduled.");
        }

        // Check if hall has reached maximum number of shows
        List<Show> currentHallShows = hallShowsMap.get(hall);
        int currentShowsCount = (currentHallShows != null) ? currentHallShows.size() : 0;

        if (currentShowsCount >= hall.MaxNumOfShowsPerHall) {
            throw new MaxShowsPerHallException(hall.getHallId(), hall.MaxNumOfShowsPerHall);
        }

        Show newShow = new Show(startTime, finishTime, hall);

        // If hall does not exist, create list and add show
        if (!hallShowsMap.containsKey(hall)) {
            List<Show> shows = new ArrayList<>();
            shows.add(newShow);
            hallShowsMap.put(hall, shows);
            return;
        }

        // Hall exists → check overlap
        List<Show> existingShows = hallShowsMap.get(hall);

        for (Show existingShow : existingShows) {
            if (TimeUtils.isOverlapping(
                    startTime,
                    finishTime,
                    existingShow.getStartTime(),
                    existingShow.getFinishTime())) {

                throw new ShowOverlapException(
                        "Show overlaps with an existing show in this hall");
            }
        }

        // Directly add to map value
        hallShowsMap.get(hall).add(newShow);
       
    }

    public void deleteShow(int movieId, String startTime, Hall hall) {
        if (movieService == null) {
            throw new IllegalStateException("MovieService is not set");
        }

        Movie movie = movieService.getMovie(movieId);
        if (movie == null) {
            throw new MovieNotFoundException(movieId);
        }

        // Find and remove show from movie
        Show showToRemove = null;
        for (Show show : movie.getShows()) {
            if (show.getHall().equals(hall) && show.getStartTime().equals(startTime)) {
                showToRemove = show;
                break;
            }
        }

        if (showToRemove != null) {
            movie.removeShow(showToRemove);

            // Remove from hall-based map
            List<Show> hallShows = hallShowsMap.get(hall);
            if (hallShows != null) {
                hallShows.remove(showToRemove);
            }
        }
    }

    public void addShowToMovie(int movieId, Show show) {
        if (movieService == null) {
            throw new IllegalStateException("MovieService is not set");
        }
        // Delegate to MovieService which handles validation
        movieService.addShow(movieId, show);
    }

    public boolean checkHallAvailability(Hall hall, String startTime, String finishTime) {
        if (hall == null) {
            return false;
        }

        // Validate time format
        if (!TimeUtils.validateTimeFormat(startTime) || !TimeUtils.validateTimeFormat(finishTime)) {
            throw new InvalidShowTimeException("Invalid time format. Expected format: yyyy-MM-dd HH:mm");
        }

        // Validate finish time is after start time
        if (!TimeUtils.isTimeAfter(finishTime, startTime)) {
            throw new InvalidShowTimeException("Finish time must be after start time");
        }

        // Get all shows for this hall
        List<Show> hallShows = hallShowsMap.get(hall);
        if (hallShows == null || hallShows.isEmpty()) {
            return true; // Hall is available if no shows scheduled
        }

        // Check for overlapping shows
        for (Show existingShow : hallShows) {
            if (TimeUtils.isOverlapping(
                    existingShow.getStartTime(), existingShow.getFinishTime(),
                    startTime, finishTime)) {
                return false; // Overlap found
            }
        }

        return true; // No overlaps, hall is available
    }

    public List<Show> getAllShows() {
        List<Show> allShows = new ArrayList<>();
        for (List<Show> shows : hallShowsMap.values()) {
            allShows.addAll(shows);
        }
        return allShows;
    }

    public List<Show> getShowsByHall(int hallId) {
        for (Map.Entry<Hall, List<Show>> entry : hallShowsMap.entrySet()) {
            if (entry.getKey().getHallId() == hallId) {
                return new ArrayList<>(entry.getValue());
            }
        }
        return new ArrayList<>(); // Return empty list if hall not found
    }

}
