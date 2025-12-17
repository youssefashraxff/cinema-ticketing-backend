package com.example.cinematicketingbackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}