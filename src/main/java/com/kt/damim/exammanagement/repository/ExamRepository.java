package com.kt.damim.exammanagement.repository;

import com.kt.damim.exammanagement.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExamRepository extends JpaRepository<Exam, Long> {}
