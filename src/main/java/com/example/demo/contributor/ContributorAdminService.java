package com.example.demo.contributor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.demo.contributor.dto.request.DemoteContributorsRequest;
import com.example.demo.contributor.dto.request.PromoteContributorsRequest;
import com.example.demo.contributor.dto.response.ContributorDto;
import com.example.demo.learner.Learner;
import com.example.demo.learner.LearnerRepository;
import com.example.demo.lesson.LessonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContributorAdminService {

    private final ContributorRepository contributorRepository;
    private final LearnerRepository learnerRepository;
    private final LessonRepository lessonRepository;
    private final ContributorService contributorService;

    public ContributorAdminService(
            ContributorRepository contributorRepository,
            LearnerRepository learnerRepository,
            LessonRepository lessonRepository,
            ContributorService contributorService
    ) {
        this.contributorRepository = contributorRepository;
        this.learnerRepository = learnerRepository;
        this.lessonRepository = lessonRepository;
        this.contributorService = contributorService;
    }

    @Transactional
    public List<ContributorDto> promoteLearners(PromoteContributorsRequest request) {
        if (request == null || request.learnerIds() == null || request.learnerIds().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "learnerIds are required");
        }

        List<Contributor> created = new ArrayList<>();
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
                    OffsetDateTime.now(),
                    null
            );
            created.add(contributorRepository.save(contributor));
        }

        return created.stream()
                .map(contributorService::toDto)
                .toList();
    }

    @Transactional
    public void demoteContributors(DemoteContributorsRequest request) {
        if (request == null || request.contributorIds() == null || request.contributorIds().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contributorIds are required");
        }

        for (UUID contributorId : request.contributorIds()) {
            if (contributorId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "contributorIds cannot contain null");
            }
            if (!contributorRepository.existsById(contributorId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contributor not found: " + contributorId);
            }
        }

        for (UUID contributorId : request.contributorIds()) {
            Contributor contributor = contributorRepository.findById(contributorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contributor not found " + contributorId));
            contributor.setDemotedAt(OffsetDateTime.now());
            lessonRepository.unpublishByContributorId(contributorId);
        }
    }
}
