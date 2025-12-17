package com.example.cinematicketingbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.repository.FacadeRepository;

@Service
public class HallService {

    private final FacadeRepository facade;

    public HallService(FacadeRepository facade, ShowService showService) {
        this.facade = facade;
    }

    public Hall createHall(int capacity, String hallType, int seatPrice) {

        int newHallId = facade.halls().findAll().stream()
                .mapToInt(Hall::getHallId)
                .max()
                .orElse(0) + 1;

        Hall hall = new Hall(newHallId, capacity, hallType, seatPrice);
        facade.halls().save(hall);
        return hall;
    }
    public void deleteHall(int hallId) {

        Hall hall = facade.halls()
                .findById(hallId)
                .orElse(null);

        if (hall == null) {
            return;
        }

        facade.halls().deleteById(hallId);
    }
    public Hall getHall(int hallId) {
        return facade.halls()
                .findById(hallId)
                .orElse(null);
    }
    public List<Hall> getAllHalls() {
        return facade.halls().findAll();
    }

}
