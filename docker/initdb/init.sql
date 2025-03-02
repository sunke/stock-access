CREATE TABLE stock (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   current_price NUMERIC(15,2) NOT NULL,
   last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO stock (name, current_price, last_update)
VALUES
    ('Apple Inc.', 175.34, '2024-03-15 09:30:00'),
    ('Microsoft Corp.', 315.22, '2024-03-15 10:15:00'),
    ('Amazon Prime', 148.90, '2024-03-14 14:45:00'),
    ('Tesla Motors', 845.67, '2024-03-14 16:20:00'),
    ('Meta Platforms', 485.50, '2024-03-13 11:10:00'),
    ('Netflix Stream', 550.40, '2024-03-13 12:30:00'),
    ('NVIDIA Chips', 895.00, '2024-03-12 15:00:00'),
    ('Google Search', 135.75, '2024-03-12 13:45:00'),
    ('Intel Inside', 42.99, '2024-03-11 10:05:00'),
    ('Adobe Cloud', 625.80, '2024-03-11 09:15:00');