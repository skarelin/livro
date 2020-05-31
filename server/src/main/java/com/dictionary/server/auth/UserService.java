package com.dictionary.server.auth;

import com.dictionary.server.auth.model.LoginUserDTO;
import com.dictionary.server.auth.model.RegisterUserDTO;
import com.dictionary.server.auth.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity register(RegisterUserDTO registerUserDTO);

    String login(LoginUserDTO loginUserDTO);

    ResponseEntity saveEmail(String username, String email);

    User findByUsername(String username);
}
