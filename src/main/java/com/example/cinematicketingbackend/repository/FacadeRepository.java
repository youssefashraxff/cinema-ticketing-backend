package com.example.cinematicketingbackend.repository;

import org.springframework.stereotype.Component;

@Component
public class FacadeRepository {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final HallRepository hallRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public FacadeRepository(
            MovieRepository movieRepository,
            ShowRepository showRepository,
            HallRepository hallRepository,
            BookingRepository bookingRepository,
            ReviewRepository reviewRepository,
            UserRepository userRepository
    ) {
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.hallRepository = hallRepository;
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public MovieRepository movies() {
        return movieRepository;
    }

    public ShowRepository shows() {
        return showRepository;
    }

    public HallRepository halls() {
        return hallRepository;
    }

    public BookingRepository bookings() {
        return bookingRepository;
    }

    public ReviewRepository reviews() {
        return reviewRepository;
    }

    public UserRepository users() {
        return userRepository;
    }
}
