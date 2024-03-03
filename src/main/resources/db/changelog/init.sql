create sequence if not exists hibernate_sequence start with 1 increment by 1;

create table organizations
(
    id bigint not null,

    constraint pk_organizations primary key (id)
);

create table patients
(
    id bigint not null,
    person_id bigint not null,
    user_id uuid,
    organization_id bigint not null,

    constraint pk_patients primary key (id),
    constraint fk_patients_organization_id_on_organizations foreign key (organization_id) references organizations (id)
);