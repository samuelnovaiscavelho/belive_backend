package br.com.fiap.belive_backend.controller;

import br.com.fiap.belive_backend.model.Doctor;
import br.com.fiap.belive_backend.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return new ResponseEntity<>(doctorService.register(token, doctor), HttpStatus.CREATED);
    }

   @GetMapping("/get/list")
    public ResponseEntity<List<Doctor>> getAll(@RequestHeader(value = "Authorization") String token){
        return ResponseEntity.ok(doctorService.findAll(token));
    }

    @GetMapping(value = "/get/list", params = "speciality")
    public ResponseEntity<List<Doctor>> getAllBySpeciality(@RequestHeader(value = "Authorization") String token,
                                                           @RequestParam(name = "speciality") String speciality){
        return ResponseEntity.ok(doctorService.findAllBySpecialityWithToken(token, speciality));
    }

    @GetMapping(value = "/get", params = "crm")
    public ResponseEntity<Doctor> getByCRM(@RequestHeader(value = "Authorization") String token,
                                           @RequestParam(name = "crm") Integer crm) {
        return ResponseEntity.ok(doctorService.findByCRMWithToken(token, crm));
    }

    @PatchMapping("/update")
    public ResponseEntity<Doctor> updateDoctor(@RequestHeader(value = "Authorization") String token, @RequestBody Doctor doctor){
        return ResponseEntity.ok(doctorService.updateWithToken(token, doctor));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDoctor(@RequestHeader(value = "Authorization") String token, @RequestBody Doctor doctor){
        doctorService.delete(token, doctor.getCrm());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/avaliable_schedule")
    public ResponseEntity<Map<String, Object>> avaliableSchedule(
            @RequestParam(defaultValue = "") Integer crm,
            @RequestParam(defaultValue = "") Integer day,
            @RequestParam(defaultValue = "") Integer month,
            @RequestParam(defaultValue = "") String cnpj ) {

        Map<String, Object> response = doctorService.avaliableScheduleByCRM(cnpj, day, month, crm);

        parseLocaleDateTimeToString(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/avaliable_schedule/list")
    public ResponseEntity<List<Map<String, Object>>> avaliableSchedule(
            @RequestParam(defaultValue = "") Integer day,
            @RequestParam(defaultValue = "") Integer month,
            @RequestParam(defaultValue = "") String cnpj,
            @RequestParam(defaultValue = "") String specialist ) {

        List<Map<String, Object>> response = doctorService.avaliableScheduleBySpecialist(cnpj, day, month, specialist);

        response.forEach(this::parseLocaleDateTimeToString);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void parseLocaleDateTimeToString(Map<String, Object> response) {
        List<LocalDateTime> localDateTimeList = (List<LocalDateTime>) response.get("scheduleAvailable");

        response.replace("scheduleAvailable", localDateTimeList.stream().map(localDateTime -> {
            DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return ZonedDateTime.of(localDateTime, ZoneId.of("America/Sao_Paulo")).format(FORMATTER);
        }).collect(Collectors.toList()));
    }
}

