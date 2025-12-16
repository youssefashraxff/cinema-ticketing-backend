package com.example.cinematicketingbackend.service;

import com.example.cinematicketingbackend.decorator.*;
import com.example.cinematicketingbackend.model.*;
import com.example.cinematicketingbackend.observer.*;
import com.example.cinematicketingbackend.util.FileHandlerManager;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    private final FileHandlerManager fileHandler = FileHandlerManager.getInstance();
    private final String BOOKING_FILE = "src/main/resources/data/bookings.json";

    // Observer Pattern Subject
    private final BookingSubject bookingSubject;

    // Inject Subject and Notifier
    public BookingService(BookingSubject bookingSubject, EmailNotifier emailNotifier) {
        this.bookingSubject = bookingSubject;
        this.bookingSubject.attach(emailNotifier);
    }

    public Booking bookSeats(int customerId, int movieId, Show show, List<Seat> requestedSeats, boolean addPopcorn, boolean add3D) {
        
        double basePrice = show.getHall().getSeatPrice();
        double totalPrice = 0.0;

        // DECORATOR PATTERN
        for (Seat seat : requestedSeats) {
            Ticket ticket = new BasicTicket(seat.getSeatId(), basePrice);
            if (addPopcorn) ticket = new PopcornDecorator(ticket);
            if (add3D) ticket = new ThreeDGlassesDecorator(ticket); 
            
            totalPrice += ticket.getCost();
        }

        // Create Booking
        Booking booking = new Booking();
        booking.setBookingId(Math.abs(UUID.randomUUID().hashCode())); 
        booking.setCustomerId(customerId);
        booking.setMovieId(movieId);
        booking.setShow(show);
        booking.setNumberOfSeats(requestedSeats.size());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("CONFIRMED");
        booking.setBookingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        booking.setSeats(requestedSeats);

        // Save to JSON
        List<Booking> bookings = fileHandler.readList(Paths.get(BOOKING_FILE), Booking.class);
        bookings.add(booking);
        fileHandler.write(Paths.get(BOOKING_FILE), bookings);

        // OBSERVER PATTERN
        bookingSubject.notifyObservers(booking);

        return booking;
    }
}