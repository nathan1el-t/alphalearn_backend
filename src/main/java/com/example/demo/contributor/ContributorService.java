package com.example.demo.contributor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.example.demo.contributor.dto.request.DemoteContributorsRequest;
import com.example.demo.contributor.dto.request.PromoteContributorsRequest;
import com.example.demo.contributor.dto.response.ContributorDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.demo.learner.Learner;
import com.example.demo.contributor.dto.response.ContributorDto;
import com.example.demo.learner.LearnerRepository;
import com.example.demo.lesson.LessonRepository;

@Service
public class ContributorService {
    private final ContributorRepository contributorRepository;
    private final LearnerRepository learnerRepository;
    private final LessonRepository lessonRepository;

    public ContributorService(
            ContributorRepository contributorRepository,
            LearnerRepository learnerRepository,
            LessonRepository lessonRepository
    ) {
        this.contributorRepository = contributorRepository;
        this.learnerRepository = learnerRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<ContributorDto> getAllContributors() {
        return contributorRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public List<ContributorDto> promoteLearners(PromoteContributorsRequest request) {
        if (request == null || request.learnerIds() == null || request.learnerIds().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "learnerIds are required");
        }

        List<Contributor> created = new java.util.ArrayList<>();
        for (UUID learnerId : request.learnerIds()) {
            if (learnerId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "learnerIds cannot contain null");
            }
            Learner learner = learnerRepository.findById(learnerId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Learner not found: " + learnerId));

            if (learner.getId() == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Learner id is null: " + learnerId);
            }

            if (contributorRepository.existsById(learnerId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Already a contributor: " + learnerId);
            }

            Contributor contributor = new Contributor(
                    learnerId,
                    null,
                    OffsetDateTime.now()
            );
            created.add(contributorRepository.save(contributor));
        }

        return created.stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public void demoteContributors(DemoteContributorsRequest request) {
        if (request == null || request.contributorIds() == null || request.contributorIds().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contributorIds are required");
        }

        for (UUID contributorId : request.contributorIds()) {
            if (!contributorRepository.existsById(contributorId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contributor not found: " + contributorId);
            }
            if (lessonRepository.existsByContributor_ContributorId(contributorId)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Contributor has lessons and cannot be demoted: " + contributorId
                );
            }
        }

        for (UUID contributorId : request.contributorIds()) {
            contributorRepository.deleteById(contributorId);
        }
    }

    private ContributorDto toDto(Contributor contributor) {
        return new ContributorDto(
                contributor.getContributorId(),
                contributor.getCreatedAt()
        );
    }
}
