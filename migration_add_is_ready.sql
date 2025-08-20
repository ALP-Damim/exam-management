-- 기존 exams 테이블에 is_ready 컬럼 추가
ALTER TABLE exams ADD COLUMN IF NOT EXISTS is_ready BOOLEAN NOT NULL DEFAULT FALSE;

-- 기존 시험들을 모두 준비 완료 상태로 설정 (필요에 따라 조정)
-- UPDATE exams SET is_ready = true WHERE is_ready IS NULL;
