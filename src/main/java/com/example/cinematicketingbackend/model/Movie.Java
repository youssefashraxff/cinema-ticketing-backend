package com.example.cinematicketingbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private String id;
    private String title;
    private String genre;
    private String language;
    private int durationMinutes;
    private double standardPrice;
    private double rating; // Average rating (e.g., 4.5)
}