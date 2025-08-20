-- 필수: 시험과 문항
TRUNCATE TABLE public.submission_answers,
    public.submissions,
    public.questions,
    public.exams
RESTART IDENTITY
CASCADE;

INSERT INTO public.exams (exam_id, class_id, name, difficulty, created_by)
VALUES (1, 1, '2024년 중간고사', '중', 1)
ON CONFLICT (exam_id) DO NOTHING;

INSERT INTO public.questions (question_id, exam_id, qtype, body, choices, answer_key, points, position)
VALUES
    (1001, 1, 'SHORT', '2+2는?', NULL, '4', 5.00, 1),
    (1002, 1, 'MCQ',   '대한민국 수도는?', '["서울","부산","대구","인천"]'::jsonb, '서울', 5.00, 2)
ON CONFLICT (question_id) DO NOTHING;

-- 시험 제출 기록 (progress 기능 제거됨 - 기본 데이터만 유지)
INSERT INTO public.submissions (exam_id, user_id, total_score)
VALUES (1, 101, 0)
ON CONFLICT (exam_id, user_id) DO NOTHING;
