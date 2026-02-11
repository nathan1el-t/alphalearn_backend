package com.example.demo.concept;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConceptDTO {

    private Integer conceptId;
    private String title;
    private String description;
    private String moderationStatus;
    private OffsetDateTime createdAt;
}