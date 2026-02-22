package com.example.demo.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.admin.dto.response.AdminLessonDetailDto;
import com.example.demo.lesson.Lesson;
import com.example.demo.lesson.LessonRepository;
import com.example.demo.lesson.enums.LessonModerationStatus;

@Service
public class AdminLessonService {
    private final LessonRepository lessonRepository;

    public AdminLessonService(LessonRepository lessonRepository){
        this.lessonRepository = lessonRepository;
    }

    public AdminLessonDetailDto approveLesson(Integer id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found."));

        if(lesson.getLessonModerationStatus() != LessonModerationStatus.PENDING){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Only PENDING lessons can be approved.");
        }

        lesson.setLessonModerationStatus(LessonModerationStatus.APPROVED);
        lessonRepository.save(lesson);

        return new AdminLessonDetailDto(
            lesson.getContributor().getContributorId(),
            lesson.getLessonId(),
            lesson.getTitle(),
            lesson.getLessonModerationStatus()
        );
    }

    public AdminLessonDetailDto rejectLesson(Integer id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found."));

        if(lesson.getLessonModerationStatus() != LessonModerationStatus.PENDING){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Only PENDING lessons can be rejected.");
        }

        lesson.setLessonModerationStatus(LessonModerationStatus.REJECTED);
        lessonRepository.save(lesson);

        return new AdminLessonDetailDto(
            lesson.getContributor().getContributorId(),
            lesson.getLessonId(),
            lesson.getTitle(),
            lesson.getLessonModerationStatus()
        );
    }
}
