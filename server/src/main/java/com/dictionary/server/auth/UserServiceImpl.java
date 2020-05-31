package com.dictionary.server.auth;

import com.dictionary.server.auth.exception.UserAlreadyExistException;
import com.dictionary.server.auth.exception.UserDoesNotExistException;
import com.dictionary.server.auth.exception.UserException;
import com.dictionary.server.auth.model.LoginUserDTO;
import com.dictionary.server.auth.model.RegisterUserDTO;
import com.dictionary.server.auth.model.User;
import com.dictionary.server.keycloak.KeycloakException;
import com.dictionary.server.keycloak.KeycloakFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private KeycloakFacade keycloakFacade;

    @Autowired
    UserServiceImpl(UserRepository userRepository, KeycloakFacade keycloakFacade) {
        this.userRepository = userRepository;
        this.keycloakFacade = keycloakFacade;
    }

    @Override
    public ResponseEntity register(RegisterUserDTO registerUserDTO) {
        if (userRepository.findByUsername(registerUserDTO.getUsername()) != null) {
            log.error("Someone tried to create existing username " + registerUserDTO.getUsername());
            throw new UserAlreadyExistException("User already exist in the DB!");
        }

        if (registerUserDTO.getUsername() == null || registerUserDTO.getPassword() == null) {
            throw new UserException("Password or user is null!");
        }

        String userUuid = keycloakFacade.registerUser(registerUserDTO);

        if(userUuid == null) {
            throw new KeycloakException("[Keycloak]: Cannot register user in keycloak: " + registerUserDTO.getUsername());
        }

        User user = User.builder()
                .username(registerUserDTO.getUsername())
                .uid(userUuid)
                .demoLimit(0)
                .premium(false)
                .dictionary(Collections.emptyList())
                .build();
        User savedUser = userRepository.save(user);

        if (savedUser == null) {
            log.error("User with UUID " + userUuid + " was register in keycloak, but can't add him to application's mysql db.");
            throw new UserAlreadyExistException("User already exist in the DB!");
        }

        log.info("User " + registerUserDTO.getUsername() + " | UUID: " + userUuid + " successfully registered.");
        return ResponseEntity.ok("User was registered");
    }

    @Override
    public String login(LoginUserDTO loginUserDTO) {
        return keycloakFacade.loginUser(loginUserDTO); // Bearer Token.
    }

    @Override
    public ResponseEntity saveEmail(String username, String email) {
        if (username == null) {
            throw new UserException("Username is null!");
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserDoesNotExistException("User does not exist!");
        }
        user.setEmail(email);
        userRepository.save(user);

        return ResponseEntity.ok("Email was saved.");
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
