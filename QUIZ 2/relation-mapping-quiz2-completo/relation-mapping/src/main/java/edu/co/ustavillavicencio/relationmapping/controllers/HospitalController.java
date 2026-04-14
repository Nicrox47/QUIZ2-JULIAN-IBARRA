package edu.co.ustavillavicencio.relationmapping.controllers;

import edu.co.ustavillavicencio.relationmapping.controllers.dtos.request.HospitalReqDTO;
import edu.co.ustavillavicencio.relationmapping.controllers.dtos.responses.HospitalResDTO;
import edu.co.ustavillavicencio.relationmapping.services.HospitalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<HospitalResDTO> create(@Valid @RequestBody HospitalReqDTO reqDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.create(reqDTO));
    }

    @GetMapping
    public ResponseEntity<List<HospitalResDTO>> findAll() {
        return ResponseEntity.ok(hospitalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(hospitalService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalResDTO> update(@PathVariable Long id, @Valid @RequestBody HospitalReqDTO reqDTO) {
        return ResponseEntity.ok(hospitalService.update(id, reqDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hospitalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
