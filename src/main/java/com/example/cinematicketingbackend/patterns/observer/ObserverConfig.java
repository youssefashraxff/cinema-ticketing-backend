package com.example.cinematicketingbackend.patterns.observer;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ObserverConfig {

    public ObserverConfig(BookingSubject bookingSubject,
                          EmailNotifier emailNotifier) {
        bookingSubject.attach(emailNotifier);
    }
}