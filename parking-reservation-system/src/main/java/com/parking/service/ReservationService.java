package com.parking.service;

import com.parking.model.Reservation;
import com.parking.model.User;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Reservation saveReservation(Reservation reservation);
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    void cancelReservation(Long id);
    List<Reservation> getReservationsByUser(User user);
}

