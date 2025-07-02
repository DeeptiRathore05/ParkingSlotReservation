package com.parking.repository;

import com.parking.model.ParkingSlot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepository extends CrudRepository<ParkingSlot, Long> {
}
