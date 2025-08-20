package com.kt.damim.exammanagement.controller;

import com.kt.damim.exammanagement.dto.QuestionDto;
import com.kt.damim.exammanagement.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {
    
    private final ExamService examService;
    
    @GetMapping("/{examId}/questions/{idx}")
    public QuestionDto getQuestion(
        @PathVariable Long examId,
        @PathVariable int idx
    ) {
        return examService.getQuestion(examId, idx);
    }
}
