
package com.example.cinematicketingbackend.repository;

import com.example.cinematicketingbackend.model.Booking;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepository {
private static final String FILE_PATH = "data/bookings.json";


    private final FileManager fileManager;

    public BookingRepository() {
        this.fileManager = FileManager.getInstance();
    }

    private List<Booking> loadBookings() {
        try {
            List<Booking> data = fileManager.read(
                    FILE_PATH,
                    new TypeReference<List<Booking>>() {}
            );
            return data != null ? data : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveBookings(List<Booking> bookings) {
        fileManager.write(FILE_PATH, bookings);
    }

    public List<Booking> findAll() {
        return new ArrayList<>(loadBookings());
    }

    public Optional<Booking> findById(int bookingId) {
        return loadBookings().stream()
                .filter(b -> b.getBookingId() == bookingId)
                .findFirst();
    }

    public Booking save(Booking booking) {
        List<Booking> bookings = loadBookings();

        bookings.removeIf(b -> b.getBookingId() == booking.getBookingId());
        bookings.add(booking);

        saveBookings(bookings);
        return booking;
    }

    public List<Booking> findByShowId(int showId) {
        return loadBookings().stream()
                .filter(b -> b.getShow().getShowId() == showId)
                .toList();
    }
    public void deleteById(int bookingId) {
        List<Booking> bookings = loadBookings();
        bookings.removeIf(b -> b.getBookingId() == bookingId);
        saveBookings(bookings);
    }
}