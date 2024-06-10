insert into organizations(id)
values (1001),
       (1002)
;

insert into departments(id, organization_id)
values (301, 1001),
       (302, 1002)

;

insert into doctors(id, person_id, user_id, position_id, department_id)
values (501, 100, 'cf29361a-c9ed-4644-a6dc-db639774850e', 401, 301),
       (502, 100, 'cf29361a-c9ed-4644-a6dc-db639774850e', 402, 302)
;