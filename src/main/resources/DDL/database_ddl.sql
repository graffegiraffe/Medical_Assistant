
create table medical_assistant.appointments (
                                                completed boolean not null,
                                                created_at timestamp(6) not null,
                                                date_time timestamp(6) not null,
                                                doctor_id bigint not null,
                                                id bigserial not null,
                                                updated_at timestamp(6) not null,
                                                user_id bigint not null,
                                                license_number varchar(255) not null,
                                                notes varchar(255),
                                                primary key (id)
);

create table medical_assistant.clinics (
                                           created_at timestamp(6) not null,
                                           id bigserial not null,
                                           updated_at timestamp(6) not null,
                                           address varchar(255) not null,
                                           name varchar(255) not null,
                                           phone varchar(255),
                                           primary key (id)
);

create table medical_assistant.doctors (
                                           is_active boolean not null,
                                           created_at timestamp(6) not null,
                                           id bigserial not null,
                                           updated_at timestamp(6) not null,
                                           username varchar(50) not null unique,
                                           full_name varchar(150) not null,
                                           clinic_name varchar(255) not null,
                                           license_number varchar(255) not null unique,
                                           specialty varchar(255) not null,
                                           primary key (id)
);

create table medical_assistant.health_metrics (
                                                  blood_sugar float(53),
                                                  temperature float(53),
                                                  created_at timestamp(6),
                                                  id bigserial not null,
                                                  timestamp timestamp(6) not null,
                                                  updated_at timestamp(6),
                                                  user_id bigint not null,
                                                  blood_pressure varchar(255),
                                                  notes varchar(255),
                                                  primary key (id)
);

create table medical_assistant.medical_records (
                                                   date date,
                                                   created_at timestamp(6) not null,
                                                   doctor_id bigint not null,
                                                   id bigserial not null,
                                                   updated_at timestamp(6) not null,
                                                   user_id bigint not null,
                                                   description varchar(255),
                                                   doctor_notes varchar(255),
                                                   title varchar(255) not null,
                                                   type varchar(255) not null check (type in ('ALLERGY','VACCINE','ILLNESS')),
                                                   primary key (id)
);

create table medical_assistant.medications (
                                               active boolean not null,
                                               created_at timestamp(6),
                                               end_date timestamp(6),
                                               id bigserial not null,
                                               start_date timestamp(6),
                                               updated_at timestamp(6),
                                               user_id bigint not null,
                                               dosage varchar(50),
                                               name varchar(100) not null,
                                               primary key (id)
);

create table medical_assistant.security (
                                            created timestamp(6) not null,
                                            id bigserial not null,
                                            updated timestamp(6),
                                            user_id bigint not null,
                                            login varchar(255) not null,
                                            password varchar(255) not null,
                                            role varchar(255) not null,
                                            primary key (id)
);

create table medical_assistant.users (
                                         birth_date date,
                                         created_at timestamp(6) not null,
                                         doctor_id bigint,
                                         id bigserial not null,
                                         updated_at timestamp(6) not null,
                                         blood_type varchar(255),
                                         email varchar(255) not null unique,
                                         username varchar(255) not null unique,
                                         primary key (id)
);

