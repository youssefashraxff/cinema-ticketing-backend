package com.example.cinematicketingbackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.model.Review;
import com.example.cinematicketingbackend.repository.FacadeRepository;

@Service
public class ReviewService {

    private final FacadeRepository facade;

    public ReviewService(FacadeRepository facade) {
        this.facade = facade;
    }

    public Review addReview(
            String movieName,
            String userId,
            int rating,
            String comment
    ) {

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User must be logged in to add a review");
        }

        if (movieName == null || movieName.isBlank()) {
            throw new IllegalArgumentException("Movie name is required");
        }

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.setId(UUID.randomUUID().toString());
        review.setUserId(userId);
        review.setMovieName(movieName);
        review.setRating(rating);
        review.setComment(comment);
        review.setTimestamp(LocalDateTime.now());

        facade.reviews().save(review);
        return review;
    }

    public List<Review> getMovieReviews(String movieName) {

        if (movieName == null || movieName.isBlank()) {
            return List.of();
        }

        return facade.reviews()
                .findAll()
                .stream()
                .filter(r -> movieName.equalsIgnoreCase(r.getMovieName()))
                .collect(Collectors.toList());
    }

    public double calculateAverageRating(String movieName) {

        List<Review> reviews = getMovieReviews(movieName);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        int sum = reviews.stream()
                .mapToInt(Review::getRating)
                .sum();

        return (double) sum / reviews.size();
    }
}