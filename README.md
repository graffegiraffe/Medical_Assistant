# Medical Assistant Management Application

## Project Description

**Medical Assistant Management Application** is a web application that helps clinic administrators, medical professionals, and patients manage various aspects of healthcare activities, such as appointment booking, clinic management, medical documentation, prescriptions, and health tracking.

The application provides a robust and user-friendly interface for managing medical records, clinics can track activities, patients can receive care, and doctors can manage their records and patients.

---
## Contents

1. [Main Functions](#Main-Functions)
2. [Used Technologies](#Used-Technologies)
3. [REST API Documentation](#REST-API-Documentation)
- [Base URL](#Base-URL)
- [Authentication](#Authentication)
- [User Registration](#User-Registration)
- [Doctor Registration](#Doctor-Registration)
- [Users](#Users)
- [Doctors](#Doctors)
- [Clinics](#Clinics)
- [Medications](#Medications)
- [Medical Records](#Medical-Records)
- [Health Metrics](#Health-Metrics)
- [Appointments](#Appointments)
- [Actuator](#Actuator)
4. [Technologies Used](#Technologies-Used)
5. [REST API Documentation](#Rest-api-documentation)
6. [Architecture](#Architecture)
- [Layers](#Layers)
7.[Testing](#Testing)
8. [Installation and Launch](#Installation-and-launch)
- [Requirements](#Requirements)
- [Project Launch](#Project-launch)
9. [Contacts](#Contacts)

---

## Main Features

1. **Patient Management**:
- CRUD operations for creating, editing and deleting user data.
- Ability for doctors and administrators to view patient information.

2. **Authentication and Authorization System**:
- Registration and login for users and doctors.
- Generating JWT tokens to secure API routes.

3. **Clinic Management**:
- Adding and editing clinic data.
- Support for sorting and pagination of the clinic list for comfortable browsing.

4. **Doctor Management**:
- CRUD operations for creating, editing and deleting user data.
- Ability to keep appointments with patients.

5. **Doctor Appointments**:
- Book appointments with a doctor, time and additional notes.
- Automatic deletion of outdated records.

6. **Adding medications for patients by doctors**:
- Maintaining a history of prescribed medications, including dosages and expiration dates.

7. **Health Tracking, Health Metrics**:
- Manage health metrics such as blood sugar, blood pressure and temperature.

8. **Medical Records**:
- Manage medical histories, allergies, vaccination reports and other medical documentation.

9. **Centralization of logging and monitoring**:
- Connecting custom request logging via [LogInterceptor](./src/main/java/by/rublevskaya/interceptor/LogInterceptor.java).
- AOP aspect TimingAspect for measuring method execution time.
- Logging via @Slf4j
10. **Security:**
- Authentication and authorization using JWT.
- Implementation of a custom service for loading data and applying access checks.
---

## Technologies Used
- **Java 21**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **Hibernate**
- **Lombok**
- **JWT**
- **SLF4J**
- **Actuator**
- **FlyWay**
- **Hibernate (JPA Provider)**
- **PostgreSQL** (or any other supported database)
- **Maven**
### Database:
- **DBMS**: PostgreSQL.
- **ORM**: Hibernate is used (JPA annotations for entities).

---

## REST API Documentation

## Overview

This API provides a comprehensive set of endpoints for managing medical-related data including users, doctors, clinics, medications, medical records, health metrics, and appointments.

## Base URL

`http://localhost:8080`


## Authentication

The API provides authentication endpoints for both users and doctors.

### User Registration

```http
POST /auth/register
```

**Request Body:**
```json
{ 
"username": "GraffeGiraffe", 
"email": "GraffeGiraffe@example.com", 
"password": "GraffeGiraffe123456", 
"birthDate": "2005-11-25", 
"bloodType": "A+"
}
```

### Doctor Registration

```http
POST /auth/register-doctor
```

**Request Body:**
```json
{ 
"fullName": "John Doe", 
"username": "johndoeeee", 
"password": "SOTNICOVeee222!", 
"specialty": "Neurologist", 
"licenseNumber": "L123456789", 
"clinicalName": "Uniclincs" 
"isActive": true
}
```

### Login

```http
POST /auth/login
```

**Request Body:**
```json
{ 
"login": "Katusha", 
"password": "Katusha123"
}
```

## Users

### Create User

```http
POST /users
```

**Request Body:**
```json
{ 
"username": "SOTNICOV", 
"email": "SOTNICOV@example.com", 
"password": "SOTNICOV2222", 
"birthDate": "2000-01-01", 
"bloodType": "B+"
}
```

### Get All Users

```http
GET /users
```

### Get User by ID

```http
GET /users/{id}
```

### Update User

```http
PUT /users/{id}
```

**RequeststBody:**
```json
{ 
"username": "newUsername", 
"email": "newemail@example.com", 
"password": "newsecurepassword", 
"birthDate": "1990-01-01", 
"bloodType": "O+"
}
```

### Partial Update User

```http
PATCH /users/{id}
```

**Request Body:**
```json
{ 
"username": "PartUpdate"
}
```

### Delete User

```http
DELETE /users/{id}
```

## Doctors

### Create Doctor

```http
POST /doctors
```

**Request Body:**
```json
{ 
"specialty": "surgeon", 
"licenseNumber": "DR07543854389", 
"clinicName": "Uniclinic", 
"fullName": "POLUSHA", 
"username": "dr.POLUSHA", 
"password": "Password is POLUSHA", 
"isActive": true, 
"email": "POLUSHA@example.com", 
"birthDate": "1990-04-15", 
"bloodType": "O+"
}
```

### Get All Doctors

```http
GET /doctors
```

### Get Doctor by ID

```http
GET /doctors/{id}
```

### Update Doctor

```http
PUT /doctors/{id}
```

**Request Body:**
```json
{ 
"fullName": "John Doe", 
"username": "johndoeee", 
"password": "johndoeee222!", 
"specialty": "Neurologist", 
"licenseNumber": "L123456776", 
"clinicName": "Uniclinic", 
"isActive": true
}
```

### Partial Update Doctor

```http
PATCH /doctors/{id}
```

**Request Body:**
```json
{ 
"fullName": "John DoeUpdate", 
"username": "johndoeeeeUpdate"
}
```

### Delete Doctor

```http
DELETE /doctors/{id}
```

## Clinics

### Create Clinic

```http
POST /clinics
```

**Request Body:**
```json
{ 
"name": "Health Treatment Center", 
"address": "Minsk, st. Primorskaya, 15", 
"phone": "+375 29 123 45 67"
}
```

### Get All Clinics

```http
GET /clinics
```

### Get Clinic by ID

```http
GET /clinics/{id}
```

### Update Clinic

```http
PUT /clinics/{id}
```

**Request Body:**
```json
{ 
"name": "Updated Clinic Name", 
"address": "123 Main Street", 
"phone": "+153456789"
}
```

### Partial Update Clinic

```http
PATCH /clinics/{id}
```

**Request Body:**
```json
{ 
"phone": "+123456789"
}
```

### Delete Clinic

```http
DELETE /clinics/{id}
```

## Medications

### Create Medication

```http
POST /medications
```

**Request Body:**
```json
{ 
"userId": 6, 
"name": "Aspirin", 
"dosage": "100mg", 
"startDate": "2025-01-01T09:30:00", 
"endDate": "2025-10-01T09:30:00", 
"active": true
}
```

### Get Medications by User

```http
GET /medications/user/{userId}
```

### Get Medication by ID

```http
GET /medications/{id}
```

### Update Medicine

```http
PUT /medications/{id}
```

**Request Body:**
```json
{ 
"userId": 1, 
"name": "Updated Aspirin", 
"dosage": "150mg", 
"startDate": "2023-09-01T09:30:00", 
"endDate": "2023-12-31T09:30:00", 
"active": false
}
```

### Delete Medication

```http
DELETE /medications/{id}
```

## Medical Records

### Create Medical Record

```http
POST /medicalrecords
```

**Request Body:**
```json
{ 
"id": 1, 
"userId": 1, 
"title": "Illness Treatment", 
"date": "2023-11-01", 
"description": "Treatment for a flu", 
"doctorNotes": "Take rest for a week", 
"type": "ILLNESS", 
"licenseNumber": "12345-AB"
}
```

### Get Medical Records by User

```http
GET /medicalrecords/user/{userId}
```

### Get Medical Record by ID

```http
GET /medicalrecords/{id}
```

### Update Medical Record

```http
PUT /medicalrecords/{id}
```

**Request Body:**
```json
{ 
"userId": 1, 
"licenseNumber": "12345-AB", 
"title": "Updated Title", 
"date": "2023-11-01", 
"description": "Updated description", 
"doctorNotes": "Updated doctor notes", 
"type": "VACCINE"
}
```

### Delete Medical Record

```http
DELETE /medicalrecords/{id}
```

## Health Metrics

###Create Health Metrics

```http
POST /healthmetrics
```

**Request Body:**
```json
{ 
"userId": 6, 
"timestamp": "2023-11-01T10:30:00", 
"bloodPressure": "120/80", 
"bloodSugar": 5.5, 
"temperature": 36.6, 
"notes": "Feeling good"
}
```

### Get Health Metrics by ID

```http
GET /healthmetrics/{id}
```

### Update Health Metrics

```http
PUT /healthmetrics/{id}
```

**Request Body:**
```json
{ 
"userId": 1, 
"timestamp": "2023-11-02T11:00:00", 
"bloodPressure": "125/85", 
"bloodSugar": 6.0, 
"temperature": 37.0, 
"notes": "Minor headache"
}
```

### Partial Update Health Metrics

```http
PATCH /healthmetrics/{id}
```

**Request Body:**
```json
{ 
"bloodSugar": 5.8, 
"temperature": 36.9
}
```

### Delete Health Metrics

```http
DELETE /healthmetrics/{id}
```

## Appointments

### Create Appointment

```http
POST /appointments
```

**Request Body:**
```json
{ 
"userId": 6, 
"licenseNumber": "12345-AB", 
"dateTime": "2025-10-01T10:00:00", 
"notes": "General check-up"
}
```

### Get User Appointments

```http
GET /appointments/user/{userId}
```

### Complete Appointment

```http
PATCH /appointments/{id}/complete
```

### Delete Appointment

```http
DELETE /appointments/{id}
```

### Delete Outdated Appointments

```http
DELETE /appointments/outdated
```

## Actuator Endpoints

### Health Check

```http
GET /actuator/health
```

### Application Info

```http
GET /actuator/info
```

## Notes

1. All endpoints require proper authentication unless specified otherwise.
2. Replace `{id}` and `{userId}` with actual IDs in the requests.
3. Timestamps should be in ISO 8601 format (e.g., "2023-11-01T10:30:00").
---

## Architecture

### Layers:
1. **Controllers**:
- Isolate the external API from the internal business logic.
2. **Services**:
- Implement the business logic of the system.
3. **Repositories**:
- Responsible for direct interaction with the database.
4. **Entities**:
- Describing database tables using JPA.
5. **DTOs and mappers**:
- Processing data for transfer between layers.

---
## Testing

- DataBase DDL is available here [DDL](src/main/resources/DDL/database_ddl.sql)
- Postman collection is available here [collection](src/main/resources/postman/medical_assistant.postman_collection.json)

---
## Installation and launch

### Requirements
- Java 21+
- Maven
- PostgreSQL

### Launching the project
1. Clone the repository.
2. Configure the database settings in the `application.yml` file.
3. Compile the project:
```shell script
mvn clean install
```
4. Run the project:
```shell script
java -jar target/medical-assistant.jar
```
5. The application will be available on port `8080`.

---

## Contacts

- Author: Katerina Rublevskaya.
- Email: katerublevsk@icloud.com
