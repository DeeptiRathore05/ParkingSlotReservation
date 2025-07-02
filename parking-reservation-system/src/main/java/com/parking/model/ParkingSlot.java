package com.parking.model;

import jakarta.persistence.*;

@Entity
public class ParkingSlot {
	//This is parking
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slotNumber;

    private boolean isAvailable;

    public ParkingSlot() {}

    public ParkingSlot(String slotNumber, boolean isAvailable) {
        this.slotNumber = slotNumber;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}
