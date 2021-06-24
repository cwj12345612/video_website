package com.cwj.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
用户类
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"email","id"})
public class user implements Serializable {
    private int id;
    private String username;
    private String password;
    private String email;
    }
