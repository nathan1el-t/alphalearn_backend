package com.example.demo.profile;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  ProfileRepository extends JpaRepository<Profile, UUID>{
    
}
