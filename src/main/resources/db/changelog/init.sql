create sequence if not exists hibernate_sequence start with 1 increment by 1;

CREATE TABLE organizations
(
    id BIGINT PRIMARY KEY
);

CREATE TABLE departments
(
    id              BIGINT PRIMARY KEY,
    organization_id BIGINT NOT NULL,

    CONSTRAINT fk_departments_organization_id_on_organizations
        FOREIGN KEY (organization_id) REFERENCES organizations (id)
);

CREATE TABLE diseases
(
    id         BIGSERIAL PRIMARY KEY,
    identifier VARCHAR(50) NOT NULL UNIQUE,
    name       VARCHAR(50) NOT NULL
);

CREATE TABLE medical_services
(
    id         BIGSERIAL PRIMARY KEY,
    identifier VARCHAR(50) NOT NULL UNIQUE,
    name       VARCHAR(50) NOT NULL
);

CREATE TABLE medical_services_dep
(
    id                 BIGSERIAL PRIMARY KEY,
    status             VARCHAR(10) NOT NULL,
    department_id      BIGINT      NOT NULL,
    medical_service_id BIGINT      NOT NULL,

    CONSTRAINT fk_medical_services_dep_department_id_on_departments
        FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_medical_services_dep_medical_service_id_on_medical_service
        FOREIGN KEY (medical_service_id) REFERENCES medical_services (id)
);

CREATE TABLE diseases_dep
(
    id            BIGSERIAL PRIMARY KEY,
    status        VARCHAR(10) NOT NULL,
    department_id BIGINT      NOT NULL,
    disease_id    BIGINT      NOT NULL,

    CONSTRAINT fk_diseases_dep_department_id_on_departments
        FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_diseases_dep_disease_id_on_diseases
        FOREIGN KEY (disease_id) REFERENCES diseases (id)
);

CREATE TABLE doctors
(
    id            BIGSERIAL PRIMARY KEY,
    person_id     BIGINT NOT NULL,
    user_id       uuid   NOT NULL,
    position_id   BIGINT NOT NULL,
    department_id BIGINT NOT NULL,

    CONSTRAINT fk_doctors_department_id_on_departments
        FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE patients
(
    id BIGSERIAL PRIMARY KEY,
    person_id BIGINT NOT NULL,
    user_id uuid,
    organization_id BIGINT NOT NULL,

    CONSTRAINT fk_patients_organization_id_on_organizations
        FOREIGN KEY (organization_id) REFERENCES organizations (id)
);

CREATE TABLE registrars
(
    id            BIGSERIAL PRIMARY KEY,
    person_id     BIGINT NOT NULL,
    user_id       uuid   NOT NULL,
    position_id   BIGINT NOT NULL,
    department_id BIGINT NOT NULL,

    CONSTRAINT fk_doctors_department_id_on_departments
        FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE appeals
(
    id             BIGSERIAL PRIMARY KEY,
    order_id       BIGINT,
    status         VARCHAR(10)  NOT NULL,
    insurance_type VARCHAR(10) NOT NULL,
    open_date      DATE         NOT NULL,
    closed_date    DATE,
    patient_id     BIGINT       NOT NULL,
    disease_dep_id BIGINT       NOT NULL,

    CONSTRAINT fk_appeals_patient_id_on_patients
        FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_appeals_disease_dep_id_on_diseases
        FOREIGN KEY (disease_dep_id) REFERENCES diseases_dep (id)
);

CREATE TABLE talons
(
    id         BIGSERIAL PRIMARY KEY,
    time       TIMESTAMP NOT NULL,
    patient_id BIGINT,
    doctor_id  BIGINT    NOT NULL,

    CONSTRAINT fk_talons_patient_id_on_patients
        FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_talons_doctor_id_on_doctors
        FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);

CREATE TABLE visits
(
    id         BIGSERIAL PRIMARY KEY,
    visit_time TIMESTAMP NOT NULL,
    doctor_id  BIGINT    NOT NULL,
    appeal_id  BIGINT    NOT NULL,

    CONSTRAINT fk_visits_doctor_id_on_doctors
        FOREIGN KEY (doctor_id) REFERENCES doctors (id),
    CONSTRAINT fk_visits_appeal_id_on_appeals
        FOREIGN KEY (appeal_id) REFERENCES appeals (id)
);

CREATE TABLE x_rays
(
    id          BIGSERIAL PRIMARY KEY,
    time        TIMESTAMP    NOT NULL,
    type        VARCHAR(10)  NOT NULL,
    document_id uuid         NOT NULL,
    size        BIGINT       NOT NULL,
    hash        VARCHAR(100) NOT NULL,
    appeal_id   BIGINT       NOT NULL,

    CONSTRAINT fk_x_rays_appeal_id_on_appeals
        FOREIGN KEY (appeal_id) REFERENCES appeals (id)
);

CREATE TABLE visit_medical_services_dep
(
    visit_id               BIGINT NOT NULL,
    medical_service_dep_id BIGINT NOT NULL,

    CONSTRAINT fk_visit_medical_services_dep_visit_id_on_visits
        FOREIGN KEY (visit_id) REFERENCES visits (id),
    CONSTRAINT fk_visit_medical_services_dep_ms_dep_id_on_ms_dep
        FOREIGN KEY (medical_service_dep_id) REFERENCES medical_services_dep (id)
);

CREATE TABLE disease_samples
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(50) NOT NULL UNIQUE,
    description    VARCHAR(100),
    doctor_id      BIGINT      NOT NULL,
    disease_dep_id BIGINT      NOT NULL,

    CONSTRAINT fk_disease_samples_doctor_id_on_doctors
        FOREIGN KEY (doctor_id) REFERENCES doctors (id),
    CONSTRAINT fk_disease_samples_disease_dep_id_on_diseases_dep
        FOREIGN KEY (disease_dep_id) REFERENCES diseases_dep (id)
);

CREATE TABLE disease_samples_medical_services_dep
(
    disease_sample_id      BIGINT NOT NULL,
    medical_service_dep_id BIGINT NOT NULL,

    CONSTRAINT fk_dsmsd_disease_sample_id_on_disease_samples
        FOREIGN KEY (disease_sample_id) REFERENCES disease_samples (id),
    CONSTRAINT fk_dsmsd_medical_service_dep_id_on_medical_services_dep
        FOREIGN KEY (medical_service_dep_id) REFERENCES medical_services_dep (id)
);