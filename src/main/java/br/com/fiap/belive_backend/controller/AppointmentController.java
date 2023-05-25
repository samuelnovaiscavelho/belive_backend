package br.com.fiap.belive_backend.controller;

import br.com.fiap.belive_backend.dto.AppointmentDTO;
import br.com.fiap.belive_backend.model.Appointment;
import br.com.fiap.belive_backend.service.AppointmentService;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/ping")
    public String pong() {
        return "pong";
    }

    @GetMapping("/get/list")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments(@RequestHeader(name = "Authorization") String token){
        return ResponseEntity.ok(appointmentService.findAllByTokenDTO(token));
    }

    @PostMapping("/create")
    public ResponseEntity<Appointment> createAppointment(@RequestHeader(name = "Authorization") String token,
                                                         @RequestBody AppointmentDTO appointmentDTO) {
        return new ResponseEntity<>(appointmentService.createAppointment(token, appointmentDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<Appointment> updateAppointment(@RequestHeader(name = "Authorization") String token,
                                                         @RequestParam(name = "code") Integer appointmentCode,
                                                         @RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appointmentService.updateAppointment(token, appointmentDTO, appointmentCode));
    }

    @GetMapping("/cancel")
    public ResponseEntity<Appointment> cancelAppointment(@RequestHeader(name = "Authorization") String token,
                                                         @RequestParam(name = "code") Integer appointmentCode) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(token, appointmentCode));
    }

    @GetMapping("/get/next_appointment")
    public ResponseEntity<Optional<AppointmentDTO>> findNextAppointment(@RequestHeader(name = "Authorization") String token){
        return ResponseEntity.ok(appointmentService.findNextAppointment(token));
    }
}
