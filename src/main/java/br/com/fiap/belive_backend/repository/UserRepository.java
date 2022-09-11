package br.com.fiap.belive_backend.repository;

import br.com.fiap.belive_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository<T extends User, K> extends MongoRepository<T, K> {
    Optional<T> findByUserLogin_Username(String username);
    Optional<T> findByUserLogin_UsernameAndUserLogin_Password(String username, String password);
}
