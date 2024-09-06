CREATE TABLE focus (
    id_speciality INT PRIMARY KEY AUTO_INCREMENT,
    name_speciality VARCHAR(30) UNIQUE
);

CREATE TABLE hospitals (
    id_hospital INT PRIMARY KEY AUTO_INCREMENT,
    name_hospital VARCHAR(40) NOT NULL UNIQUE,
    address_hospital VARCHAR(50) NOT NULL
);

CREATE TABLE contact_info (
    id_contact INT PRIMARY KEY AUTO_INCREMENT,
    telephone VARCHAR(15),
    address VARCHAR(80),
    patient_id INT,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id)
);

CREATE TABLE patient (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(50) NOT NULL,
    gender ENUM ('MALE','FEMALE','OTHER') NOT NULL,
    birth_date DATE,
    blood_type VARCHAR(3),
    medical_insurance VARCHAR(30),
    height DOUBLE NOT NULL,
    weight DOUBLE NOT NULL
);

CREATE TABLE doctor (
    doctor_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    is_active BOOLEAN,
    years_of_experience INT,
    office_number VARCHAR(10),
    id_speciality INT NOT NULL,
    hospital_id INT NOT NULL,
    FOREIGN KEY (id_speciality) REFERENCES focus(id_speciality),
    FOREIGN KEY (hospital_id) REFERENCES hospitals(id_hospital)
);

CREATE TABLE appointments (
    id_appointment INT PRIMARY KEY AUTO_INCREMENT,
    id_doctor INT,
    id_patient INT,
    appointment_date DATETIME,
    status ENUM ('PENDING', 'CONFIRMED', 'CANCELLED') NOT NULL,
    reason VARCHAR(255),
    FOREIGN KEY (id_doctor) REFERENCES doctor(doctor_id),
    FOREIGN KEY (id_patient) REFERENCES patient(patient_id)
);

CREATE TABLE medical_records (
    id_record INT PRIMARY KEY AUTO_INCREMENT,
    id_patient INT NOT NULL,
    creation_date DATE,
    notes TEXT,
    diagnoses TEXT,
    FOREIGN KEY (id_patient) REFERENCES patient(patient_id)
);

CREATE TABLE tests (
    id_test INT PRIMARY KEY AUTO_INCREMENT,
    id_patient INT,
    test_type VARCHAR(50),
    description TEXT,
    results TEXT,
    test_date DATE,
    FOREIGN KEY (id_patient) REFERENCES patient(patient_id)
);

CREATE TABLE medications (
    id_medication INT PRIMARY KEY AUTO_INCREMENT,
    name_medication VARCHAR(50) NOT NULL,
    presentation VARCHAR(50),
    dosage VARCHAR(50),
    indications TEXT,
    contraindications TEXT
);

CREATE TABLE prescriptions (
    id_prescription INT PRIMARY KEY AUTO_INCREMENT,
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
