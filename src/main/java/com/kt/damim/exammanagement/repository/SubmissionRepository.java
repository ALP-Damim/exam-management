package com.kt.damim.exammanagement.repository;

import com.kt.damim.exammanagement.entity.Submission;
import com.kt.damim.exammanagement.entity.SubmissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface SubmissionRepository extends JpaRepository<Submission, SubmissionId> {
    List<Submission> findByExamId(Long examId);
    Optional<Submission> findByExamIdAndUserId(Long examId, Long userId);
}
