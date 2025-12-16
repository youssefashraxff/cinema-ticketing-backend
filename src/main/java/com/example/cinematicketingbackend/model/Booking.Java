package com.example.cinematicketingbackend.model;
import java.util.List;
/**
 * Represents a booking in the cinema ticketing system.
 */
public class Booking {
    private int bookingId;
    private int customerId;
    private int movieId;
    private Show show;
    private int numberOfSeats;
    private double totalPrice;
    private String bookingTime; // Format: "yyyy-MM-dd HH:mm:ss"
    private String status; // "CONFIRMED", "CANCELLED", "PENDING"
    private List<Seat> seats;

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Booking() {
        this.status = "PENDING";
    }

    public Booking(int bookingId, int customerId, int movieId, Show show, int numberOfSeats, double totalPrice) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.movieId = movieId;
        this.show = show;
        this.numberOfSeats = numberOfSeats;
        this.totalPrice = totalPrice;
        this.status = "CONFIRMED";
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", customerId=" + customerId +
                ", movieId=" + movieId +
                ", show=" + show +
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
