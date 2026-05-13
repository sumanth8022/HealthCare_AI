package com.example.HealthCare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.HealthCare.model.Patient;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByFullNameContaining(String keyword);
    List<Patient> findByDiseaseContaining(String keyword);
    List<Patient> findByPhoneContaining(String keyword);
    long countByStatus(String status);
}
