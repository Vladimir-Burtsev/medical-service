CREATE TABLE departments
(
    id              SERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL,
    FOREIGN KEY (organization_id) REFERENCES organizations (id)
);

CREATE TABLE diseases
(
    id         SERIAL PRIMARY KEY,
    identifier VARCHAR(255) NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL
);

CREATE TABLE medical_service
(
    id         SERIAL PRIMARY KEY,
    identifier VARCHAR(255) NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL
);

CREATE TABLE medical_services_dep
(
    id                 SERIAL PRIMARY KEY,
    status             VARCHAR(45) NOT NULL,
    department_id      BIGINT      NOT NULL,
    medical_service_id BIGINT      NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments (id),
    FOREIGN KEY (medical_service_id) REFERENCES medical_service (id)
);

CREATE TABLE diseases_dep
(
    id            SERIAL PRIMARY KEY,
    status        VARCHAR(45) NOT NULL,
    department_id BIGINT      NOT NULL,
    disease_id    BIGINT      NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments (id),
    FOREIGN KEY (disease_id) REFERENCES diseases (id)
);

CREATE TABLE doctors
(
    id            SERIAL PRIMARY KEY,
    person_id     BIGINT NOT NULL,
    user_id       BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE appeals
(
    id             SERIAL PRIMARY KEY,
    order_id       BIGINT,
    status         VARCHAR(45)  NOT NULL,
    insurance_type VARCHAR(255) NOT NULL,
    open_date      DATE         NOT NULL,
    closed_date    DATE,
    patient_id     BIGINT       NOT NULL,
    disease_dep_id BIGINT       NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (disease_dep_id) REFERENCES diseases_dep (id)
);

CREATE TABLE talons
(
    id         SERIAL PRIMARY KEY,
    time       TIMESTAMP NOT NULL,
    patient_id BIGINT    NOT NULL,
    doctor_id  BIGINT    NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);

CREATE TABLE visits
(
    id         SERIAL PRIMARY KEY,
    time       TIMESTAMP NOT NULL,
    patient_id BIGINT    NOT NULL,
    doctor_id  BIGINT    NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);

CREATE TABLE x_ray
(
    id         SERIAL PRIMARY KEY,
    time       TIMESTAMP NOT NULL,
    patient_id BIGINT    NOT NULL,
    doctor_id  BIGINT    NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);
