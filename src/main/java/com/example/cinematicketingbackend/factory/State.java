package com.example.cinematicketingbackend.factory;
import com.example.cinematicketingbackend.model.Seat;
public abstract class State {
  
    public Seat seat;


    public State(Seat seat)
    {
        this.seat = seat;
    }
    public abstract void bookSeat();
    public abstract void lockSeat();
    public abstract void releaseSeat();
    

}



