package com.example.cinematicketingbackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cinematicketingbackend.model.Booking;
import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    // Create a booking (book seats)
    @PostMapping
    public Booking bookSeats(@RequestBody Map<String, Object> body) {

        int customerId = (int) body.get("customerId");
        int movieId = (int) body.get("movieId");
        int showId = (int) body.get("showId");
        String paymentType = (String) body.get("paymentType");
        

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> seatsMap =
                (List<Map<String, Object>>) body.get("seats");

        List<Seat> seats = seatsMap.stream()
                .map(s -> new Seat(
                        ((String) s.get("row")).charAt(0),
                        (int) s.get("number")
                ))
                .toList();

        return bookingService.bookSeats(
                customerId,
                movieId,
                showId,
                seats,
                paymentType
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