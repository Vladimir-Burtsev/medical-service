CREATE TABLE departments
(
    id              SERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL,

    CONSTRAINT fk_departments_organization_id_on_organizations
        FOREIGN KEY (organization_id) REFERENCES organizations (id)
);

CREATE TABLE diseases
(
    id         SERIAL PRIMARY KEY,
    identifier VARCHAR(255) NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL
);

CREATE TABLE medical_services
(
    id         SERIAL PRIMARY KEY,
    identifier VARCHAR(255) NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL
);

CREATE TABLE medical_services_dep
(
    id                 SERIAL PRIMARY KEY,
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
    id            SERIAL PRIMARY KEY,
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
    id            SERIAL PRIMARY KEY,
    person_id     BIGINT NOT NULL,
    user_id       uuid   NOT NULL,
    department_id BIGINT NOT NULL,

    CONSTRAINT fk_doctors_department_id_on_departments
        FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE appeals
(
    id             SERIAL PRIMARY KEY,
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
    id         SERIAL PRIMARY KEY,
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
    id         SERIAL PRIMARY KEY,
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
    id          SERIAL PRIMARY KEY,
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
