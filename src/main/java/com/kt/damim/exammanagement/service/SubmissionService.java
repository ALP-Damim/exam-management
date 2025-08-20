package com.kt.damim.exammanagement.service;

import com.kt.damim.exammanagement.dto.SubmitAnswerRequest;
import com.kt.damim.exammanagement.dto.SubmitAnswerResponse;


public interface SubmissionService {
    SubmitAnswerResponse submitAnswer(Long examId, SubmitAnswerRequest req);
}
