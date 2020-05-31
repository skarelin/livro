package com.dictionary.server.auth;

import com.dictionary.server.auth.model.EmailUserDTO;
import com.dictionary.server.auth.model.LoginUserDTO;
import com.dictionary.server.auth.model.RegisterUserDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ApiOperation("Register user library")
    @ResponseBody
    public ResponseEntity register(@RequestBody RegisterUserDTO registerUserDTO) {
        return userService.register(registerUserDTO);
    }

    @PostMapping("/login")
    @ApiOperation("Login user library")
    public String login(@RequestBody LoginUserDTO loginUserDTO) {
        return userService.login(loginUserDTO);
    }

    @PostMapping("/sendEmail")
    @ApiOperation("Save user library email")
    public ResponseEntity sendEmail(Principal principal, @RequestHeader("Authorization") String authHeader, @RequestBody EmailUserDTO emailUserDTO) {
        String username = principal.getName();
        return userService.saveEmail(username, emailUserDTO.getEmail());
    }
}
