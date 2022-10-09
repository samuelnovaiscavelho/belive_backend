package br.com.fiap.belive_backend.controller;

import br.com.fiap.belive_backend.model.Doctor;
import br.com.fiap.belive_backend.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user/company/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

   @PostMapping("/register")
    public ResponseEntity<Doctor> registerDoctor(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid Doctor doctor){
        return new ResponseEntity(doctorService.register(token, doctor), HttpStatus.CREATED);
    }

   @GetMapping("/get/list")
    public ResponseEntity<List<Doctor>> getAll(@RequestHeader(value = "Authorization") String token){
        return ResponseEntity.ok(doctorService.findAll(token));
    }

    @GetMapping(value = "/get/list", params = "speciality")
    public ResponseEntity<List<Doctor>> getAllBySpeciality(@RequestHeader(value = "Authorization") String token,
                                                           @RequestParam(name = "speciality") String speciality){
        return ResponseEntity.ok(doctorService.findAllBySpeciality(token, speciality));
    }

    @GetMapping(value = "/get", params = "crm")
    public ResponseEntity<Doctor> getByCRM(@RequestHeader(value = "Authorization") String token,
                                           @RequestParam(name = "crm") Integer crm) {
        return ResponseEntity.ok(doctorService.findByCRM(token, crm));
    }

    @PatchMapping("/update")
    public ResponseEntity<Doctor> updateDoctor(@RequestHeader(value = "Authorization") String token, @RequestBody Doctor doctor){
        return ResponseEntity.ok(doctorService.update(token, doctor));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDoctor(@RequestHeader(value = "Authorization") String token, @RequestBody Doctor doctor){
        doctorService.delete(token, doctor.getCrm());
        return ResponseEntity.ok().build();
    }
}
