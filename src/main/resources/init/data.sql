INSERT INTO role (id, idx, name, permissions, created_at, updated_at)
VALUES
('ROX7p2Q9aB3mN5tR8sC1dL6kV4wY', 1, 'CAPTAIN', '{"canCreate": true, "canEdit": true, "canDelete": true, "canView": true}'::jsonb, NOW(), NOW()),
('RO3nT8wQ5zL1xC7vB2mR9pK4sD6a', 2, 'EDITOR' , '{"canCreate": false, "canEdit": true, "canDelete": false, "canView": true}'::jsonb, NOW(), NOW()),
('ROu5Jk2Pq9Lr4Tn6Vm8Yc1Xb7ZDd', 3, 'VIEWER' , '{"canCreate": false, "canEdit": false, "canDelete": false, "canView": true}'::jsonb, NOW(), NOW()),
('ROQ1wE2rT3yU4iO5pA6sD7fGJ9kL', 4, 'GHOST'  , '{"canCreate": false, "canEdit": false, "canDelete": false, "canView": true}'::jsonb, NOW(), NOW())
ON CONFLICT (name) DO NOTHING;

INSERT INTO MEMBER (id, idx, name, password, email, phone, status, created_at, updated_at)
    SELECT * FROM (VALUES
       ('ROQ1wE2rT3yU4iO5pA6sD7fGJ9k1', 1, '나규태', '$2a$10$lOdf9mRlytPuxvDvGapCNOOsF9N77CDa6LvPL6A5RMNt5Hbf7HeAy', 'kyoutae_93@naver.com', '01091092682', 'ACTIVE', NOW(), NOW()),
       ('ROQ1wE2rT3yU4iO5pA6sD7fGJ9k2', 2, '최보영', '$2a$10$lOdf9mRlytPuxvDvGapCNOOsF9N77CDa6LvPL6A5RMNt5Hbf7HeAy', 'boyoung.np@kakao.com', '01085511423', 'ACTIVE', NOW(), NOW())
    ) AS new_data(id, idx, name, password, email, phone, status, created_at, updated_at)
WHERE NOT EXISTS (
    SELECT 1 FROM MEMBER
    WHERE name = new_data.name
);