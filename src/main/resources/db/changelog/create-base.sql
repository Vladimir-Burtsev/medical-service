CREATE TABLE departments
(
    id              SERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL
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
    medical_service_id BIGINT      NOT NULL
);

CREATE TABLE diseases_dep
(
    id            SERIAL PRIMARY KEY,
    status        VARCHAR(45) NOT NULL,
    department_id BIGINT      NOT NULL,
    disease_id    BIGINT      NOT NULL
);

CREATE TABLE doctors
(
    id            SERIAL PRIMARY KEY,
    person_id     BIGINT NOT NULL,
    user_id       BIGINT NOT NULL,
    department_id BIGINT NOT NULL
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
    disease_dep_id BIGINT       NOT NULL
);

CREATE TABLE talons
(
    id         SERIAL PRIMARY KEY,
    time       TIMESTAMP NOT NULL,
    patient_id BIGINT    NOT NULL,
    doctor_id  BIGINT    NOT NULL
);

CREATE TABLE visits
(
    id         SERIAL PRIMARY KEY,
    time       TIMESTAMP NOT NULL,
    patient_id BIGINT    NOT NULL,
    doctor_id  BIGINT    NOT NULL
);

CREATE TABLE x_ray
(
    id         SERIAL PRIMARY KEY,
    time       TIMESTAMP NOT NULL,
    patient_id BIGINT    NOT NULL,
    doctor_id  BIGINT    NOT NULL
);
