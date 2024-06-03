insert into organizations(id)
values (1),
       (2);

insert into departments(id, organization_id)
values (1, 1),
       (2, 2);

insert into doctors(id, person_id, user_id, position_id, department_id)
values (1, 1, 'cf29361a-c9ed-4644-a6dc-db639774850e', 1, 1),
       (2, 5, 'cf29361a-c9ed-4644-a6dc-db639778888e', 4, 2);

insert into diseases (id, identifier, name)
values (1, 'A1', 'Asc_disease1'),
       (2, 'A1.2', 'Asc_disease2'),
       (3, 'A1.3', 'Asc_disease3'),
       (4, 'A1.4', 'Asc_disease4'),
       (5, 'A1.5', 'Asc_disease5'),
       (6, 'A1.5.1', 'Asc_disease5.1'),
       (7, 'A1.5.2', 'Asc_disease5.2'),
       (8, 'A1.5.3', 'Asc_disease5.3'),
       (9, 'A1.5.4', 'Asc_disease5.4'),
       (10, 'A1.5.5', 'Asc_disease5.5'),
       (11, 'B1', 'Bsc_disease1'),
       (12, 'B2', 'Bsc_disease2'),
       (13, 'B3', 'Bsc_disease3'),
       (14, 'B4', 'Bsc_disease4'),
       (15, 'B5', 'Bsc_disease5'),
       (16, 'B6', 'Bsc_disease6'),
       (17, 'B7', 'Bsc_disease7');

insert into diseases_dep (id, status, department_id, disease_id)
values (1, 'OPEN', 1, 1),
       (2, 'OPEN', 1, 2),
       (3, 'OPEN', 1, 3),
       (4, 'OPEN', 1, 4),
       (5, 'OPEN', 1, 5),
       (6, 'OPEN', 1, 6),
       (7, 'OPEN', 1, 7),
       (8, 'OPEN', 1, 8),
       (9, 'OPEN', 1, 9),
       (10, 'OPEN', 1, 10),
       (11, 'OPEN', 1, 11),
       (12, 'OPEN', 1, 12),
       (13, 'OPEN', 1, 13),
       (14, 'OPEN', 1, 14),
       (15, 'OPEN', 2, 15),
       (16, 'OPEN', 1, 16),
       (17, 'BLOCKED', 1, 17);