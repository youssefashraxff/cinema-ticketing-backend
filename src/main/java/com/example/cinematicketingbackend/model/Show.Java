package com.example.cinematicketingbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Show {
    private String id;
    private String movieId;
    private String hallName;
    private LocalDateTime startTime;
    private List<String> bookedSeats = new ArrayList<>(); // ["A1", "A2"]
}