package com.quiz2.julianibarra;

@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String role; // ADMIN o DOCTOR
}


