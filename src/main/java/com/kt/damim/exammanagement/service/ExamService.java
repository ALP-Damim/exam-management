package com.kt.damim.exammanagement.service;

import com.kt.damim.exammanagement.dto.QuestionDto;

public interface ExamService {
    QuestionDto getQuestion(Long examId, int position);
    boolean isExamReady(Long examId);
}
