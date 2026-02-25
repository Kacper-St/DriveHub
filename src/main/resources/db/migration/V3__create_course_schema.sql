CREATE TABLE courses (
                         id               UUID PRIMARY KEY,
                         course_status    VARCHAR(30)  NOT NULL,
                         license_category VARCHAR(10)  NOT NULL,
                         total_Minutes      INTEGER      NOT NULL,
                         remaining_Minutes  INTEGER      NOT NULL,
                         student_id       UUID         NOT NULL,
                         instructor_id    UUID         NOT NULL,
                         created_at       TIMESTAMPTZ  NOT NULL,
                         updated_at       TIMESTAMPTZ  NOT NULL,

                         CONSTRAINT fk_courses_student
                             FOREIGN KEY (student_id) REFERENCES users (id),

                         CONSTRAINT fk_courses_instructor
                             FOREIGN KEY (instructor_id) REFERENCES users (id),

                         CONSTRAINT chk_courses_total_hours CHECK (total_Minutes >= 1),
                         CONSTRAINT chk_courses_status CHECK (course_status IN ('ACTIVE', 'FINISHED', 'CANCELED'))
);

CREATE INDEX idx_courses_student_id ON courses(student_id);
CREATE INDEX idx_courses_instructor_id ON courses(instructor_id);