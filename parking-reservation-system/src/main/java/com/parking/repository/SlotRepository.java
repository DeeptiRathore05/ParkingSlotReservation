package com.parking.repository;

import com.parking.model.ParkingSlot;
import org.springframework.data.repository.CrudRepository;

public interface SlotRepository extends CrudRepository<ParkingSlot, Long> {
}

