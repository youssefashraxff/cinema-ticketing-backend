package com.example.cinematicketingbackend.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.cinematicketingbackend.exception.InvalidBookingException;
import com.example.cinematicketingbackend.model.Booking;
import com.example.cinematicketingbackend.model.Hall;
import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.patterns.observer.BookingSubject;
import com.example.cinematicketingbackend.repository.FacadeRepository;

@Service
public class BookingService {

    private final FacadeRepository facade;
    private final BookingSubject bookingSubject;
    
   public BookingService(FacadeRepository facade, BookingSubject bookingSubject) {
    this.facade = facade;
    this.bookingSubject = bookingSubject;
}
    public List<Booking> getAllBookings() {
        return facade.bookings().findAll();
    }

    public Booking bookSeats(int customerId,int movieId,int showId,List<Seat> requestedSeats) {

        if (requestedSeats == null || requestedSeats.isEmpty()) {
            throw new InvalidBookingException("No seats selected");
        }

        Show show = facade.shows()
                .findById(showId)
                .orElseThrow(() ->
                        new InvalidBookingException("Show not found"));
        
        int remainingSeats = getRemainingSeats(showId);

        if (requestedSeats.size() > remainingSeats) {
            throw new InvalidBookingException(
                    "Not enough available seats. Remaining: " + remainingSeats);
        }

        Hall hall = facade.halls()
                .findById(show.getHallId())
                .orElseThrow(() ->
                        new InvalidBookingException(
                                "Hall not found for show"));

        double totalPrice =
                requestedSeats.size() * hall.getSeatPrice();

        int bookingId = facade.bookings().findAll().size() + 1;

        Booking booking = new Booking.Builder()
                .bookingId(bookingId)
                .customerId(customerId)
                .movieId(movieId)
                .showId(showId)
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
        bookingSubject.notifyObservers(booking);

        return booking;
    }

    public int getRemainingSeats(int showId) {
        int bookedSeats = facade.bookings()
                .findByShowId(showId)
                .stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .mapToInt(Booking::getNumberOfSeats)
                .sum();

        Show show = facade.shows().findById(showId).orElseThrow();
        Hall hall = facade.halls().findById(show.getHallId()).orElseThrow();
        return hall.getCapacityOfSeats() - bookedSeats;
    }

    public List<Booking> getBookingsByCustomer(int customerId) {
        return facade.bookings().findAll().stream()
                .filter(b -> b.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    public Booking getBooking(int bookingId) {
        System.out.println("\nBooking Id "+bookingId+"\n");
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