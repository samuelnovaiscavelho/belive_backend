package br.com.fiap.belive_backend.repository;

import br.com.fiap.belive_backend.model.Appointment;
import br.com.fiap.belive_backend.model.Customer;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    Optional<Appointment> findByCode(BigInteger appointmentCode);
    List<Appointment> findAllByCustomer(Customer customer);

    @SneakyThrows
    default Appointment update(Appointment updateAppointment) {
        Appointment appointment = findByCode(updateAppointment.getCode())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        BeanUtils.copyProperties(appointment, updateAppointment);
        return save(appointment);
    }
}
