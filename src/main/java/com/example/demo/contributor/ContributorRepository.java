package com.example.demo.contributor;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributorRepository extends JpaRepository<Contributor, UUID> {
    
}
