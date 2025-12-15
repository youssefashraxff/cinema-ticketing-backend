package com.example.cinematicketingbackend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.cinematicketingbackend.model.Booking;
import com.example.cinematicketingbackend.model.Seat;
import com.example.cinematicketingbackend.model.Show;

public class BookingService {

    public void bookSeats(int bookingId,int customerId,int movieId,Show show,List<Seat> requestedSeats)
        {
        double totalPrice = requestedSeats.size() * show.getHall().getSeatPrice();

        // 3️⃣ Create booking
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setCustomerId(customerId);
        booking.setMovieId(movieId);
        booking.setShow(show);
        booking.setNumberOfSeats(requestedSeats.size());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("CONFIRMED");
        booking.setBookingTime( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        booking.setSeats(requestedSeats);

       SeatService seatService=new SeatService();
        for (Seat seat : requestedSeats) {
            seatService.bookSeat(seat);
        }

    }
}