package com.parking.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.parking.model.ParkingSlot;
import com.parking.model.Reservation;
import com.parking.model.User;
import com.parking.repository.ParkingSlotRepository;
import com.parking.repository.UserRepository;
import com.parking.service.ReservationService;

@Controller
public class AdminController {
	
	@Autowired
    private UserRepository userRepository;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private ParkingSlotRepository parkingSlotRepository;

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard() {
        return "admin-dashboard"; // this will be admin-dashboard.html
    }
    
    @GetMapping("/admin/users")
    public String viewAllUsers(Model model) {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        model.addAttribute("users", users);
        return "admin-users";
    }
    @GetMapping("/admin/reservations")
    public String viewAllReservations(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "admin-reservations";
    }
    @GetMapping("/admin/slots")
    public String manageSlots(Model model) {
        List<ParkingSlot> slots = new ArrayList<>();
        parkingSlotRepository.findAll().forEach(slots::add);
        model.addAttribute("slots", slots);
        return "admin-slots";
    }

    @PostMapping("/admin/slots/add")
    public String addSlot(@RequestParam String slotNumber, @RequestParam(required = false) Boolean available) {
        ParkingSlot slot = new ParkingSlot();
        slot.setSlotNumber(slotNumber);
        slot.setAvailable(available);
        parkingSlotRepository.save(slot);
        return "redirect:/admin/slots";
    }
    @GetMapping("/admin/slots/delete/{id}")
    public String deleteSlot(@PathVariable Long id) {
        parkingSlotRepository.deleteById(id);
        return "redirect:/admin/slots";
    }

}