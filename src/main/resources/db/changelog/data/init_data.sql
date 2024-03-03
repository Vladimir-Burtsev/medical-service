insert into organizations(id)
values (1),
       (2)
;

insert into patients(id, person_id, user_id, organization_id)
values (1, 1, 'cf29361a-c9ed-4644-a6dc-db639774850e', 1),
       (2, 1, 'cf29361a-c9ed-4644-a6dc-db639774850e', 2)
;