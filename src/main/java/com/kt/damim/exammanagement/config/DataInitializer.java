package com.kt.damim.exammanagement.config;

import com.kt.damim.exammanagement.entity.Exam;
import com.kt.damim.exammanagement.entity.Question;
import com.kt.damim.exammanagement.entity.QuestionType;
import com.kt.damim.exammanagement.repository.ExamRepository;
import com.kt.damim.exammanagement.repository.QuestionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;


@Slf4j
@Component
@Profile("!prod")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;
    
    @Override
    public void run(String... args) throws Exception {
        if (examRepository.count() == 0) {
            initializeSampleData();
        }
    }
    
    private void initializeSampleData() throws JsonProcessingException {
        log.info("샘플 시험 데이터를 초기화합니다...");
        
        // 시험 생성
        Exam exam = new Exam();
        exam.setName("2024년 중간고사");
        exam.setDifficulty("중");
        exam.setIsReady(true); // 시험을 준비 완료 상태로 설정
        exam.setCreatedBy(1L);
        exam.setCreatedAt(Instant.now());
        exam = examRepository.save(exam);
        
        // 객관식 문제
        Question mcq1 = new Question();
        mcq1.setExam(exam);
        mcq1.setPosition(1);
        mcq1.setQtype(QuestionType.MCQ);
        mcq1.setBody("다음 중 Java의 특징이 아닌 것은?");
        mcq1.setChoices(objectMapper.writeValueAsString(Arrays.asList("A. 객체지향", "B. 플랫폼 독립적", "C. 컴파일 언어", "D. 인터프리터 언어")));
        mcq1.setAnswerKey("D");
        mcq1.setPoints(new BigDecimal("10.0"));
        questionRepository.save(mcq1);
        
        Question mcq2 = new Question();
        mcq2.setExam(exam);
        mcq2.setPosition(2);
        mcq2.setQtype(QuestionType.MCQ);
        mcq2.setBody("Spring Boot에서 @RestController 어노테이션의 역할은?");
        mcq2.setChoices(objectMapper.writeValueAsString(Arrays.asList("A. 데이터베이스 연결", "B. REST API 엔드포인트 제공", "C. 보안 설정", "D. 로깅 설정")));
        mcq2.setAnswerKey("B");
        mcq2.setPoints(new BigDecimal("10.0"));
        questionRepository.save(mcq2);
        
        // 단답식 문제
        Question short1 = new Question();
        short1.setExam(exam);
        short1.setPosition(3);
        short1.setQtype(QuestionType.SHORT);
        short1.setBody("JPA에서 엔티티 간의 관계를 표현하는 어노테이션 3가지를 작성하세요.");
        short1.setChoices(null);
        short1.setAnswerKey("@OneToOne, @OneToMany, @ManyToOne, @ManyToMany");
        short1.setPoints(new BigDecimal("15.0"));
        questionRepository.save(short1);
        
        log.info("샘플 시험 데이터 초기화 완료: 시험 ID={}, 문제 수={}", exam.getId(), 3);
    }
}

