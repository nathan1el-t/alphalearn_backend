package com.example.demo.contributor;


import java.util.List;
import java.util.UUID;

import com.example.demo.contributor.dto.request.DemoteContributorsRequest;
import com.example.demo.contributor.dto.request.PromoteContributorsRequest;
import com.example.demo.contributor.dto.response.ContributorDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/contributors")
public class ContributorController {
    
    private final ContributorService contributorService;

    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @GetMapping
    public List<ContributorDto> getContributors() {
        return contributorService.getAllContributors();
    }

    @PostMapping("/promote")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ContributorDto> promoteLearners(@RequestBody PromoteContributorsRequest request) {
        return contributorService.promoteLearners(request);
    }

    @DeleteMapping("/demote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void demoteContributors(@RequestBody DemoteContributorsRequest request) {
        contributorService.demoteContributors(request);
    }
}
