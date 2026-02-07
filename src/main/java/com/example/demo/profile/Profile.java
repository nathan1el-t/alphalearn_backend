package com.example.demo.profile;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "profiles")
public class Profile {
    
    @Id
    @Column(columnDefinition= "uuid")
    private UUID id;

    @Column(unique= true)
    private String username;

    // Getters

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }
}
