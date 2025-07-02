package com.parking.repository;

import com.parking.model.Reservation;
import com.parking.model.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	List<Reservation> findByUser(User user);
    // No custom query methods for now
}
