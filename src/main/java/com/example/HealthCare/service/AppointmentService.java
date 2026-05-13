package com.example.HealthCare.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HealthCare.model.Appointment;
import com.example.HealthCare.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepo;

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public Appointment saveAppointment(Appointment a) {
        return appointmentRepo.save(a);
    }

    public Optional<Appointment> getAppointmentById(long id) {
        return appointmentRepo.findById(id);
    }

    public void deleteAppointment(long id) {
        appointmentRepo.deleteById(id);
    }

    public List<Appointment> getTodayAppointments(String date) {
        return appointmentRepo.findByDate(date);
    }

    public long countAppointments() {
        return appointmentRepo.count();
    }
}
