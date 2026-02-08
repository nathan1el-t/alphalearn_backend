package com.example.demo.learner;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties("profile") //temporary fix to prevent loop, to review in the future
@Getter
@NoArgsConstructor
@Entity
@Table(name = "learners")
public class Learner {

    @Id
    @Column(name = "learner_id", columnDefinition = "uuid")
    private UUID learnerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "learner_id")
    private Profile profile;

    @Setter
    @Column(name = "total_points", nullable = false)
    private short totalPoints;
}