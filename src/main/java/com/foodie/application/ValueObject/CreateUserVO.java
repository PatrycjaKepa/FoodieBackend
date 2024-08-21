package com.foodie.application.ValueObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserVO {
    @Column
    private String login;
    @Column
    private String email;
    @Column
    private String password;
}
