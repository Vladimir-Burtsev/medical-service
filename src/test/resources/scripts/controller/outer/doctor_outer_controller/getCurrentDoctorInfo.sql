insert into organizations (id)
values  (1),
        (2),
        (3)
;

insert into departments(id, organization_id)
values (10, 1),
       (20, 2),
       (30, 3)
;

insert into doctors(id, person_id, user_id, position_id, department_id)
values (1001, 1111, '63fcae3f-ae3c-48e8-b073-b91a2af624b5', 100, 10),
       (2002, 2222, '0e8e16b9-a474-41f6-9d78-0fe41e6aa564', 200, 20),
       (3003, 3333, '0759545c-1d8c-4513-9f70-c8de0dc97c7f', 300, 30)
;



