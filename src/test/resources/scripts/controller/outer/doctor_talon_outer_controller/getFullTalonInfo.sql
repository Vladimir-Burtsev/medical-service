insert into test.organizations(id)
values (100);

insert into test.departments(id, organization_id)
values (200, 100),
       (201, 100);

insert into test.patients(id, person_id, user_id, organization_id)
values (300, 600, '83bb225a-ad24-474f-a6e8-64a26c7db63f', 100),
       (301, 601, '1ce6590e-9dcc-4a58-afea-b1fb58d9342b', 100);

insert into test.doctors(id, person_id, user_id, position_id, department_id)
values (400, 602, '99efc3cd-c5e6-4469-ab40-ff97d3d98882', 700, 200),
       (401, 603, 'cc70ff03-5637-46da-9dc4-91ccbd8c51c3', 701, 201);

insert into test.talons(id, time, patient_id, doctor_id)
values (500, '2024-06-26 13:00:00.000000', 300, 400),
       (501, '2024-06-26 14:00:00.000000', null, 400),
       (502, '2024-06-26 15:00:00.000000', null, 401);
