package com.example.HealthCare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HealthCare.model.Patient;
import com.example.HealthCare.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientrepo;

    public List<Patient> getAllPatients() {
        return patientrepo.findAll();
    }

    public Patient savePatient(Patient p) {
        return patientrepo.save(p);
    }

    public Optional<Patient> getPatientById(long id) {
        return patientrepo.findById(id);
    }

    public void deletePatient(long id) {
        patientrepo.deleteById(id);
    }

    public List<Patient> searchPatients(String keyword) {
        List<Patient> results = new ArrayList<>();
        results.addAll(patientrepo.findByFullNameContaining(keyword));
        results.addAll(patientrepo.findByDiseaseContaining(keyword));
        results.addAll(patientrepo.findByPhoneContaining(keyword));
        return results;
    }

    public long countPatients() {
        return patientrepo.count();
    }

    public long countActivePatients() {
        return patientrepo.countByStatus("Active");
    }
}
