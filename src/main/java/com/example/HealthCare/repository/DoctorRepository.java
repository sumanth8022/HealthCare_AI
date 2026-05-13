package com.example.HealthCare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HealthCare.model.Doctor;
import com.example.HealthCare.model.Patient;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	

}
