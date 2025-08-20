package com.kt.damim.exammanagement.repository;

import com.kt.damim.exammanagement.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    
    @Query("SELECT e FROM Exam e WHERE e.id = :examId AND e.isReady = true")
    Optional<Exam> findByIdAndIsReadyTrue(@Param("examId") Long examId);
}
