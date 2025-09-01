INSERT INTO role (id, idx, name, permissions, created_at, updated_at)
VALUES
('ROX7p2Q9aB3mN5tR8sC1dL6kV4wY', 1, 'CAPTAIN', '{"canCreate": true, "canEdit": true, "canDelete": true, "canView": true}'::jsonb, NOW(), NOW()),
('RO3nT8wQ5zL1xC7vB2mR9pK4sD6a', 2, 'EDITOR' , '{"canCreate": false, "canEdit": true, "canDelete": false, "canView": true}'::jsonb, NOW(), NOW()),
('ROu5Jk2Pq9Lr4Tn6Vm8Yc1Xb7ZDd', 3, 'VIEWER' , '{"canCreate": false, "canEdit": false, "canDelete": false, "canView": true}'::jsonb, NOW(), NOW()),
('ROQ1wE2rT3yU4iO5pA6sD7fGJ9kL', 4, 'GHOST'  , '{"canCreate": false, "canEdit": false, "canDelete": false, "canView": true}'::jsonb, NOW(), NOW())
ON CONFLICT (name) DO NOTHING;