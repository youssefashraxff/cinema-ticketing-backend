package com.example.cinematicketingbackend.observer;

import com.example.cinematicketingbackend.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class EmailNotifier implements BookingObserver {

    @Override
    public void update(Booking booking) {
        System.out.println("   [EMAIL NOTIFICATION] -------------------------");
        System.out.println("   To User ID: " + booking.getCustomerId());
        System.out.println("   Subject: Booking Confirmed! ID: " + booking.getBookingId());
        System.out.println("   Total Paid: $" + booking.getTotalPrice());
        System.out.println("   Seat(s): " + booking.getNumberOfSeats());
        System.out.println("--------------------------------------------------");
    }
}