package com.example.cinematicketingbackend.observer;

import com.example.cinematicketingbackend.model.Booking;

public interface BookingObserver {
    void update(Booking booking);
}