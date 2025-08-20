package com.kt.damim.exammanagement.dto;

import jakarta.validation.constraints.NotNull;


public record SubmitAnswerRequest(
    @NotNull Long questionId,
    String answerText
) {}
