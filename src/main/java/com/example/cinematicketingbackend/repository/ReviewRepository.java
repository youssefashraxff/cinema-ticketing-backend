package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.Review;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepository {

    private static final String FILE_PATH = "data/reviews.json";

    private final FileManager fileManager;

    public ReviewRepository() {
        this.fileManager = FileManager.getInstance();
    }

    private List<Review> loadReviews() {
        try {
            List<Review> data = fileManager.read(
                    FILE_PATH,
                    new TypeReference<List<Review>>() {}
            );
            return data != null ? data : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveReviews(List<Review> reviews) {
        fileManager.write(FILE_PATH, reviews);
    }

    public List<Review> findAll() {
        return new ArrayList<>(loadReviews());
    }

    public Optional<Review> findById(String id) {
        return loadReviews().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public Review save(Review review) {
        List<Review> reviews = loadReviews();

        reviews.removeIf(r -> r.getId().equals(review.getId()));
        reviews.add(review);

        saveReviews(reviews);
        return review;
    }

    public void deleteById(String id) {
        List<Review> reviews = loadReviews();
        reviews.removeIf(r -> r.getId().equals(id));
        saveReviews(reviews);
    }
}
