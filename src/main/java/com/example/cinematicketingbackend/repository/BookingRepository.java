package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.Booking;
import com.example.cinematicketingbackend.util.FileHandlerManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingRepository {
    private static BookingRepository instance;
    private List<Booking> bookings;
    private final FileHandlerManager fileHandler;
    private final Path bookingsFilePath;

    private BookingRepository() {
        this.fileHandler = FileHandlerManager.getInstance();
        this.bookingsFilePath = Paths.get("src/main/resources/data/bookings.json");
        loadBookingsFromJson();
    }

    public static synchronized BookingRepository getInstance() {
        if (instance == null) {
            instance = new BookingRepository();
        }
        return instance;
    }

    private void loadBookingsFromJson() {
        try {
            bookings = fileHandler.readList(bookingsFilePath, new TypeReference<List<Booking>>() {});
        } catch (Exception e) {
            System.err.println("Error loading bookings from file: " + e.getMessage());
            bookings = new ArrayList<>();
        }
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    public Optional<Booking> findById(int bookingId) {
        return bookings.stream()
                .filter(booking -> booking.getBookingId() == bookingId)
                .findFirst();
    }

    public List<Booking> findByCustomerId(int customerId) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomerId() == customerId) {
                result.add(booking);
            }
        }
        return result;
    }

    public List<Booking> findByMovieId(int movieId) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getMovieId() == movieId) {
                result.add(booking);
            }
        }
        return result;
    }

    public List<Booking> findByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Booking> result = new ArrayList<>();
        String normalizedStatus = status.trim().toUpperCase();
        for (Booking booking : bookings) {
            if (normalizedStatus.equals(booking.getStatus())) {
                result.add(booking);
            }
        }
        return result;
    }

    public boolean existsById(int bookingId) {
        return bookings.stream().anyMatch(booking -> booking.getBookingId() == bookingId);
    }

    public List<Booking> findActiveBookingsByCustomerId(int customerId) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomerId() == customerId && 
                !"CANCELLED".equalsIgnoreCase(booking.getStatus())) {
                result.add(booking);
            }
        }
        return result;
    }

    public long count() {
        return bookings.size();
    }

    public long countByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return 0;
        }
        String normalizedStatus = status.trim().toUpperCase();
        return bookings.stream()
                .filter(booking -> normalizedStatus.equals(booking.getStatus()))
                .count();
    }

    // FIXED: Save a single booking
    public void save(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        // Remove existing booking with same ID
        bookings.removeIf(b -> b.getBookingId() == booking.getBookingId());
        
        // Add the new booking
        bookings.add(booking);
        
        saveAllToFile();
    }

    // FIXED: Save all bookings properly
    public void saveAll(List<Booking> bookingsToSave) {
        if (bookingsToSave == null || bookingsToSave.isEmpty()) {
            return;
        }

        for (Booking booking : bookingsToSave) {
            // Remove existing booking with same ID
            bookings.removeIf(b -> b.getBookingId() == booking.getBookingId());
            // Add the booking
            bookings.add(booking);
        }
        
        saveAllToFile();
    }

    private void saveAllToFile() {
        try {
            fileHandler.write(bookingsFilePath, bookings);
        } catch (Exception e) {
            System.err.println("Error saving bookings to file: " + e.getMessage());
            throw new RuntimeException("Failed to save bookings", e);
        }
    }

    public boolean deleteById(int bookingId) {
        boolean removed = bookings.removeIf(booking -> booking.getBookingId() == bookingId);
        if (removed) {
            saveAllToFile();
        }
        return removed;
    }
}