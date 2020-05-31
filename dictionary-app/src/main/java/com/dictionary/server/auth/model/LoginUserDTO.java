package com.dictionary.server.auth.model;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class LoginUserDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
