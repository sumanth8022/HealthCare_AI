package com.example.HealthCare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HealthCare.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
	List<Appointment> findByDate(String date);
}
