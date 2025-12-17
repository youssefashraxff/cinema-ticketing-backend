package com.example.cinematicketingbackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.cinematicketingbackend.model.Hall;
import com.fasterxml.jackson.core.type.TypeReference;

@Repository
public class HallRepository {
private static final String FILE_PATH = "data/halls.json";


    private final FileManager fileManager;

    public HallRepository() {
        this.fileManager = FileManager.getInstance();
    }

    private List<Hall> loadHalls() {
        try {
            List<Hall> data = fileManager.read(
                    FILE_PATH,
                    new TypeReference<List<Hall>>() {}
            );
            System.out.println("Data:"+data);
            return data != null ? data : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Data empty");
            return new ArrayList<>();
        }
    }

    private void saveHalls(List<Hall> halls) {
        fileManager.write(FILE_PATH, halls);
    }

    public List<Hall> findAll() {
        return new ArrayList<>(loadHalls());
    }

    public Optional<Hall> findById(int hallId) {
        System.out.println("\n\nShow Hall Id"+hallId+"\n\n");
        System.out.println(loadHalls());
        return loadHalls().stream()
                .filter(h -> h.getHallId() == hallId)
                .findFirst();
    }

    public Hall save(Hall hall) {
        List<Hall> halls = loadHalls();

        halls.removeIf(h -> h.getHallId() == hall.getHallId());
        halls.add(hall);

        saveHalls(halls);
        return hall;
    }

    public void deleteById(int hallId) {
        List<Hall> halls = loadHalls();
        halls.removeIf(h -> h.getHallId() == hallId);
        saveHalls(halls);
    }
}
