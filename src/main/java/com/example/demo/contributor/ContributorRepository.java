package com.example.demo.contributor;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContributorRepository extends JpaRepository<Contributor, UUID> {

    @Query("""
            select c.contributorId
            from Contributor c
            where c.promotedAt is not null
              and (c.demotedAt is null or c.promotedAt > c.demotedAt)
            """)
    Set<UUID> findCurrentContributorIds();

    @Query("""
            select count(c)
            from Contributor c
            where c.contributorId = :contributorId
              and c.promotedAt is not null
              and (c.demotedAt is null or c.promotedAt > c.demotedAt)
            """)
    long countCurrentContributorById(@Param("contributorId") UUID contributorId);

    default boolean existsCurrentContributorById(UUID contributorId) {
        return countCurrentContributorById(contributorId) > 0;
    }
}
