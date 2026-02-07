package com.example.demo.contributor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ContributorService {
    private final ContributorRepository contributorRepository;

    public ContributorService(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    public List<Contributor> getAllContributors() {
        return contributorRepository.findAll();
    }
}
