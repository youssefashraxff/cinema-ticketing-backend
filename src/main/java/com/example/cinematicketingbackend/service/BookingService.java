package com.example.cinematicketingbackend.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.exception.InvalidBookingException;
import com.example.cinematicketingbackend.model.Booking;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.repository.FacadeRepository;

@Service
public class BookingService {

    private final FacadeRepository facade;
    

    public BookingService(FacadeRepository facade) {
        this.facade = facade;
        
    }

    public Booking bookSeats(int bookingId,int customerId,int movieId,Show show,List<Seat> requestedSeats) {

        if (requestedSeats == null || requestedSeats.isEmpty()) {
            throw new InvalidBookingException("No seats selected");
        }

        Hall hall = facade.halls()
                .findById(show.getHallId())
                .orElseThrow(() ->
                        new InvalidBookingException(
                                "Hall not found for show"));

        double totalPrice =
                requestedSeats.size() * hall.getSeatPrice();

        Booking booking = new Booking.Builder()
                .bookingId(bookingId)
                .customerId(customerId)
                .movieId(movieId)
                .show(show)
                .numberOfSeats(requestedSeats.size())
                .totalPrice(totalPrice)
                .bookingTime(
                        LocalDateTime.now().format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd HH:mm:ss")))
                .seats(requestedSeats)
                .status("CONFIRMED")
                .build();

        // Persist booking
        facade.bookings().save(booking);

        return booking;
    }

    public List<Booking> getBookingsByCustomer(int customerId) {
        return facade.bookings().findAll().stream()
                .filter(b -> b.getCustomerId() == customerId)
                .toList();
    }

    public Booking getBooking(int bookingId) {
        return facade.bookings()
                .findById(bookingId)
                .orElseThrow(() ->
                        new InvalidBookingException(
                                "Booking not found: " + bookingId));
    }

    public void cancelBooking(int bookingId) {
        Booking booking = getBooking(bookingId);
        booking.setStatus("CANCELLED");
        facade.bookings().save(booking);
    }
}