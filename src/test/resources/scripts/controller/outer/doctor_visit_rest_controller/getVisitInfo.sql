-- Добавляем запись в таблицу organizations
INSERT INTO organizations (id) VALUES (1);

-- Вставка данных для департамента
INSERT INTO departments (id, organization_id) VALUES (1, 1);

-- Вставка данных для доктора
INSERT INTO doctors (id, person_id, position_id, user_id, department_id)
VALUES (1, 1001, 2001, '123e4567-e89b-12d3-a456-426614174000', 1);

-- Вставка данных для пациента
INSERT INTO patients(id, person_id, user_id, organization_id)
values (1, 1, 'cf29361a-c9ed-4644-a6dc-db639774850e', 1);

-- Вставка данных о списке заболеваний
INSERT INTO diseases (id, identifier, name)
values (1, '1', 'disease1');

-- Вставка данных о заболевании
INSERT INTO diseases_dep (id, status, department_id, disease_id)
values (1, 'OPEN', 1, 1);

-- Вставка данных для обращения
INSERT INTO appeals (id, order_id, status, insurance_type, open_date, closed_date, patient_id, disease_dep_id)
VALUES (1, null, 'OPEN', 'OMS', '2023-01-01', null, 1, 1);

-- Вставка данных для медицинской услуги
INSERT INTO medical_services (id, identifier, name)
VALUES (1, 'ServiceIdentifier', 'ServiceName');

-- Вставка данных для медицинской услуги департамента
INSERT INTO medical_services_dep (id, status, department_id, medical_service_id)
VALUES (1, 'OPEN', 1, 1);

-- Вставка данных для визита
INSERT INTO visits (id, visit_time, doctor_id, appeal_id)
VALUES (1, '2023-07-01T12:00:00', 1, 1);

-- Связь визита с медицинскими услугами
INSERT INTO visit_medical_services_dep (visit_id, medical_service_dep_id)
VALUES (1, 1);
