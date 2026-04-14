package edu.co.ustavillavicencio.relationmapping.services;

import edu.co.ustavillavicencio.relationmapping.controllers.dtos.request.HospitalReqDTO;
import edu.co.ustavillavicencio.relationmapping.controllers.dtos.responses.HospitalResDTO;
import edu.co.ustavillavicencio.relationmapping.entities.Hospital;
import edu.co.ustavillavicencio.relationmapping.exception.BusinessRuleException;
import edu.co.ustavillavicencio.relationmapping.repositories.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalResDTO create(HospitalReqDTO reqDTO) {
        validateUniqueName(reqDTO.getName(), null);
        Hospital hospital = new Hospital();
        hospital.setName(reqDTO.getName().trim());
        return toDto(hospitalRepository.save(hospital));
    }

    public List<HospitalResDTO> findAll() {
        return hospitalRepository.findAll().stream().map(this::toDto).toList();
    }

    public HospitalResDTO findById(Long id) {
        return toDto(getHospitalEntity(id));
    }

    public HospitalResDTO update(Long id, HospitalReqDTO reqDTO) {
        Hospital hospital = getHospitalEntity(id);
        validateUniqueName(reqDTO.getName(), id);
        hospital.setName(reqDTO.getName().trim());
        return toDto(hospitalRepository.save(hospital));
    }

    public void delete(Long id) {
        Hospital hospital = getHospitalEntity(id);
        hospitalRepository.delete(hospital);
    }

    public Hospital getHospitalEntity(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Hospital no encontrado con id: " + id));
    }

    private void validateUniqueName(String name, Long currentId) {
        hospitalRepository.findByNameIgnoreCase(name.trim())
                .ifPresent(existing -> {
                    if (currentId == null || !existing.getId().equals(currentId)) {
                        throw new BusinessRuleException("Ya existe un hospital con ese nombre");
                    }
                });
    }

    private HospitalResDTO toDto(Hospital hospital) {
        return new HospitalResDTO(hospital.getId(), hospital.getName());
    }
}
