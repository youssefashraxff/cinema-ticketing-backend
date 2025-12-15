package com.example.cinematicketingbackend.service;
import com.example.cinematicketingbackend.model.Seat;

public class SeatService {
    public void bookSeat(Seat seat)
    {
        seat.setStatus("booked");

    }
    
}
