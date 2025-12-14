package com.example.cinematicketingbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String id;
    private String movieId;
    private String userId;
    private int rating; 
    private String comment;
    private LocalDateTime timestamp;
}