package com.kt.damim.exammanagement.repository;

import com.kt.damim.exammanagement.entity.SubmissionAnswer;
import com.kt.damim.exammanagement.entity.SubmissionAnswerId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface SubmissionAnswerRepository extends JpaRepository<SubmissionAnswer, SubmissionAnswerId> {
    List<SubmissionAnswer> findByExamIdAndUserId(Long examId, Long userId);
}
