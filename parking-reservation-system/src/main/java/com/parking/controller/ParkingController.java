/*
 * package com.parking.controller;
 * 
 * import com.parking.model.Reservation; import
 * com.parking.service.ReservationService; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.*;
 * 
 * import java.time.Duration; import java.time.Instant; import
 * java.time.LocalDateTime; import java.time.ZoneId; import java.util.List;
 * import java.util.Optional;
 * 
 * @Controller public class ParkingController {
 * 
 * @Autowired private ReservationService reservationService;
 * 
 * // Show home page (or booking form)
 * 
 * @GetMapping("/") public String home(Model model) {
 * model.addAttribute("reservation", new Reservation()); return "booking"; }
 * 
 * 
 * // View all reservations
 * 
 * @GetMapping("/reservations") public String viewAllReservations(Model model) {
 * List<Reservation> reservations = reservationService.getAllReservations();
 * model.addAttribute("reservations", reservations); return "reservations"; }
 * 
 * 
 * @GetMapping("/cancel/{id}") public String cancelReservation(@PathVariable
 * Long id, Model model) { Optional<Reservation> optionalReservation =
 * reservationService.getReservationById(id);
 * 
 * if (optionalReservation.isPresent()) { Reservation reservation =
 * optionalReservation.get();
 * 
 * LocalDateTime now = LocalDateTime.now();
 * 
 * // Convert java.util.Date to LocalDateTime Instant instant =
 * reservation.getStartTime().toInstant(); ZoneId zoneId =
 * ZoneId.systemDefault(); // you can use ZoneId.of("Asia/Kolkata") if needed
 * LocalDateTime reservationStartTime =
 * instant.atZone(zoneId).toLocalDateTime();
 * 
 * // Calculate duration Duration diff = Duration.between(now,
 * reservationStartTime);
 * 
 * // Refund logic: eligible if more than 30 minutes remaining boolean
 * eligibleForRefund = diff.toMinutes() >= 30;
 * 
 * // Cancel the reservation reservationService.cancelReservation(id);
 * 
 * model.addAttribute("message", "Reservation cancelled.");
 * 
 * if (eligibleForRefund) { model.addAttribute("refund",
 * "Refund will be processed to your account."); } else {
 * model.addAttribute("refund",
 * "Reservation cancelled, but not eligible for refund."); } } else {
 * model.addAttribute("message", "Reservation not found."); }
 * 
 * return "cancel"; // your Thymeleaf template name } }
 */