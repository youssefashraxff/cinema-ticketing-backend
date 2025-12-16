package com.example.cinematicketingbackend.service;
import com.example.cinematicketingbackend.model.User;
import com.example.cinematicketingbackend.model.Review;
import java.time.LocalDateTime;
import java.util.*;

public class ReviewService {

    private Map<String, List<Review>> reviewsByMovie = new HashMap<>();
    public void addReview(String movieName, User loggedInUser, int rating, String comment) {
        if (loggedInUser == null) {
            System.out.println("You must be logged in to add a review!");
            return;
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        Review review = new Review();
        String id = String.valueOf((int)(Math.random() * 900) + 100);
        review.setId(id);
        review.setUserId(loggedInUser.getId());               // automatically set user ID
        review.setMovieName(movieName);
        review.setRating(rating);
        review.setComment(comment);
        review.setTimestamp(LocalDateTime.now());

        if (!reviewsByMovie.containsKey(movieName)) {
            reviewsByMovie.put(movieName, new ArrayList<>());
        }
        List<Review> reviews = reviewsByMovie.get(movieName);
        reviews.add(review);

        System.out.println("Review added successfully by " + loggedInUser.getUsername());
    }

    public List<Review> getMovieReviews(String movieName) {
        return reviewsByMovie.getOrDefault(movieName, new ArrayList<>());
    }

    public double calculateAverageRating(String movieName) {
        List<Review> reviews = getMovieReviews(movieName);
        if (reviews.isEmpty()) return 0.0;

        int sum = 0;
        for (Review r : reviews) sum += r.getRating();
        return (double) sum / reviews.size();
    }
}