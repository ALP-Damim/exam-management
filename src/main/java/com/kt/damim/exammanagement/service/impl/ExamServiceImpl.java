package com.kt.damim.exammanagement.service.impl;

import com.kt.damim.exammanagement.dto.QuestionDto;
import com.kt.damim.exammanagement.entity.Exam;
import com.kt.damim.exammanagement.entity.Question;
import com.kt.damim.exammanagement.repository.ExamRepository;
import com.kt.damim.exammanagement.repository.QuestionRepository;
import com.kt.damim.exammanagement.service.ExamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamServiceImpl implements ExamService {
    
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final ObjectMapper objectMapper;
    
    @Override
    public QuestionDto getQuestion(Long examId, int position) {
        // 시험 준비 상태 확인
        Exam exam = examRepository.findByIdAndIsReadyTrue(examId)
            .orElseThrow(() -> new IllegalArgumentException("준비되지 않은 시험이거나 존재하지 않는 시험입니다: examId=" + examId));
        
        Question question = questionRepository.findByExamIdAndPosition(examId, position)
            .orElseThrow(() -> new IllegalArgumentException("시험 문제를 찾을 수 없습니다: examId=" + examId + ", position=" + position));
        
        List<String> choices = null;
        if (question.getQtype().name().equals("MCQ")) {
            try {
                choices = objectMapper.readValue(question.getChoices(), new TypeReference<List<String>>() {});
            } catch (JsonProcessingException e) {
                log.error("선택지 JSON 파싱 오류", e);
                choices = List.of();
            }
        }
        
        return QuestionDto.of(question, choices);
    }
    
    @Override
    public boolean isExamReady(Long examId) {
        return examRepository.findByIdAndIsReadyTrue(examId).isPresent();
    }
}

