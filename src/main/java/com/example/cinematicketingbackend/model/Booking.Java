package com.example.cinematicketingbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private String id;
    private String userId;
    private String showId;
    private List<String> seats;
    private double totalAmount;
    private String status; // "Confirmed", "Cancelled"
    private LocalDateTime timestamp;
}