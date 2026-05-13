package com.example.HealthCare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.HealthCare.model.Patient;
import com.example.HealthCare.service.PatientService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PatientController {

    @Autowired PatientService patientService;

    @GetMapping("/patients")
    public String getAllPatients(Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/list";
    }

    @GetMapping("/patients/add")
    public String showForm(Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        model.addAttribute("patient", new Patient());
        return "patients/add";
    }

    @PostMapping("/patients/add")
    public String savePatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients?success=added";
    }

    @GetMapping("/patients/search")
    public String searchPatient(
            @RequestParam(required = false, defaultValue = "") String keyword,
            Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        model.addAttribute("patients", patientService.searchPatients(keyword));
        model.addAttribute("keyword", keyword);
        return "patients/list";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return "redirect:/patients?success=deleted";
    }

    @GetMapping("/patients/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model, HttpSession session) {
        if(session.getAttribute("loggedIn") == null) return "redirect:/login";
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        model.addAttribute("patient", patient);
        return "patients/edit";
    }

    @PostMapping("/patients/edit/{id}")
    public String updatePatient(@PathVariable long id, @ModelAttribute Patient patient) {
        patient.setId((int) id);
        patientService.savePatient(patient);
        return "redirect:/patients?success=updated";
    }
}
