package com.example.demo.learner;

import java.util.UUID;
import java.util.List;

import com.example.demo.learner.dto.response.LearnerContributorRoleDto;
import com.example.demo.learner.dto.response.LearnerDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/learners")
public class LearnerController {
    
    private final LearnerService learnerService;

    public LearnerController(LearnerService learnerService) {
        this.learnerService = learnerService;
    }

    @GetMapping
    public List<LearnerDto> getLearners(
            @RequestParam(defaultValue = "false") boolean excludeContributors
    ) {
        return learnerService.getAllLearners(excludeContributors);
    }

    @GetMapping({"/{learnerId}/is-contributor", "/{learnerId}/is_contributor"})
    public LearnerContributorRoleDto getLearnerContributorRole(@PathVariable UUID learnerId) {
        return learnerService.getLearnerContributorRole(learnerId);
    }
    
}
