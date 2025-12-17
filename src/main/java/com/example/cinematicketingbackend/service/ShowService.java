package com.example.cinematicketingbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.exception.InvalidHallStatusException;
import com.example.cinematicketingbackend.exception.InvalidShowTimeException;
import com.example.cinematicketingbackend.exception.MaxShowsPerHallException;
import com.example.cinematicketingbackend.exception.ShowOverlapException;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.repository.FacadeRepository;
import com.example.cinematicketingbackend.util.TimeUtils;

@Service
public class ShowService {

    private final FacadeRepository facade;

    public ShowService(FacadeRepository facade) {
        this.facade = facade;
    }

    public Show createShow(String startTime, String finishTime) {

        if (!TimeUtils.validateTimeFormat(startTime) ||
            !TimeUtils.validateTimeFormat(finishTime)) {
            throw new InvalidShowTimeException(
                    "Invalid time format. Expected format: yyyy-MM-dd HH:mm");
        }

        if (!TimeUtils.isTimeAfter(finishTime, startTime)) {
            throw new InvalidShowTimeException(
                    "Finish time must be after start time");
        }

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
            if (TimeUtils.isOverlapping(
                    show.getStartTime(),
                    show.getFinishTime(),
                    existingShow.getStartTime(),
                    existingShow.getFinishTime())) {

                throw new ShowOverlapException(
                        "Show overlaps with an existing show in this hall");
            }
        }

        show.setHallId(hallId);
        facade.shows().save(show);
    }

    public void deleteShow(String startTime, String finishTime, int hallId) {
        facade.shows().delete(startTime, finishTime, hallId);
    }

    public List<Show> getAllShows() {
        return facade.shows().findAll();
    }

    public List<Show> getShowsByHall(int hallId) {
        return facade.shows().findAll().stream()
                .filter(s -> s.getHallId() == hallId)
                .collect(Collectors.toList());
    }
}
