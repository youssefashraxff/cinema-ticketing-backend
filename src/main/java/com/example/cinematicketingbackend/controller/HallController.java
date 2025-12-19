package com.example.cinematicketingbackend.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.service.HallService;


@RestController
@RequestMapping("/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping
    public Hall createHall(
            @RequestParam int capacity,
            @RequestParam String hallType,
            @RequestParam int seatPrice) {

        return hallService.createHall(capacity, hallType, seatPrice);
    }

    @DeleteMapping("/{hallId}")
    public void deleteHall(@PathVariable int hallId) {
        hallService.deleteHall(hallId);
    }

    @GetMapping
    public List<Hall> getAllHalls() {
        return hallService.getAllHalls();
    }


    @GetMapping("/{hallId}")
    public Hall getHalById(@PathVariable int hallId) {
       return hallService.getHall(hallId);
    }
    
}
