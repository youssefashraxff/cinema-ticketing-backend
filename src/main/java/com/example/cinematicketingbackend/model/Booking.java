package com.example.cinematicketingbackend.model;
import java.util.List;
/**
 * Represents a booking in the cinema ticketing system.
 */
public class Booking {
    private final int bookingId;
    private final int customerId;
    private final int movieId;
    private final int showId;
    private final int numberOfSeats;
    private final double totalPrice;
    private final String bookingTime; // Format: "yyyy-MM-dd HH:mm:ss"
    private String status; // "CONFIRMED", "CANCELLED", "PENDING"
    private final List<Seat> seats;

    public Booking() {
    this.bookingId = 0;
    this.customerId = 0;
    this.movieId = 0;
    this.showId = 0;
    this.numberOfSeats = 0;
    this.totalPrice = 0;
    this.bookingTime = null;
    this.seats = null;
}

    public List<Seat> getSeats() {
        return seats;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.customerId = builder.customerId;
        this.movieId = builder.movieId;
        this.showId = builder.showId;
        this.numberOfSeats = builder.numberOfSeats;
        this.totalPrice = builder.totalPrice;
        this.bookingTime = builder.bookingTime;
        this.seats = builder.seats;
        this.status = builder.status;
    }

    public static class Builder {

        private int bookingId;
        private int customerId;
        private int movieId;
        private int showId;
        private int numberOfSeats;
        private double totalPrice;
        private String bookingTime;
        private String status = "PENDING";
        private List<Seat> seats;

        public Builder bookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder customerId(int customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder movieId(int movieId) {
            this.movieId = movieId;
            return this;
        }

        public Builder showId(int showId) {
            this.showId = showId;
            return this;
        }

        public Builder numberOfSeats(int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            return this;
        }

        public Builder totalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder bookingTime(String bookingTime) {
            this.bookingTime = bookingTime;
            return this;
        }

        public Builder seats(List<Seat> seats) {
            this.seats = seats;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }

    // Getters
    public int getBookingId() {
        return bookingId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public int getMovieId() {
        return movieId;
    }
    public int getShowId() {
        return showId;
    }
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public String getBookingTime() {
        return bookingTime;
    }
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", customerId=" + customerId +
                ", movieId=" + movieId +
                ", showId=" + showId +
                ", numberOfSeats=" + numberOfSeats +
                ", totalPrice=" + totalPrice +
                ", bookingTime='" + bookingTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return bookingId == booking.bookingId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(bookingId);
    }

    
}
