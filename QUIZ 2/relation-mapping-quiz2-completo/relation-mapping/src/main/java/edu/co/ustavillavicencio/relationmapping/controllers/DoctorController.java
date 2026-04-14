package edu.co.ustavillavicencio.relationmapping.controllers;

import edu.co.ustavillavicencio.relationmapping.controllers.dtos.request.DoctorReqDTO;
import edu.co.ustavillavicencio.relationmapping.controllers.dtos.responses.DoctorResDTO;
import edu.co.ustavillavicencio.relationmapping.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/doctors")
    public ResponseEntity<DoctorResDTO> create(@Valid @RequestBody DoctorReqDTO reqDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.create(reqDTO));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResDTO>> findAll() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorResDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.findById(id));
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<DoctorResDTO> update(@PathVariable Long id, @Valid @RequestBody DoctorReqDTO reqDTO) {
        return ResponseEntity.ok(doctorService.update(id, reqDTO));
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctor/me")
    public ResponseEntity<DoctorResDTO> me() {
        return ResponseEntity.ok(doctorService.getCurrentDoctorProfile());
    }
}
