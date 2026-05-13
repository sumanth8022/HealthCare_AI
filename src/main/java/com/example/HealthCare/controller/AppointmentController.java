package com.example.HealthCare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.HealthCare.model.Appointment;
import com.example.HealthCare.service.AppointmentService;
import com.example.HealthCare.service.DoctorService;
import com.example.HealthCare.service.PatientService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AppointmentController {

    @Autowired AppointmentService appointmentService;
    @Autowired PatientService patientService;
    @Autowired DoctorService doctorService;

    @GetMapping("/appointments")
    public String getAllAppointments(Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments/list";
    }

    @GetMapping("/appointments/book")
    public String showBookForm(Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "appointments/book";
    }

    @PostMapping("/appointments/book")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        appointment.setStatus("Scheduled");
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments?success=booked";
    }

    @GetMapping("/appointments/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        Appointment appointment = appointmentService.getAppointmentById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        model.addAttribute("appointment", appointment);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "appointments/edit";
    }

    @PostMapping("/appointments/edit/{id}")
    public String updateAppointment(@PathVariable long id,
                                    @ModelAttribute Appointment appointment) {
        appointment.setId((int) id);
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments?success=updated";
    }

    @GetMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments?success=deleted";
    }
}
