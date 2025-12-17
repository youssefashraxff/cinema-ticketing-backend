

package com.example.cinematicketingbackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cinematicketingbackend.model.Booking;
import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create a booking (book seats)
    @PostMapping
    public Booking bookSeats(
            @RequestParam int bookingId,
            @RequestParam int customerId,
            @RequestParam int movieId,
            @RequestBody Show show,
            @RequestBody List<Seat> seats) {

        return bookingService.bookSeats(
                bookingId,
                customerId,
                movieId,
                show,
                seats
        );
    }

    // Get booking by ID
    @GetMapping("/{bookingId}")
    public Booking getBooking(@PathVariable int bookingId) {
        return bookingService.getBooking(bookingId);
    }

    // Get bookings by customer
    @GetMapping("/customer/{customerId}")
    public List<Booking> getBookingsByCustomer(@PathVariable int customerId) {
        return bookingService.getBookingsByCustomer(customerId);
    }

    // Cancel booking
    @PutMapping("/{bookingId}/cancel")
    public void cancelBooking(@PathVariable int bookingId) {
        bookingService.cancelBooking(bookingId);
    }
}