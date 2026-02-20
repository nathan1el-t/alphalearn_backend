package com.example.demo.contributor;

import java.util.List;

import com.example.demo.contributor.dto.request.DemoteContributorsRequest;
import com.example.demo.contributor.dto.request.PromoteContributorsRequest;
import com.example.demo.contributor.dto.response.ContributorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/contributors")
public class ContributorAdminController {

    private final ContributorAdminService contributorAdminService;

    public ContributorAdminController(ContributorAdminService contributorAdminService) {
        this.contributorAdminService = contributorAdminService;
    }

    @PostMapping("/promote")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ContributorDto> promoteLearners(@RequestBody PromoteContributorsRequest request) {
        return contributorAdminService.promoteLearners(request);
    }

    @DeleteMapping("/demote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void demoteContributors(@RequestBody DemoteContributorsRequest request) {
        contributorAdminService.demoteContributors(request);
    }
}
