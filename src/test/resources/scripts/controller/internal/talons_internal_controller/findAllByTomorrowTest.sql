insert into test.organizations(id)
values (1);

insert into test.departments(id, organization_id)
values (1, 1);

insert into test.doctors(id, person_id, user_id, position_id, department_id)
values (1, 1, 'cf29361a-c9ed-4644-a6dc-db639774850e', 1, 1);

insert into test.patients(id, person_id, user_id, organization_id)
values (1, 1, '662b6f6e-4702-44c4-98f4-e73243087d46', 1),
       (2, 2, '599d9ef0-7ae0-4924-890b-55eb13f85e53', 1);

insert into test.talons(id, time, patient_id, doctor_id)
values (1, now() + interval '1 day', 1, 1),
       (2, now() + interval '1 day', null, 1),
       (3, now() + interval '1 day', null, 1);



