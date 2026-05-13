package com.example.HealthCare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HealthCare.model.Doctor;
import com.example.HealthCare.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepo;
	
	
	public List<Doctor> getAllDoctors(){
		return doctorRepo.findAll();
	}
	
	
	public Doctor SaveDoctor(Doctor d){
		return doctorRepo.save(d);
	}
	
	public Optional<Doctor> getDoctorById(long id) {
		return doctorRepo.findById(id);
	}
	
	public void deleteDoctor(long id) {
		doctorRepo.deleteById(id);;
	}
	
	public long countDoctors() {
		return doctorRepo.count();
	}

}
