ALTER TABLE departments
    ADD CONSTRAINT fk_departments_organization_id_on_organizations
        FOREIGN KEY (organization_id) REFERENCES organizations (id);

ALTER TABLE medical_services_dep
    ADD CONSTRAINT fk_medical_services_dep_department_id_on_departments
        FOREIGN KEY (department_id) REFERENCES departments (id),
    ADD CONSTRAINT fk_medical_services_dep_medical_service_id_on_medical_service
        FOREIGN KEY (medical_service_id) REFERENCES medical_service (id);

ALTER TABLE diseases_dep
    ADD CONSTRAINT fk_diseases_dep_department_id_on_departments
        FOREIGN KEY (department_id) REFERENCES departments (id),
    ADD CONSTRAINT fk_diseases_dep_disease_id_on_diseases
        FOREIGN KEY (disease_id) REFERENCES diseases (id);

ALTER TABLE doctors
    ADD CONSTRAINT fk_doctors_department_id_on_departments
        FOREIGN KEY (department_id) REFERENCES departments (id);

ALTER TABLE appeals
    ADD CONSTRAINT fk_appeals_patient_id_on_patients
        FOREIGN KEY (patient_id) REFERENCES patients (id),
    ADD CONSTRAINT fk_appeals_disease_dep_id_on_diseases
        FOREIGN KEY (disease_dep_id) REFERENCES diseases_dep (id);

ALTER TABLE talons
    ADD CONSTRAINT fk_talons_patient_id_on_patients
        FOREIGN KEY (patient_id) REFERENCES patients (id),
    ADD CONSTRAINT fk_talons_doctor_id_on_doctors
        FOREIGN KEY (doctor_id) REFERENCES doctors (id);

ALTER TABLE visits
    ADD CONSTRAINT fk_visits_patient_id_on_patients
        FOREIGN KEY (patient_id) REFERENCES patients (id),
    ADD CONSTRAINT fk_visits_doctor_id_on_doctors
        FOREIGN KEY (doctor_id) REFERENCES doctors (id);

ALTER TABLE x_ray
    ADD CONSTRAINT fk_x_ray_patient_id_on_patients
        FOREIGN KEY (patient_id) REFERENCES patients (id),
    ADD CONSTRAINT fk_x_ray_doctor_id_on_doctors
        FOREIGN KEY (doctor_id) REFERENCES doctors (id);
