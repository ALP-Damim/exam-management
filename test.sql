-- 필수: 시험과 문항
TRUNCATE TABLE public.submission_answers,
    public.submissions,
    public.questions,
    public.exams
RESTART IDENTITY
CASCADE;

-- 테스트용 시험 데이터 생성
-- isReady가 true인 시험 (시험 ID: 1)
INSERT INTO exams (exam_id, session_id, name, difficulty, is_ready, created_by, created_at) 
VALUES (1, 101, '2024년 중간고사', '중', true, 1, NOW());

-- isReady가 false인 시험 (시험 ID: 2)
INSERT INTO exams (exam_id, session_id, name, difficulty, is_ready, created_by, created_at) 
VALUES (2, 101, '2024년 기말고사', '상', false, 1, NOW());

-- 존재하지 않는 시험 ID: 999 (테스트용)

-- 시험 1번에 문제 추가 (준비된 시험)
INSERT INTO questions (question_id, exam_id, qtype, body, choices, answer_key, points, position) 
VALUES (1, 1, 'MCQ', '다음 중 Java의 특징이 아닌 것은?', '["A. 객체지향", "B. 플랫폼 독립적", "C. 컴파일 언어", "D. 인터프리터 언어"]', 'D', 10.0, 1);

INSERT INTO questions (question_id, exam_id, qtype, body, choices, answer_key, points, position) 
VALUES (2, 1, 'MCQ', 'Spring Boot에서 @RestController 어노테이션의 역할은?', '["A. 데이터베이스 연결", "B. REST API 엔드포인트 제공", "C. 보안 설정", "D. 로깅 설정"]', 'B', 10.0, 2);

INSERT INTO questions (question_id, exam_id, qtype, body, choices, answer_key, points, position) 
VALUES (3, 1, 'SHORT', 'JPA에서 엔티티 간의 관계를 표현하는 어노테이션 3가지를 작성하세요.', NULL, '@OneToOne, @OneToMany, @ManyToOne, @ManyToMany', 15.0, 3);

-- 시험 2번에는 문제가 없음 (준비되지 않은 시험)

-- 시험 제출 기록 (progress 기능 제거됨 - 기본 데이터만 유지)
INSERT INTO public.submissions (exam_id, user_id, total_score)
VALUES (1, 101, 0)
ON CONFLICT (exam_id, user_id) DO NOTHING;

-- 테스트 결과 확인 쿼리
-- 1. 모든 시험 조회
SELECT exam_id, name, is_ready, created_at FROM exams ORDER BY exam_id;

-- 2. 준비된 시험만 조회
SELECT exam_id, name, is_ready FROM exams WHERE is_ready = true;

-- 3. 준비되지 않은 시험만 조회
SELECT exam_id, name, is_ready FROM exams WHERE is_ready = false;

-- 4. 시험별 문제 수 조회
SELECT e.exam_id, e.name, e.is_ready, COUNT(q.question_id) as question_count
FROM exams e
LEFT JOIN questions q ON e.exam_id = q.exam_id
GROUP BY e.exam_id, e.name, e.is_ready
ORDER BY e.exam_id;

-- 5. 특정 시험의 준비 상태 확인
SELECT exam_id, name, is_ready FROM exams WHERE exam_id = 1;
SELECT exam_id, name, is_ready FROM exams WHERE exam_id = 2;
SELECT exam_id, name, is_ready FROM exams WHERE exam_id = 999;

-- 테스트 데이터 정리 (필요시)
-- DELETE FROM questions WHERE exam_id IN (1, 2);
-- DELETE FROM exams WHERE exam_id IN (1, 2);
