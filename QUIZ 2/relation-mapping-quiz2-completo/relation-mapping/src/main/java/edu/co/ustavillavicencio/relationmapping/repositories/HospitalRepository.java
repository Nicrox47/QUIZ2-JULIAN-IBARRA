package edu.co.ustavillavicencio.relationmapping.repositories;

import edu.co.ustavillavicencio.relationmapping.entities.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByNameIgnoreCase(String name);
}
