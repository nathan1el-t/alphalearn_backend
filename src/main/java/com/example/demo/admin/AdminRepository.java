package com.example.demo.admin;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
}
