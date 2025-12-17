

package com.example.cinematicketingbackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cinematicketingbackend.model.Review;
import com.example.cinematicketingbackend.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Add a review
    @PostMapping
    public Review addReview(
            @RequestParam String movieName,
            @RequestParam String userId,
            @RequestParam int rating,
            @RequestParam(required = false) String comment
    ) {
        return reviewService.addReview(
                movieName,
                userId,
                rating,
                comment
        );
    }

    // Get all reviews for a movie
    @GetMapping("/movie")
    public List<Review> getMovieReviews(
            @RequestParam String movieName
    ) {
        return reviewService.getMovieReviews(movieName);
    }

    // Get average rating for a movie
    @GetMapping("/movie/average-rating")
    public double getAverageRating(
            @RequestParam String movieName
    ) {
        return reviewService.calculateAverageRating(movieName);
    }
}