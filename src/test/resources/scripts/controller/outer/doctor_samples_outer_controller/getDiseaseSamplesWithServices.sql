INSERT INTO organizations (id)
VALUES (1);


INSERT INTO diseases (id, identifier, name)
VALUES (1, 'identifier_1', 'Disease Name 1'),
       (2, 'identifier_2', 'Disease Name 2');

INSERT INTO departments (id, organization_id)
VALUES (1, 1);


INSERT INTO diseases_dep (id, status, department_id, disease_id)
VALUES (1, 'OPEN', 1, 1),
       (2, 'OPEN', 1, 2);

INSERT INTO doctors (id, person_id, position_id, user_id, department_id)
VALUES (1, 1, 101, 'cf29361a-c9ed-4644-a6dc-db639774850e', 1);

INSERT INTO disease_samples (id, name, description, doctor_id, disease_dep_id)
VALUES (1, 'Sample 1', 'Description for Sample 1', 1, 1),
       (2, 'Sample 2', 'Description for Sample 2', 1, 1);

INSERT INTO medical_services (id, identifier, name)
VALUES (1, 'service_identifier_1', 'Service Name 1'),
       (2, 'service_identifier_2', 'Service Name 2');

INSERT INTO medical_services_dep (id, status, department_id, medical_service_id)
VALUES (1, 'OPEN', 1, 1),
       (2, 'OPEN', 1, 2);

INSERT INTO disease_samples_medical_services_dep (disease_sample_id, medical_service_dep_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2);