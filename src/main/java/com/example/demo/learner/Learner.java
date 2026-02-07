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

@JsonIgnoreProperties("profile") //temporary fix to prevent loop, to review in the future
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

    @Column(name = "total_points", nullable = false)
    private short totalPoints;

    // Getters

    public UUID getLearnerId() {
        return learnerId;
    }

    public Profile getProfile() {
        return profile;
    }

    public short getTotalPoints() {
        return totalPoints;
    }

    // Setters

    public void setTotalPoints(short totalPoints) {
        this.totalPoints = totalPoints;
    }
}
