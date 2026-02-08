package com.example.demo.profile;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @Column(columnDefinition= "uuid")
    private UUID id;

    @Setter
    @Column(unique= true)
    private String username;
}