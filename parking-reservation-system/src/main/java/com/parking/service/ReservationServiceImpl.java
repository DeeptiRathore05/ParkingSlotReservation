package com.parking.service;

import com.parking.model.Reservation;
import com.parking.model.User;
import com.parking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        reservationRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public List<Reservation> getReservationsByUser(User user) {
        return reservationRepository.findByUser(user);
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + id));
        
    }

    @Override
    public void cancelReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
