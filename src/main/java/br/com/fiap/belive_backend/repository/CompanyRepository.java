package br.com.fiap.belive_backend.repository;

import br.com.fiap.belive_backend.model.Company;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CompanyRepository extends UserRepository<Company, String> {
    Optional<Company> findByCnpj(String cpf);
}
