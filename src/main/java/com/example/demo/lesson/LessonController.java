package com.example.demo.lesson;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.lesson.dto.CreateLessonRequest;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<Lesson> getLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{lessonId}")
    public Lesson getLesson(@PathVariable Integer lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lesson createLesson(@RequestBody CreateLessonRequest request) {
        return lessonService.createLesson(request);
    }
}
