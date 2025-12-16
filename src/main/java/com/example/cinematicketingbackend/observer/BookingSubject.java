package com.example.cinematicketingbackend.observer;

import com.example.cinematicketingbackend.model.Booking;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingSubject {

    private final List<BookingObserver> observers = new ArrayList<>();

    public void attach(BookingObserver observer) {
        observers.add(observer);
    }

    public void detach(BookingObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.update(booking);
        }
    }
}