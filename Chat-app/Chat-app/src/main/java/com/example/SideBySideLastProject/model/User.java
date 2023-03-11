package com.example.SideBySideLastProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "user_name")
    private String user_name;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name = "gender")
    private String gender;
    @Column(name = "age")
    private Integer age;

    @Column(name = "updated_time")
    private Timestamp updated_time;
    @Column(name = "created_time")
    private Timestamp created_time;

    @ManyToOne
    @JoinColumn(name = "status_id_status_id")
    private Status status_id;
}
