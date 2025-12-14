package com.example.cinematicketingbackend.repository;
import java.util.ArrayList;
import java.util.List;
import com.example.cinematicketingbackend.model.Review;

public class ReviewRepository {

    private List<Review> reviews = new ArrayList<>();

    public List<Review> findByMovieId(String movieId) {
        List<Review> result = new ArrayList<>();

        for (Review r : reviews) {
            if (r.getMovieId().equals(movieId)) {
                result.add(r);
            }
        }
        return result;
    }

    public void save(Review review) {
        reviews.add(review);
    }
}
