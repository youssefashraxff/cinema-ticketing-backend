package com.example.cinematicketingbackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(
                review.getMovieName(),
                review.getUserId(),
                review.getRating(),
                review.getComment()
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