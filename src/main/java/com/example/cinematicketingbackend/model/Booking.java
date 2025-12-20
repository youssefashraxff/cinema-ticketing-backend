package com.example.cinematicketingbackend.model;
import java.util.List;
/**
 * Represents a booking in the cinema ticketing system.
 */
public class Booking {
    private int bookingId;
    private int customerId;
    private int movieId;
    private int showId;
    private int numberOfSeats;
    private double totalPrice;
    private String bookingTime; // Format: "yyyy-MM-dd HH:mm:ss"
    private String status; // "CONFIRMED", "CANCELLED", "PENDING"
    private List<Seat> seats;

    private String paymentType;
  

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
        this.paymentType = builder.paymentType;
   
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
        private String paymentType;
      

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
        
        public Builder paymentType(String paymentType) {
            this.paymentType = paymentType;
            return this;
        }

     

        public Booking build() {
            return new Booking(this);
        }
    }

    // Getters
    public List<Seat> getSeats() {
        return seats;
    }
    
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

   

    // Setters
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public void setShowId(int showId) {
        this.showId = showId;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
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
                ", paymentType='" + paymentType + '\'' +
               
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
