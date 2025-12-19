package com.example.cinematicketingbackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.service.ShowService;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    } 

    @GetMapping
    public List<Show> getAllShows() {

        return showService.getAllShows();
    }
    @GetMapping("/{showId}")
    public Show getShowById(@PathVariable int showId) {
        return showService.getShow(showId);
    }
    

    @PostMapping
    public Show createShow(
            @RequestParam String startTime,
            @RequestParam String finishTime) {

        return showService.createShow(startTime, finishTime);
    }

    @PutMapping("/{showId}/hall/{hallId}")
    public void assignShowToHall(
            @PathVariable int showId,
            @PathVariable int hallId) {

        showService.assignShowToHall(showId, hallId);
    }

    @PutMapping("/{showId}/movie/{movieId}")
    public void assignMovieToMovie(
            @PathVariable int showId,
            @PathVariable int movieId) {

        showService.assignMovieToShow(showId, movieId);
    }

    @DeleteMapping("/{showId}")
    public void deleteShow(@PathVariable int showId) {
        showService.deleteShow(showId);
    }

    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovie(@PathVariable int movieId) {
        return showService.getShowsByMovie(movieId);
    }

    @GetMapping("/hall/{hallId}")
    public List<Show> getShowsByHall(@PathVariable int hallId) {
        return showService.getShowsByHall(hallId);
    }

    @GetMapping("/{showId}/seats")
    public List<Seat> getRemainList(@PathVariable int showId) {
        return showService.getRemainingSeats(showId);
    }
}