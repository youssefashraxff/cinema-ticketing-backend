package com.example.cinematicketingbackend.factory;
import com.example.cinematicketingbackend.model.Seat;
public class AvailableSeats extends State {

    public AvailableSeats(Seat seat)
    {
        super(seat);
    }
    @Override
    public void bookSeat()
    {
        seat.setStatus("booked");
    }
    @Override
    public void lockSeat()
    {
        seat.setStatus("locked");
    }
    @Override
    public void releaseSeat()
    {
        seat.setStatus("available");
    }
}
