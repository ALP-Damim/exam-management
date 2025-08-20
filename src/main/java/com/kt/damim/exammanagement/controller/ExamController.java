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

    /**
     * Base URL: /api/exams
     * 문제 조회
     * GET /api/exams/{examId}/questions/{idx}
     * 설명: 특정 시험의 idx번째 문제 조회 (idx는 1부터 시작)
     * 
     * Path Params
     * examId(number, int64): 시험 ID
     * idx(number): 문제 순서(1-based)
     * Request Body: 없음
     * Response 200(JSON)
     * id(number): 문제 ID
     * position(number): 문제 순서
     * qtype(string): MCQ | SHORT
     * body(string): 문제 내용
     * choices(string[] | null): 객관식 선택지, 단답형은 null
     * points(number): 배점
     * 
     * Error
     * 400: { timestamp, status, error, message }
     * 500: { timestamp, status, error, message }
     * 
     * 예시 요청
     *   GET http://localhost:8080/api/exams/1/questions/2
     *  Accept: application/json
     * 
     * 예시 응답(200, MCQ)
     * {
        "id": 12,
        "position": 2,
        "qtype": "MCQ",
        "body": "Spring Boot에서 @RestController 어노테이션의 역할은?",
        "choices": ["A. 데이터베이스 연결", "B. REST API 엔드포인트 제공", "C. 보안 설정", "D. 로깅 설정"],
        "points": 10.0
        }
     * 핵심 요약: 단일 엔드포인트로 인덱스 기반 문제 단건 조회, 본문 없음, 200 시 QuestionDto 반환, 오류는 표준 에러 JSON. 
     */
    @GetMapping("/{examId}/questions/{idx}")
    public QuestionDto getQuestion(
        @PathVariable Long examId,
        @PathVariable int idx
    ) {
        return examService.getQuestion(examId, idx);
    }
}


/**
 * `ExamController`의 실제 엔드포인트를 확인해 정확한 API 명세를 제시하겠습니다. 컨트롤러, 서비스, DTO와 `build.gradle`을 한 번에 열어볼게요.
Read file: src/main/java/com/kt/damim/exammanagement/controller/ExamController.java
Read file: src/main/java/com/kt/damim/exammanagement/service/ExamService.java
Read file: src/main/java/com/kt/damim/exammanagement/service/impl/ExamServiceImpl.java
Read file: src/main/java/com/kt/damim/exammanagement/dto/SubmitAnswerRequest.java
Read file: src/main/java/com/kt/damim/exammanagement/dto/SubmitAnswerResponse.java
Read file: src/main/java/com/kt/damim/exammanagement/dto/QuestionDto.java
Read file: build.gradle
Read file: src/main/java/com/kt/damim/exammanagement/config/AppConfig.java

컨트롤러의 에러 응답 규격까지 정확히 적기 위해 예외 매핑을 확인하겠습니다.
Read file: src/main/java/com/kt/damim/exammanagement/exception/GlobalExceptionHandler.java

### 이 컨트롤러에 적을 API 명세(요약)

- **엔드포인트**: GET `/api/exams/{examId}/questions/{idx}`
- **설명**: 특정 시험의 `idx`번째(문항 순서) 문제를 조회합니다. `idx`는 1부터 시작합니다.
- **경로변수**
  - `examId`(Long): 시험 ID
  - `idx`(int): 문제의 위치(1-based)
- **요청 본문**: 없음
- **성공 응답 (200)**
  - 본문(JSON): `QuestionDto`
    - `id`(Long): 문제 ID
    - `position`(int): 문제 순서
    - `qtype`(string): 문제 유형, 예: `MCQ` 또는 `SHORT`
    - `body`(string): 문제 내용
    - `choices`(string[]) : 객관식(`MCQ`)일 때 선택지 배열, 단답(`SHORT`)은 `null` 가능
    - `points`(number): 배점
- **오류 응답**
  - 400 Bad Request: 유효하지 않은 요청(문제 없음 등)
    - 본문(JSON): `{ timestamp, status, error, message }`
  - 500 Internal Server Error: 서버 내부 오류
    - 본문(JSON): `{ timestamp, status, error, message }`

### 예시 cURL
```bash
curl -X GET "http://localhost:8080/api/exams/1/questions/2" -H "Accept: application/json"
```

### 성공 응답 예시(200, MCQ)
```json
{
  "id": 12,
  "position": 2,
  "qtype": "MCQ",
  "body": "Spring Boot에서 @RestController 어노테이션의 역할은?",
  "choices": ["A. 데이터베이스 연결", "B. REST API 엔드포인트 제공", "C. 보안 설정", "D. 로깅 설정"],
  "points": 10.0
}
```

### 오류 응답 예시(400)
```json
{
  "timestamp": "2025-08-20T12:34:56.789",
  "status": 400,
  "error": "Bad Request",
  "message": "시험 문제를 찾을 수 없습니다: examId=1, position=999"
}
```

### OpenAPI 스니펫(yaml)
```yaml
paths:
  /api/exams/{examId}/questions/{idx}:
    get:
      summary: 시험 문제 단건 조회
      description: 특정 시험의 idx번째 문제(1-based)를 조회합니다.
      parameters:
        - name: examId
          in: path
          required: true
          schema: { type: integer, format: int64 }
          description: 시험 ID
        - name: idx
          in: path
          required: true
          schema: { type: integer, minimum: 1 }
          description: 문제 순서(1부터 시작)
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                type: object
                properties:
                  id: { type: integer, format: int64 }
                  position: { type: integer }
                  qtype: { type: string, enum: [MCQ, SHORT] }
                  body: { type: string }
                  choices:
                    type: array
                    items: { type: string }
                    nullable: true
                  points: { type: number, format: double }
        '400':
          description: Bad Request
          content:
            application/json:
              schema:
                type: object
                properties:
                  timestamp: { type: string, format: date-time }
                  status: { type: integer }
                  error: { type: string }
                  message: { type: string }
        '500':
          description: Internal Server Error
```

원하시면 Swagger UI를 쓰도록 의존성 추가와 컨트롤러에 어노테이션(`@Operation`, `@ApiResponse`)도 바로 넣어드릴게요.
 */