insert into organizations(id)
values (1001),
       (1002)
;

insert into departments(id, organization_id)
values (301, 1001),
       (302, 1001),
       (401, 1002)
;

insert into doctors(id, person_id, user_id, position_id, department_id)
values (501, 100, 'cf29361a-c9ed-4644-a6dc-db639774850e', 601, 301),
       (502, 100, 'cf29361a-c9ed-4644-a6dc-db639774850e', 602, 302),
       (503, 100, 'cf29361a-c9ed-4644-a6dc-db639774850e', 603, 401),
       (504, 101, '5708767e-f51f-4dac-a4c9-828446473aa4', 604, 301)
;