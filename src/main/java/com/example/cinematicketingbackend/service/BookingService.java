package com.example.cinematicketingbackend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.cinematicketingbackend.model.Booking;
import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.model.Show;
import com.example.cinematicketingbackend.repository.BookingRepository;

public class BookingService {
    private BookingRepository bookingRepository;
    private SeatService seatService;
    private CustomerService customerService;

    public BookingService() {
        this.bookingRepository = BookingRepository.getInstance();
    }

    public void setSeatService(SeatService seatService) {
        this.seatService = seatService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Booking createBooking(int customerId, int movieId, Show show, 
                               int numberOfSeats, double totalPrice, List<Seat> seats) {
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("Number of seats must be greater than 0");
        }

        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }

        if (seats == null || seats.size() != numberOfSeats) {
            throw new IllegalArgumentException("Seats list must match number of seats");
        }

        // Generate new booking ID
        List<Booking> allBookings = bookingRepository.findAll();
        int newBookingId = 1;
        if (!allBookings.isEmpty()) {
            newBookingId = allBookings.stream()
                    .mapToInt(Booking::getBookingId)
                    .max()
                    .orElse(0) + 1;
        }

        Booking booking = new Booking(newBookingId, customerId, movieId, show, numberOfSeats, totalPrice);
        booking.setBookingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        booking.setSeats(seats);
        booking.setStatus("CONFIRMED");
        
        // Save booking
        bookingRepository.save(booking);
        
        // Add booking to customer
        if (customerService != null) {
            customerService.addBookingToCustomer(customerId, booking);
        }
        
        // Update seat status
        if (seatService != null) {
            for (Seat seat : seats) {
                seat.setStatus("booked");
                seatService.updateSeatStatus(seat.getHallid(), seat.getSeatNumber(), "booked");
            }
        }
        
        return booking;
    }

    public Booking getBooking(int bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        return bookingOpt.orElse(null);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByCustomer(int customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public List<Booking> getBookingsByMovie(int movieId) {
        return bookingRepository.findByMovieId(movieId);
    }

    public List<Booking> getBookingsByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String normalizedStatus = status.toUpperCase().trim();
        return bookingRepository.findByStatus(normalizedStatus);
    }

    public boolean cancelBooking(int bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (!bookingOpt.isPresent()) {
            return false;
        }

        Booking booking = bookingOpt.get();
        
        if ("CANCELLED".equals(booking.getStatus())) {
            return false; // Already cancelled
        }

        booking.setStatus("CANCELLED");
        
        // Update booking in repository
        bookingRepository.save(booking);
        
        // Release seats
        if (seatService != null && booking.getSeats() != null) {
            for (Seat seat : booking.getSeats()) {
                seatService.updateSeatStatus(seat.getHallid(), seat.getSeatNumber(), "available");
            }
        }
        
        return true;
    }

    public Booking updateBookingStatus(int bookingId, String newStatus) {
        if (newStatus == null || newStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        String normalizedStatus = newStatus.toUpperCase().trim();
        if (!isValidBookingStatus(normalizedStatus)) {
            throw new IllegalArgumentException(
                "Invalid status: " + newStatus + ". Valid statuses: PENDING, CONFIRMED, CANCELLED");
        }

        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (!bookingOpt.isPresent()) {
            throw new IllegalArgumentException("Booking with ID " + bookingId + " not found");
        }

        Booking booking = bookingOpt.get();
        booking.setStatus(normalizedStatus);
        
        // Save updated booking
        bookingRepository.save(booking);
        
        return booking;
    }

    private boolean isValidBookingStatus(String status) {
        return status.equals("PENDING") ||
               status.equals("CONFIRMED") ||
               status.equals("CANCELLED");
    }

    public List<Booking> getActiveBookings() {
        return bookingRepository.findByStatus("CONFIRMED");
    }

    public boolean isBookingActive(int bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (!bookingOpt.isPresent()) {
            return false;
        }
        Booking booking = bookingOpt.get();
        return "CONFIRMED".equals(booking.getStatus());
    }

    public void bookSeats(int customerId, int movieId, Show show, List<Seat> requestedSeats) {
        if (requestedSeats == null || requestedSeats.isEmpty()) {
            throw new IllegalArgumentException("Requested seats cannot be empty");
        }

        // Calculate total price
        double totalPrice = requestedSeats.size() * show.getHall().getSeatPrice();

        // Generate new booking ID
        List<Booking> allBookings = bookingRepository.findAll();
        int newBookingId = 1;
        if (!allBookings.isEmpty()) {
            newBookingId = allBookings.stream()
                    .mapToInt(Booking::getBookingId)
                    .max()
                    .orElse(0) + 1;
        }

        // 3️⃣ Create booking
        Booking booking = new Booking();
        booking.setBookingId(newBookingId);
        booking.setCustomerId(customerId);
        booking.setMovieId(movieId);
        booking.setShow(show);
        booking.setNumberOfSeats(requestedSeats.size());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("CONFIRMED");
        booking.setBookingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        booking.setSeats(requestedSeats);

        // Book seats
        for (Seat seat : requestedSeats) {
            seat.setStatus("booked");
            if (seatService != null) {
                seatService.updateSeatStatus(seat.getHallid(), seat.getSeatNumber(), "booked");
            }
        }

        // Save booking
        bookingRepository.save(booking);
        
        // Add booking to customer
        if (customerService != null) {
            customerService.addBookingToCustomer(customerId, booking);
        }
    }
}