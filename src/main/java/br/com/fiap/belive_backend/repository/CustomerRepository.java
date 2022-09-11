package br.com.fiap.belive_backend.repository;

import br.com.fiap.belive_backend.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends UserRepository<Customer, String> {
    Optional<Customer> findByCpf(String cpf);
}
