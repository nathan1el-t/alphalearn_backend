package com.example.demo.admin;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.demo.learner.Learner;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @Column(name = "admin_id", columnDefinition = "uuid")
    private UUID adminId;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
