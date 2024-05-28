insert into organizations(id)
values (1),
       (2);

insert into departments (id, organization_id)
values (1, 1),
       (2, 2);

insert into diseases(id, identifier, name)
values(1, 'Covid-19', 'Covid-19'),
      (2, 'Covid-19-1', 'Covid-19');

insert into diseases_dep(id, status, department_id, disease_id)
values (1, 'OPEN', 1, 1),
       (2, 'OPEN', 2, 2);

insert into doctors(id, person_id, user_id,position_id, department_id)
values (1, 1, 'cf29361a-c9ed-4644-a6dc-db639774860e',1, 1),
       (2, 1, 'cf29361a-c9ed-4644-a6dc-db639774861e',2, 2);

insert into patients(id, person_id, user_id, organization_id)
values (1, 1, 'cf29361a-c9ed-4644-a6dc-db639774850e', 1),
       (2, 1, 'cf29361a-c9ed-4644-a6dc-db639774851e', 2);



