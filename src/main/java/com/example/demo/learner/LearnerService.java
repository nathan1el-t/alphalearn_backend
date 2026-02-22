package com.example.demo.learner;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.demo.contributor.ContributorRepository;
import com.example.demo.learner.dto.response.LearnerContributorRoleDto;
import com.example.demo.learner.dto.response.LearnerDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LearnerService {
    
    private final LearnerRepository learnerRepository;
    private final ContributorRepository contributorRepository;

    public LearnerService(
            LearnerRepository learnerRepository,
            ContributorRepository contributorRepository
    ) {
        this.learnerRepository = learnerRepository;
        this.contributorRepository = contributorRepository;
    }

    public List<LearnerDto> getAllLearners(boolean excludeContributors) {
        List<Learner> learners = learnerRepository.findAll();
        Set<UUID> currentContributorIds = contributorRepository.findCurrentContributorIds();

        return learners.stream()
                .filter(learner -> !excludeContributors || !currentContributorIds.contains(learner.getId()))
                .map(learner -> toDto(learner, currentContributorIds.contains(learner.getId())))
                .toList();
    }

    public LearnerContributorRoleDto getLearnerContributorRole(UUID learnerId) {
        if (!learnerRepository.existsById(learnerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Learner not found: " + learnerId);
        }

        return new LearnerContributorRoleDto(
                learnerId,
                contributorRepository.existsCurrentContributorById(learnerId)
        );
    }

    LearnerDto toDto(Learner learner, boolean isContributor) {
        return new LearnerDto(
                learner.getId(),
                learner.getUsername(),
                learner.getCreatedAt(),
                learner.getTotalPoints(),
                isContributor
        );
    }
}
