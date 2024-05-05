insert into test.organizations(id)
values (1),
       (2)
;

insert into test.departments(id, organization_id)
values (1, 2)
;

insert into test.patients(id, person_id, user_id, organization_id)
values (1, 1, 'cf29361a-c9ed-4644-a6dc-db639774850e', 1),
       (2, 2, '3c0f6e56-69f3-459e-9fe8-5069d4537951', 2)
;

insert into test.doctors(id, person_id, user_id, position_id, department_id)
values (12, 45, 'cf29361a-c9ed-4644-a6dc-db639775760e', 1, 1)
;

insert into test.talons(id, time, patient_id, doctor_id)
values (1, '2024-04-25 10:00:00.000000', 1, 12),
       (2, '2024-04-27 11:00:00.000000', 2, 12),
       (3, '2024-04-28 12:00:00.000000', null, 12)
;



