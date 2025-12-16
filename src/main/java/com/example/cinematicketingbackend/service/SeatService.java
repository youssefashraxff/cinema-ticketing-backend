package com.example.cinematicketingbackend.service;

import java.util.List;
import java.util.Optional;

import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.repository.SeatRepository;

public class SeatService {
    private SeatRepository seatRepository;

    public SeatService() {
        this.seatRepository = SeatRepository.getInstance();
    }

    public void bookSeat(Seat seat) {
        if (seat == null) {
            throw new IllegalArgumentException("Seat cannot be null");
        }
        seat.setStatus("booked");
        seatRepository.save(seat);
    }
    
    public void lockSeat(Seat seat) {
        if (seat == null) {
            throw new IllegalArgumentException("Seat cannot be null");
        }
        seat.setStatus("locked");
        seatRepository.save(seat);
    }
    
    public void releaseSeat(Seat seat) {
        if (seat == null) {
            throw new IllegalArgumentException("Seat cannot be null");
        }
        seat.setStatus("available");
        seatRepository.save(seat);
    }
    
    public Seat getSeat(int hallId, int seatNumber) {
        Optional<Seat> seatOpt = seatRepository.findBySeatNumberAndHallId(seatNumber, hallId);
        return seatOpt.orElse(null);
    }
    
    public List<Seat> getAvailableSeats(int hallId) {
        return seatRepository.findAvailableSeatsByHallId(hallId);
    }
    
    public List<Seat> getSeatsByHall(int hallId) {
        return seatRepository.findByHallId(hallId);
    }
    
    public void initializeHallSeats(int hallId, int capacity) {
        seatRepository.initializeSeatsForHall(hallId, capacity);
    }
    
    public boolean updateSeatStatus(int hallId, int seatNumber, String status) {
        Optional<Seat> seatOpt = seatRepository.findBySeatNumberAndHallId(seatNumber, hallId);
        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            seat.setStatus(status);
            seatRepository.save(seat);
            return true;
        }
        return false;
    }
    
    public boolean isSeatAvailable(int hallId, int seatNumber) {
        Optional<Seat> seatOpt = seatRepository.findBySeatNumberAndHallId(seatNumber, hallId);
        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            return "available".equals(seat.getStatus());
        }
        return false;
    }
    
    public long countAvailableSeats(int hallId) {
        List<Seat> seats = seatRepository.findByHallId(hallId);
        return seats.stream()
                .filter(seat -> "available".equals(seat.getStatus()))
                .count();
    }
}