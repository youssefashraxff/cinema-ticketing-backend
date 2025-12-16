package com.example.cinematicketingbackend.service;

import java.util.List;

import com.example.cinematicketingbackend.exception.InvalidHallStatusException;
import com.example.cinematicketingbackend.exception.InvalidShowTimeException;
import com.example.cinematicketingbackend.exception.MaxShowsPerHallException;
import com.example.cinematicketingbackend.exception.MovieNotFoundException;
import com.example.cinematicketingbackend.exception.ShowOverlapException;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Movie;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.repository.ShowRepository;
import com.example.cinematicketingbackend.util.TimeUtils;

public class ShowService {
    private ShowRepository showRepository;
    private MovieService movieService;
    private HallService hallService;

    public ShowService() {
        this.showRepository = ShowRepository.getInstance();
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
        List<Show> currentHallShows = showRepository.findByHallId(hall.getHallId());
        int currentShowsCount = currentHallShows.size();

        if (currentShowsCount >= hall.MaxNumOfShowsPerHall) {
            throw new MaxShowsPerHallException(hall.getHallId(), hall.MaxNumOfShowsPerHall);
        }

        // Validate time format
        if (!TimeUtils.validateTimeFormat(startTime) || !TimeUtils.validateTimeFormat(finishTime)) {
            throw new InvalidShowTimeException("Invalid time format. Expected format: yyyy-MM-dd HH:mm");
        }

        // Validate finish time is after start time
        if (!TimeUtils.isTimeAfter(finishTime, startTime)) {
            throw new InvalidShowTimeException("Finish time must be after start time");
        }

        // Check for overlapping shows
        if (!isHallAvailableForTime(hall.getHallId(), startTime, finishTime)) {
            throw new ShowOverlapException("Show overlaps with an existing show in this hall");
        }

        Show newShow = new Show(startTime, finishTime, hall);
        
        // Save show using repository
        showRepository.save(newShow);
    }

    private boolean isHallAvailableForTime(int hallId, String startTime, String finishTime) {
        List<Show> hallShows = showRepository.findByHallId(hallId);
        
        for (Show existingShow : hallShows) {
            if (TimeUtils.isOverlapping(
                    startTime,
                    finishTime,
                    existingShow.getStartTime(),
                    existingShow.getFinishTime())) {
                return false;
            }
        }
        return true;
    }

    public void deleteShow(int movieId, String startTime, Hall hall) {
        if (movieService == null) {
            throw new IllegalStateException("MovieService is not set");
        }

        Movie movie = movieService.getMovie(movieId);
        if (movie == null) {
            throw new MovieNotFoundException(movieId);
        }

        // Remove show from repository
        boolean removed = showRepository.deleteByHallAndTime(hall.getHallId(), startTime);
        
        if (removed) {
            // Also remove from movie's show list
            Show showToRemove = null;
            for (Show show : movie.getShows()) {
                if (show.getHall().getHallId() == hall.getHallId() && 
                    show.getStartTime().equals(startTime)) {
                    showToRemove = show;
                    break;
                }
            }
            
            if (showToRemove != null) {
                movie.removeShow(showToRemove);
                // Update movie in repository
                movieService.updateMovieInfo(movieId, movie);
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

        return isHallAvailableForTime(hall.getHallId(), startTime, finishTime);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByHall(int hallId) {
        return showRepository.findByHallId(hallId);
    }
}