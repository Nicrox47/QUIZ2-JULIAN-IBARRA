package edu.co.ustavillavicencio.relationmapping.repositories;

import edu.co.ustavillavicencio.relationmapping.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserUsername(String username);
    boolean existsByUserUsername(String username);
    boolean existsByUserUsernameAndIdNot(String username, Long id);
}
