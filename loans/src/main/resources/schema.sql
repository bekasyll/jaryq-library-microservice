CREATE TABLE loans (
    id BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL UNIQUE,
    member_id BIGINT NOT NULL,
    loan_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP NOT NULL,
    status VARCHAR(10) NOT NULL,
    created_at DATE NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_at DATE,
    updated_by VARCHAR(50)
);