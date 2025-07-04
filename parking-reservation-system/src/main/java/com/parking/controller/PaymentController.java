package com.parking.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.parking.model.Reservation;
import com.parking.service.ReservationService;

@Controller
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/form/{id}")
    public String showPaymentForm(@PathVariable Long id, Model model) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null && reservation.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = reservation.getDate().format(formatter);
            model.addAttribute("formattedDate", formattedDate);
        }
        model.addAttribute("reservation", reservation);
        return "payment-form"; // This points to payment-form.html
    }

    // Process the fake payment form submission
    @PostMapping("/submit")
    public String submitFakePayment(@RequestParam("reservationId") Long reservationId,
                                    RedirectAttributes redirectAttributes) {
        Reservation reservation = reservationService.getReservationById(reservationId);

        if (reservation != null) {
            reservation.setPaymentStatus(true); // Mark as paid
            reservationService.saveReservation(reservation);
            redirectAttributes.addFlashAttribute("message", "Payment successful!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid reservation ID.");
        }

        return "redirect:/pay/payment-success"; // ✅ Redirect to success page
    }
    @GetMapping("/payment-success")
    public String paymentSuccessPage(Model model) {
        return "payment-success"; // points to payment-success.html
    }


}
