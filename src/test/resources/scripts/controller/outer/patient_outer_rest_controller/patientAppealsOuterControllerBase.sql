insert into organizations(id)
values (1),
       (2);

insert into patients(id, person_id, user_id, organization_id)
values (1, 1, 'cf29361a-c9ed-4644-a6dc-db639774850e', 1),
       (2, 1, 'cf29361a-c9ed-4644-a6dc-db639774851e', 2);

insert into diseases (id, identifier, name)
values (1, '1', 'disease1'),
       (2, '2', 'disease2');

insert into departments (id, organization_id)
values (1, 1),
       (2, 2);

insert into diseases_dep (id, status, department_id, disease_id)
values (1, 'OPEN', 1, 1),
       (2, 'OPEN', 2, 2);

insert into appeals(id, order_id, status, insurance_type, open_date, closed_date, patient_id, disease_dep_id)
values (1, null, 'OPEN', 'DMS', '2020-01-01', null, 1, 1),
       (2, null, 'OPEN', 'DMS', '2020-01-01', null, 2, 1);


