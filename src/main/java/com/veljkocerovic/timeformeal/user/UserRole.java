package com.veljkocerovic.timeformeal.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
