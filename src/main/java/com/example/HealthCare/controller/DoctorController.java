package com.example.HealthCare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.HealthCare.model.Doctor;
import com.example.HealthCare.service.DoctorService;
import jakarta.servlet.http.HttpSession;

@Controller
public class DoctorController {

    @Autowired DoctorService doctorService;

    @GetMapping("/doctors")
    public String getAllDoctors(Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors/list";
    }

    @GetMapping("/doctors/add")
    public String showForm(Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        model.addAttribute("doctor", new Doctor());
        return "doctors/add";
    }

    @PostMapping("/doctors/add")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorService.SaveDoctor(doctor);
        return "redirect:/doctors?success=added";
    }

    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors?success=deleted";
    }

    @GetMapping("/doctors/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        Doctor doctor = doctorService.getDoctorById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        model.addAttribute("doctor", doctor);
        return "doctors/edit";
    }

    @PostMapping("/doctors/edit/{id}")
    public String updateDoctor(@PathVariable long id, @ModelAttribute Doctor doctor) {
        doctor.setId((int) id);
        doctorService.SaveDoctor(doctor);
        return "redirect:/doctors?success=updated";
    }
}
