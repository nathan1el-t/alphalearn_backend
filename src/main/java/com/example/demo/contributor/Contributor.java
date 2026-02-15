package com.example.demo.contributor;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.demo.profile.Profile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties("profile") //temporary fix to prevent loop, to review in the future
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contributors")
public class Contributor {

    @Id
    @Column(name = "contributor_id", columnDefinition = "uuid")
    private UUID contributorId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "contributor_id")
    private Profile profile;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
