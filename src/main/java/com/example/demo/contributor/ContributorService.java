package com.example.demo.contributor;

import java.util.List;

import com.example.demo.contributor.dto.response.ContributorDto;
import org.springframework.stereotype.Service;

@Service
public class ContributorService {

    private final ContributorRepository contributorRepository;

    public ContributorService(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    public List<ContributorDto> getAllContributors() {
        return contributorRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    ContributorDto toDto(Contributor contributor) {
        return new ContributorDto(
                contributor.getContributorId(),
                contributor.getCreatedAt()
        );
    }
}
