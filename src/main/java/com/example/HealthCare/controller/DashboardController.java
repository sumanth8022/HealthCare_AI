package com.example.HealthCare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.HealthCare.service.AppointmentService;
import com.example.HealthCare.service.DoctorService;
import com.example.HealthCare.service.PatientService;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired PatientService patientService;
    @Autowired DoctorService doctorService;
    @Autowired AppointmentService appointmentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }

        model.addAttribute("totalPatients", patientService.countPatients());
        model.addAttribute("activePatients", patientService.countActivePatients());
        model.addAttribute("totalDoctors", doctorService.countDoctors());
        model.addAttribute("totalAppointments", appointmentService.countAppointments());
        model.addAttribute("username", session.getAttribute("username"));

        return "dashboard";
    }
}
