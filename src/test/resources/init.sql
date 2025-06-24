DELETE FROM sessions;
DELETE FROM users;

INSERT INTO users (id, first_name, last_name, middle_name, email, deleted)
VALUES
    (1, 'John', 'Smith', 'Michael', 'john.smith@example.com', false),
    (2, 'Emily', 'Johnson', 'Anne', 'emily.johnson@example.com', false),
    (3, 'Michael', 'Williams', 'James', 'michael.williams@example.com', true);

INSERT INTO sessions (device_type, ended_at_utc, started_at_utc, user_id)
VALUES
    (1, '2023-06-15 14:30:00', '2023-06-15 12:00:00', 1),
    (2, '2023-06-15 15:45:00', '2023-06-15 14:35:00', 2),
    (1, NULL, '2023-06-17 15:00:00', 3),
    (2, NULL, '2023-06-18 12:00:00', 1),
    (1, '2023-06-21 10:45:00', '2023-06-21 09:30:00', 2),
    (2, '2023-06-23 18:45:00', '2023-06-23 17:30:00', 3);