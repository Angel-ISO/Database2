-- Active: 1725557404427@@127.0.0.1@1433@hospital@dbo


-- sql server version 

CREATE TABLE focus (
    id_speciality INT PRIMARY KEY IDENTITY(1,1),
    name_speciality VARCHAR(30) UNIQUE
);

CREATE TABLE hospitals (
    id_hospital INT PRIMARY KEY IDENTITY(1,1),
    name_hospital VARCHAR(40) NOT NULL UNIQUE,
    address_hospital VARCHAR(50) NOT NULL
);

CREATE TABLE contact_info (
    id_contact INT PRIMARY KEY IDENTITY(1,1),
    telephone VARCHAR(15),
    address VARCHAR(80),
    patient_id INT,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id)
);

CREATE TABLE patient (
    patient_id INT PRIMARY KEY IDENTITY(1,1),
    full_name VARCHAR(50) NOT NULL,
    gender VARCHAR(10) CHECK (gender IN ('MALE','FEMALE','OTHER')) NOT NULL,
    birth_date DATE,
    blood_type VARCHAR(3),
    medical_insurance VARCHAR(30),
    height FLOAT NOT NULL,
    weight FLOAT NOT NULL
);

CREATE TABLE doctor (
    doctor_id INT PRIMARY KEY IDENTITY(1,1),
    full_name VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    is_active BIT,
    years_of_experience INT,
    office_number VARCHAR(10),
    id_speciality INT NOT NULL,
    hospital_id INT NOT NULL,
    FOREIGN KEY (id_speciality) REFERENCES focus(id_speciality),
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id_hospital)
);

CREATE TABLE appointments (
    id_appointment INT PRIMARY KEY IDENTITY(1,1),
    id_doctor INT,
    id_patient INT,
    appointment_date DATETIME,
    status VARCHAR(10) CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED')) NOT NULL,
    reason VARCHAR(255),
    FOREIGN KEY (id_doctor) REFERENCES doctor(doctor_id),
    FOREIGN KEY (id_patient) REFERENCES patient(patient_id)
);

CREATE TABLE medical_records (
    id_record INT PRIMARY KEY IDENTITY(1,1),
    id_patient INT NOT NULL,
    creation_date DATE,
    notes TEXT,
    diagnoses TEXT,
    FOREIGN KEY (id_patient) REFERENCES patient(patient_id)
);

CREATE TABLE tests (
    id_test INT PRIMARY KEY IDENTITY(1,1),
    id_patient INT,
    test_type VARCHAR(50),
    description TEXT,
    results TEXT,
    test_date DATE,
    FOREIGN KEY (id_patient) REFERENCES patient(patient_id)
);

CREATE TABLE medications (
    id_medication INT PRIMARY KEY IDENTITY(1,1),
    name_medication VARCHAR(50) NOT NULL,
    presentation VARCHAR(50),
    dosage VARCHAR(50),
    indications TEXT,
    contraindications TEXT
);

CREATE TABLE prescriptions (
    id_prescription INT PRIMARY KEY IDENTITY(1,1),
    id_doctor INT,
    id_patient INT,
    id_medication INT,
    prescription_date DATE,
    dosage VARCHAR(50),
    frequency VARCHAR(50),
    duration VARCHAR(50),
    FOREIGN KEY (id_doctor) REFERENCES doctor(doctor_id),
    FOREIGN KEY (id_patient) REFERENCES patient(patient_id),
    FOREIGN KEY (id_medication) REFERENCES medications(id_medication)
);

CREATE TABLE patients_history (
    history_id INT IDENTITY(1,1) PRIMARY KEY,
    patient_id INT,
    action_type VARCHAR(10),  
    old_full_name VARCHAR(50),
    old_gender VARCHAR(10) CHECK (old_gender IN ('MALE', 'FEMALE', 'OTHER')),
    old_birth_date DATE,
    old_blood_type VARCHAR(3),
    old_medical_insurance VARCHAR(30),
    old_height FLOAT,
    old_weight FLOAT,
    new_full_name VARCHAR(50),
    new_gender VARCHAR(10) CHECK (new_gender IN ('MALE', 'FEMALE', 'OTHER')),
    new_birth_date DATE,
    new_blood_type VARCHAR(3),
    new_medical_insurance VARCHAR(30),
    new_height FLOAT,
    new_weight FLOAT,
    action_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Enfermera (
    id_enfermera INT PRIMARY KEY IDENTITY(1,1),
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    direccion VARCHAR(100),
    edad INT,
    documento_identificacion VARCHAR(20) UNIQUE,
    telefono VARCHAR(15),
    cargo VARCHAR(50),
    email VARCHAR(50) UNIQUE
);

CREATE TABLE TraceResults (
    TraceID INT PRIMARY KEY IDENTITY(1,1),
    ActionType VARCHAR(50),
    TableName VARCHAR(50),
    RecordID INT,
    ActionTimestamp DATETIME DEFAULT GETDATE(),
    OldValue TEXT,
    NewValue TEXT
);

INSERT INTO focus (name_speciality)
VALUES 
    ('Cardiología'),
    ('Oftalmología'),
    ('Pediatría'),
    ('Cirugía Plástica'),
    ('Dermatología'),
    ('Neurocirugía');

INSERT INTO hospitals (name_hospital, address_hospital)
VALUES 
    ('International', 'Valle de Menzuly, Km 7. Piedecuesta'),
    ('Clinica Chicamocha', 'Cl. 40 #27A-22'),
    ('Clinica Giron E.S.E','Cl. 33 #25-36');

INSERT INTO doctor (full_name, email, is_active, years_of_experience, office_number, id_speciality, hospital_id)
VALUES 
    ('Dr. Juan Perez', 'juan.perez@gmail.com', 1, 12, 'C-3', 3, 1),  
    ('Dra. Maria Lopez', 'maria.lopez@gmail.com', 1, 8, 'B-2', 1, 2),  
    ('Dr. Carlos Ruiz', 'carlos.ruiz@gmail.com', 1, 15, 'A-1', 2, 3); 

INSERT INTO patient (full_name, gender, birth_date, blood_type, medical_insurance, height, weight)
VALUES 
    ('Luis Colmenares', 'MALE', '1980-05-15', 'O+', 'Seguro ABC', 1.75, 70),
    ('Lucia Gómez', 'FEMALE', '1992-10-25', 'A-', 'Seguro XYZ', 1.60, 55),
    ('Carlos Martínez', 'MALE', '1985-07-20', 'B+', 'Seguro DEF', 1.80, 85);

INSERT INTO appointments (id_doctor, id_patient, appointment_date, status, reason)
VALUES 
    (1, 1, '2024-09-10 10:00:00', 'PENDING', 'Chequeo general'),
    (2, 2, '2024-09-15 11:30:00', 'CONFIRMED', 'Consulta cardiológica'),
    (3, 3, '2024-09-20 09:00:00', 'PENDING', 'Examen de la vista');

INSERT INTO medications (name_medication, presentation, dosage, indications, contraindications)
VALUES 
    ('Ibuprofeno', 'Tabletas', '200mg', 'Dolor leve a moderado', 'Úlceras pépticas'),
    ('Amoxicilina', 'Cápsulas', '500mg', 'Infecciones bacterianas', 'Alergia a penicilina'),
    ('Paracetamol', 'Jarabe', '500mg/5ml', 'Fiebre', 'Insuficiencia hepática grave');

INSERT INTO prescriptions (id_doctor, id_patient, id_medication, prescription_date, dosage, frequency, duration)
VALUES 
    (1, 1, 1, '2024-09-10', '200mg', 'Cada 8 horas', '5 días'),
    (2, 2, 2, '2024-09-15', '500mg', 'Cada 12 horas', '7 días'),
    (3, 3, 3, '2024-09-20', '500mg/5ml', 'Cada 6 horas', '3 días');



-- triggers

CREATE TRIGGER trg_AfterInsert_Doctor
ON doctor
AFTER INSERT
AS
BEGIN
    DECLARE @NewValue VARCHAR(MAX), @RecordID INT;
    SELECT @RecordID = inserted.doctor_id FROM inserted;
    SELECT @NewValue = CONCAT(full_name, ', ', email, ', ', years_of_experience) FROM inserted WHERE doctor_id = @RecordID;

    INSERT INTO TraceResults (ActionType, TableName, RecordID, NewValue)
    VALUES ('INSERT', 'doctor', @RecordID, @NewValue);
END;

CREATE TRIGGER trg_AfterUpdate_Doctor
ON doctor
AFTER UPDATE
AS
BEGIN
    DECLARE @OldValue VARCHAR(MAX), @NewValue VARCHAR(MAX), @RecordID INT;
    SELECT @RecordID = inserted.doctor_id FROM inserted;
    SELECT @OldValue = CONCAT(full_name, ', ', email, ', ', years_of_experience) FROM deleted WHERE doctor_id = @RecordID;
    SELECT @NewValue = CONCAT(full_name, ', ', email, ', ', years_of_experience) FROM inserted WHERE doctor_id = @RecordID;

    INSERT INTO TraceResults (ActionType, TableName, RecordID, OldValue, NewValue)
    VALUES ('UPDATE', 'doctor', @RecordID, @OldValue, @NewValue);
END;

CREATE TRIGGER trg_AfterDelete_Doctor
ON doctor
AFTER DELETE
AS
BEGIN
    DECLARE @OldValue VARCHAR(MAX), @RecordID INT;
    SELECT @RecordID = deleted.doctor_id FROM deleted;
    SELECT @OldValue = CONCAT(full_name, ', ', email, ', ', years_of_experience) FROM deleted WHERE doctor_id = @RecordID;

    INSERT INTO TraceResults (ActionType, TableName, RecordID, OldValue)
    VALUES ('DELETE', 'doctor', @RecordID, @OldValue);
END;



UPDATE doctor
SET email = 'email@outlook.com'
WHERE doctor_id = 1;


