package edu.co.ustavillavicencio.relationmapping.services;

import edu.co.ustavillavicencio.relationmapping.controllers.dtos.request.DoctorReqDTO;
import edu.co.ustavillavicencio.relationmapping.controllers.dtos.responses.DoctorResDTO;
import edu.co.ustavillavicencio.relationmapping.controllers.dtos.responses.HospitalResDTO;
import edu.co.ustavillavicencio.relationmapping.entities.Doctor;
import edu.co.ustavillavicencio.relationmapping.entities.Hospital;
import edu.co.ustavillavicencio.relationmapping.entities.UserApp;
import edu.co.ustavillavicencio.relationmapping.exception.BusinessRuleException;
import edu.co.ustavillavicencio.relationmapping.repositories.DoctorRepository;
import edu.co.ustavillavicencio.relationmapping.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final HospitalService hospitalService;

    public DoctorResDTO create(DoctorReqDTO reqDTO) {
        UserApp user = getDoctorUser(reqDTO.getUsername());
        if (doctorRepository.existsByUserUsername(reqDTO.getUsername())) {
            throw new BusinessRuleException("Ese usuario ya está asociado a un doctor");
        }

        Hospital hospital = hospitalService.getHospitalEntity(reqDTO.getHospitalId());

        Doctor doctor = new Doctor();
        applyChanges(doctor, reqDTO, user, hospital);
        return toDto(doctorRepository.save(doctor));
    }

    public List<DoctorResDTO> findAll() {
        return doctorRepository.findAll().stream().map(this::toDto).toList();
    }

    public DoctorResDTO findById(Long id) {
        return toDto(getDoctorEntity(id));
    }

    public DoctorResDTO update(Long id, DoctorReqDTO reqDTO) {
        Doctor doctor = getDoctorEntity(id);
        UserApp user = getDoctorUser(reqDTO.getUsername());

        if (doctorRepository.existsByUserUsernameAndIdNot(reqDTO.getUsername(), id)) {
            throw new BusinessRuleException("Ese usuario ya está asociado a otro doctor");
        }

        Hospital hospital = hospitalService.getHospitalEntity(reqDTO.getHospitalId());
        applyChanges(doctor, reqDTO, user, hospital);
        return toDto(doctorRepository.save(doctor));
    }

    public void delete(Long id) {
        doctorRepository.delete(getDoctorEntity(id));
    }

    public DoctorResDTO getCurrentDoctorProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new BusinessRuleException("No hay un usuario autenticado");
        }

        Doctor doctor = doctorRepository.findByUserUsername(authentication.getName())
                .orElseThrow(() -> new BusinessRuleException("El usuario autenticado no tiene perfil de doctor"));

        return toDto(doctor);
    }

    private Doctor getDoctorEntity(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Doctor no encontrado con id: " + id));
    }

    private UserApp getDoctorUser(String username) {
        UserApp user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessRuleException("No existe un usuario con username: " + username));

        String normalizedRole = user.getRole() == null ? "" : user.getRole().replace("ROLE_", "").trim().toUpperCase();
        if (!"DOCTOR".equals(normalizedRole)) {
            throw new BusinessRuleException("El usuario indicado no tiene rol DOCTOR");
        }

        return user;
    }

    private void applyChanges(Doctor doctor, DoctorReqDTO reqDTO, UserApp user, Hospital hospital) {
        doctor.setUser(user);
        doctor.setName(reqDTO.getName().trim());
        doctor.setLastName(reqDTO.getLastName().trim());
        doctor.setAge(reqDTO.getAge());
        doctor.setSpecialization(reqDTO.getSpecialization().trim());
        doctor.setHospital(hospital);
    }

    private DoctorResDTO toDto(Doctor doctor) {
        HospitalResDTO hospitalDTO = new HospitalResDTO(
                doctor.getHospital().getId(),
                doctor.getHospital().getName()
        );

        return new DoctorResDTO(
                doctor.getId(),
                doctor.getUser().getUsername(),
                doctor.getName(),
                doctor.getLastName(),
                doctor.getAge(),
                doctor.getSpecialization(),
                hospitalDTO
        );
    }
}
