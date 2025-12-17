package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.Show;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ShowRepository {

    private static final String FILE_PATH = "data/shows.json";


    private final FileManager fileManager;

    public ShowRepository() {
        this.fileManager = FileManager.getInstance();
    }

    private List<Show> loadShows() {
        try {
            List<Show> data = fileManager.read(
                    FILE_PATH,
                    new TypeReference<List<Show>>() {}
            );
            return data != null ? data : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveShows(List<Show> shows) {
        fileManager.write(FILE_PATH, shows);
    }

    public Optional <Show> findById(int showId) {
        return loadShows().stream()
                .filter(s -> s.getShowId() == showId)
                .findFirst();
    }


    public List<Show> findAll() {
        return new ArrayList<>(loadShows());
    }

    public Optional <Show> findByTimeAndHall(String startTime, String finishTime, int hallId) {
        return loadShows().stream()
                .filter(s ->
                        s.getStartTime().equals(startTime) &&
                        s.getFinishTime().equals(finishTime) &&
                        s.getHallId() == hallId
                )
                .findFirst();
    }

    public Show save(Show show) {
        List<Show> shows = loadShows();

        shows.removeIf(s ->
                s.getStartTime().equals(show.getStartTime()) &&
                s.getFinishTime().equals(show.getFinishTime()) &&
                s.getHallId() == show.getHallId()
        );

        shows.add(show);
        saveShows(shows);
        return show;
    }

    public void delete(int showId) {
        List<Show> shows = loadShows();
        shows.removeIf(s -> s.getShowId() == showId);
        saveShows(shows);
    }
}