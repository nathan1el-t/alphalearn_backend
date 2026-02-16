package com.example.demo.lesson;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.lesson.dto.CreateLessonRequest;
import com.example.demo.lesson.dto.LessonDTO;
import com.example.demo.lesson.dto.UpdateLessonRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/lessons")
@Tag(name = "Lessons", description = "Lesson creation and moderation endpoints")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    @Operation(summary = "List lessons", description = "Optionally filter by conceptId or contributorId")
    public List<LessonDTO> getLessons(
            @RequestParam(required = false) Integer conceptId,
            @RequestParam(required = false) java.util.UUID contributorId
    ) {
        return lessonService.getLessons(conceptId, contributorId);
    }

    @GetMapping("/{lessonId}")
    @Operation(summary = "Get lesson by ID")
    public LessonDTO getLesson(@PathVariable Integer lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create lesson")
    public LessonDTO createLesson(@RequestBody CreateLessonRequest request) {
        return lessonService.createLesson(request);
    }

    @PutMapping("/{lessonId}")
    @Operation(summary = "Update lesson content")
    public LessonDTO updateLesson(
            @PathVariable Integer lessonId,
            @RequestBody UpdateLessonRequest request
    ) {
        return lessonService.updateLesson(lessonId, request);
    }

    @PostMapping("/{lessonId}/submit")
    @Operation(summary = "Submit lesson for review")
    public LessonDTO submitLesson(@PathVariable Integer lessonId) {
        return lessonService.submitLesson(lessonId);
    }

    @PostMapping("/{lessonId}/unpublish")
    @Operation(summary = "Unpublish lesson")
    public LessonDTO unpublishLesson(@PathVariable Integer lessonId) {
        return lessonService.unpublishLesson(lessonId);
    }
}
