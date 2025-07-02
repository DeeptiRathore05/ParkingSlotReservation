package com.parking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.parking.model.ParkingSlot;
import com.parking.model.Reservation;
import com.parking.model.User;
import com.parking.repository.ParkingSlotRepository;
import com.parking.repository.UserRepository;
import com.parking.service.ReservationService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    // Show all reservations
    @GetMapping("/reservations")
    public String viewReservations(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email);

        List<Reservation> list = reservationService.getReservationsByUser(user);
        model.addAttribute("reservations", list);
        return "reservations";
    }


    // Show booking form
    @GetMapping("/book")
    public String showBookingForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("slots", parkingSlotRepository.findAll());
        return "book";
    }

    // Handle booking
    @PostMapping("/book")
    public String bookSlot(@ModelAttribute Reservation reservation,
                           @RequestParam("parkingSlot") Long parkingSlotId,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email);

        reservation.setUser(user);
        ParkingSlot slot = parkingSlotRepository.findById(parkingSlotId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid slot ID: " + parkingSlotId));
        reservation.setParkingSlot(slot);
        reservation.setPaymentStatus(false);
        reservation.setDate(LocalDate.now());

        Reservation savedReservation = reservationService.saveReservation(reservation);

        // ✅ Redirect directly to payment form after saving
        return "redirect:/pay/form/" + savedReservation.getId();
    }

    @GetMapping("/booking-success")
    public String bookingSuccessPage(Model model) {
        return "success"; // This will render success.html with flash message
    }

    // Cancel reservation
    @GetMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return "redirect:/reservations";
    }
}
