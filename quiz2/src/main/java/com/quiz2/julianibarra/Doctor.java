package com.quiz2.julianibarra;

@Entity
public class Doctor {
    @Id @GeneratedValue
    private Long id;
    private String username; // validar que sea doctor
    private String name;
    private String lastname;
    private int age;
    private String specialization;

    @ManyToOne
    private Hospital hospital;
}
