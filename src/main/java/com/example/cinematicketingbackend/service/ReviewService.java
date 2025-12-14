package com.example.cinematicketingbackend.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.example.cinematicketingbackend.model.Review;
import com.example.cinematicketingbackend.repository.ReviewRepository;

public class ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void addReview(String movieId, String userId, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        Review review = new Review();
        review.setId(UUID.randomUUID().toString());
        review.setMovieId(movieId);
        review.setUserId(userId);
        review.setRating(rating);
        review.setComment(comment);
        review.setTimestamp(LocalDateTime.now());
        reviewRepository.save(review);
    }


    public List<Review> getMovieReviews(String mId){
        return reviewRepository.findByMovieId(mId);
    }

    public double calculateAverageRating(String mId){
        List<Review> reviews = reviewRepository.findByMovieId(mId);
        if(reviews.isEmpty()){
            return 0.0;
        }
        int sum=0;
        for(Review r: reviews){
            sum +=r.getRating();
        }
        return (double) sum/reviews.size();
    }

}