package br.com.fiap.belive_backend.security.service;

import br.com.fiap.belive_backend.model.User;
import br.com.fiap.belive_backend.repository.UserRepository;
import br.com.fiap.belive_backend.security.model.UserDetailsImpl;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        return (UserDetails) userRepository.findByUserLogin_Username(username)
                .map( user -> new UserDetailsImpl((User) user))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username '%s' not found", username)));
    }
}
