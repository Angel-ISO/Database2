
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
    ('Dr. Juan Perez', 'juan.perez@gmail.com', true, 12, 'C-3', 3, 1),  
    ('Dra. Maria Lopez', 'maria.lopez@gmail.com', true, 8, 'B-2', 1, 2),  
    ('Dr. Carlos Ruiz', 'carlos.ruiz@gmail.com', true, 15, 'A-1', 2, 3); 


INSERT INTO patient (full_name, gender, birth_date, blood_type, medical_insurance, height, weight)
VALUES 
    ('Luis Colmenares', 'MALE', '1980-05-15', 'O+', 'Seguro ABC', 1.75, 70),
    ('Lucia Gómez', 'FEMALE', '1992-10-25', 'A-', 'Seguro XYZ', 1.60, 55),
    ('Carlos Martínez', 'MALE', '1985-07-20', 'B+', 'Seguro DEF', 1.80, 85);

INSERT INTO appointments (id_doctor, id_patient, appointment_date, status, reason)
VALUES 
    (8, 1, '2024-09-10 10:00:00', 'PENDING', 'Chequeo general'),
    (9, 2, '2024-09-15 11:30:00', 'CONFIRMED', 'Consulta cardiológica'),
    (10, 3, '2024-09-20 09:00:00', 'PENDING', 'Examen de la vista');


INSERT INTO medications (name_medication, presentation, dosage, indications, contraindications)
VALUES 
    ('Ibuprofeno', 'Tabletas', '200mg', 'Dolor leve a moderado', 'Úlceras pépticas'),
    ('Amoxicilina', 'Cápsulas', '500mg', 'Infecciones bacterianas', 'Alergia a penicilina'),
    ('Paracetamol', 'Jarabe', '500mg/5ml', 'Fiebre', 'Insuficiencia hepática grave');

INSERT INTO prescriptions (id_doctor, id_patient, id_medication, prescription_date, dosage, frequency, duration)
VALUES 
    (8, 1, 1, '2024-09-10', '200mg', 'Cada 8 horas', '5 días'),
    (9, 2, 2, '2024-09-15', '500mg', 'Cada 12 horas', '7 días'),
    (10, 3, 3, '2024-09-20', '500mg/5ml', 'Cada 6 horas', '3 días');

