package com.example.demo.admin;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.admin.dto.response.AdminLessonDetailDto;

@RestController()
@RequestMapping("/api/admin/lessons")
public class AdminLessonController {
    private final AdminLessonService adminService;

    public AdminLessonController(AdminLessonService adminService){
        this.adminService = adminService;
    }

    @PutMapping("/{id}/approve")
    public AdminLessonDetailDto approveLesson(@PathVariable Integer id){
        return adminService.approveLesson(id);
    }

    @PutMapping("/{id}/reject")
    public AdminLessonDetailDto rejectLesson(@PathVariable Integer id){
        return adminService.rejectLesson(id);
    }

}
