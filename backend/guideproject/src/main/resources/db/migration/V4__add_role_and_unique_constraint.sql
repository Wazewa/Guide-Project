ALTER TABLE Person
    ADD COLUMN IF NOT EXISTS Role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER';

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM information_schema.table_constraints
            WHERE table_name = 'review'
              AND constraint_name = 'unique_review_per_user_landmark'
        ) THEN
            ALTER TABLE Review
                ADD CONSTRAINT unique_review_per_user_landmark
                    UNIQUE (person_id, landmark_id);
        END IF;
    END $$;