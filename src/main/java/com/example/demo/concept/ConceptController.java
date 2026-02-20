package com.example.demo.concept;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/concepts")
public class ConceptController {

    // read only controller, create/update/delete endpoints are at AdminConceptController
    private final ConceptService conceptService;

    public ConceptController(ConceptService conceptService) {
        this.conceptService = conceptService;
    }

    @GetMapping
    public List<ConceptDTO> getConcepts() {
        return conceptService.getAllConcepts();
    }

    @GetMapping("/{id}")
    public ConceptDTO getConceptById(@PathVariable Integer id) {
        return conceptService.getConceptById(id);
    }

}
