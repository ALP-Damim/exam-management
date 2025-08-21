-- 1) exams
CREATE TABLE IF NOT EXISTS exams (
                                     exam_id     BIGINT PRIMARY KEY,
                                     session_id  BIGINT,
                                     name        TEXT NOT NULL,
                                     difficulty  TEXT,
                                     is_ready    BOOLEAN NOT NULL DEFAULT FALSE,
                                     created_by  BIGINT,
                                     created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
    );

-- 2) questions
CREATE TABLE IF NOT EXISTS questions (
                                         question_id BIGINT PRIMARY KEY,
                                         exam_id     BIGINT NOT NULL REFERENCES exams(exam_id) ON DELETE CASCADE,
    qtype       TEXT NOT NULL CHECK (qtype IN ('MCQ','SHORT')),
    body        TEXT NOT NULL,
    choices     JSONB,
    answer_key  TEXT,
    points      NUMERIC(6,2) NOT NULL DEFAULT 0 CHECK (points >= 0),
    position    INT NOT NULL,
    CONSTRAINT uq_questions_exam_pos UNIQUE (exam_id, position)
    );

-- 3) submissions (복합 PK)
CREATE TABLE IF NOT EXISTS submissions (
                                           exam_id      BIGINT NOT NULL,
                                           user_id      BIGINT NOT NULL,
                                           submitted_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    total_score  NUMERIC(8,2) NOT NULL DEFAULT 0 CHECK (total_score >= 0),
    feedback     TEXT,
    feedback_status TEXT NOT NULL DEFAULT 'PENDING',
    feedback_requested_at TIMESTAMPTZ,
    feedback_retry_count INT NOT NULL DEFAULT 0,
    PRIMARY KEY (exam_id, user_id),
    FOREIGN KEY (exam_id) REFERENCES exams(exam_id) ON DELETE CASCADE
    );

-- 4) submission_answers (복합 PK)
CREATE TABLE IF NOT EXISTS submission_answers (
                                                  question_id BIGINT NOT NULL,
                                                  exam_id     BIGINT NOT NULL,
                                                  user_id     BIGINT NOT NULL,
                                                  answer_text TEXT,
                                                  solving_time INT,
                                                  is_correct  BOOLEAN NOT NULL DEFAULT FALSE,
                                                  score       NUMERIC(6,2) NOT NULL DEFAULT 0 CHECK (score >= 0),
    PRIMARY KEY (exam_id, user_id, question_id),
    FOREIGN KEY (exam_id, user_id) REFERENCES submissions(exam_id, user_id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE
    );

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_questions_exam_order   ON questions (exam_id, position);
CREATE INDEX IF NOT EXISTS idx_submissions_exam       ON submissions (exam_id);
CREATE INDEX IF NOT EXISTS idx_submissions_user       ON submissions (user_id);
CREATE INDEX IF NOT EXISTS idx_subm_ans_submission    ON submission_answers (exam_id, user_id);
CREATE INDEX IF NOT EXISTS idx_subm_ans_question      ON submission_answers (question_id);
