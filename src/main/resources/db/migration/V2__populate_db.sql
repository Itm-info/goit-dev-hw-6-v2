INSERT INTO worker (name, birthday, level, salary) VALUES
    ('Gregor', '2000-01-01', 'Trainee', 100000),
    ('Max', '1988-11-30', 'Junior', 100),
    ('Gregor', '2000-01-01', 'Middle', 100000),
    ('Max', '1988-11-30', 'Senior', 100),
    ('Gregor', '2000-01-01', 'Trainee', 100000),
    ('Max', '1988-11-30', 'Trainee', 100),
    ('Gregor', '2000-01-01', 'Trainee', 100000),
    ('Max', '1988-11-30', 'Trainee', 100),
    ('Gregor', '2000-01-01', 'Trainee', 100000),
    ('Max', '1988-11-30', 'Trainee', 100)
;

INSERT INTO client (name) VALUES
    'Gregor','Gregor','Gregor','Gregor','Gregor'
;

INSERT INTO project (client_id, start_date, finish_date) VALUES
    ((SELECT id FROM client LIMIT 1),'2000-01-01','2000-03-01'),
    ((SELECT id FROM client LIMIT 1),'2000-01-01','2000-03-01'),
    ((SELECT id FROM client LIMIT 1),'2000-01-01','2000-03-01'),
    ((SELECT id FROM client LIMIT 1),'2000-01-01','2000-03-01'),
    ((SELECT id FROM client LIMIT 1 OFFSET 1),'2000-01-01','2000-03-01'),
    ((SELECT id FROM client LIMIT 1 OFFSET 1),'2000-01-01','2000-03-01'),
    ((SELECT id FROM client LIMIT 1 OFFSET 1),'2000-01-01','2000-03-01'),
    ((SELECT id FROM client LIMIT 1 OFFSET 1),'2000-01-01','2000-03-01'),
    ((SELECT id FROM client LIMIT 1 OFFSET 2),'2000-01-01','2000-05-01'),
    ((SELECT id FROM client LIMIT 1 OFFSET 2),'2000-01-01','2000-05-01')
;

INSERT INTO project_worker (project_id, worker_id)
    SELECT project.id, worker.id FROM project JOIN worker ON
    worker.id = (
        SELECT worker.id FROM worker
        WHERE worker.birthday < project.start_date
        ORDER BY worker.id LIMIT 1
    )
;