package br.com.fiap.belive_backend.service;


import br.com.fiap.belive_backend.dto.UserDTO;
import br.com.fiap.belive_backend.model.User;
import br.com.fiap.belive_backend.model.User.UserLogin;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public interface DefaultUserService<T extends User, U extends UserDTO> {

    T register(U dto);

    Map<String, String> login(User.UserLogin userLogin);

    T getUserByUsername(String token);

    default void encryptPassword(UserLogin userLogin){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(userLogin.getPassword());
        userLogin.setPassword(encryptedPassword);
    }

    default Map<String, String> generateBasicToken(UserLogin userLogin, T user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(userLogin.getPassword(), user.getUserLogin().getPassword())) {
            String auth = userLogin.getUsername().concat(":").concat(userLogin.getPassword());

            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));

            Map<String, String> basicToken = new HashMap<>();

            basicToken.put("token", "Basic ".concat(new String(encodedAuth)));

            userLogin.setUsername(user.getUserLogin().getUsername());

            return basicToken;
        }
        return null;
    }
}
