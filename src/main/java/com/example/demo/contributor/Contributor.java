package com.example.demo.contributor;

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
import lombok.Setter;

@JsonIgnoreProperties("learner") //temporary fix to prevent loop, to review in the future
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contributors")
public class Contributor {

    @Id
    @Column(name = "contributor_id", columnDefinition = "uuid")
    private UUID contributorId;

    @OneToOne(optional = false)
    @JoinColumn(name = "contributor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Learner learner;
    
    // @Column(name = "total_points")
    // private Short totalPoints;

    @Column(name = "promoted_at", nullable = false)
    private OffsetDateTime promotedAt;

    @Column(name = "demoted_at")
    private OffsetDateTime demotedAt;

}
