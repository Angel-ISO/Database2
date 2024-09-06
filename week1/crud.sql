START TRANSACTION;

INSERT INTO doctor (full_name, email, is_active, years_of_experience, office_number, id_speciality, hospital_id)
VALUES ('Dra. Ana Torres', 'ana.torres@gmail.com', true, 10, 'D-4', 4, 1); 


INSERT INTO patient (full_name, gender, birth_date, blood_type, medical_insurance, height, weight)
VALUES ('Ana Serrano', 'FEMALE', '1995-04-22', 'AB+', 'Seguro OPQ', 1.65, 60);

INSERT INTO appointments (id_doctor, id_patient, appointment_date, status, reason)
VALUES (11, 4, '2024-10-01 15:00:00', 'PENDING', 'Consulta dermatológica');

UPDATE appointments
SET status = 'CONFIRMED', reason = 'Consulta dermatológica confirmada'
WHERE id_appointment = 4;

INSERT INTO medications (name_medication, presentation, dosage, indications, contraindications)
VALUES ('Loratadina', 'Tabletas', '10mg', 'Alergias', 'Hipersensibilidad');

INSERT INTO prescriptions (id_doctor, id_patient, id_medication, prescription_date, dosage, frequency, duration)
VALUES (11, 4, 4, '2024-10-01', '10mg', 'Cada 24 horas', '7 días');

UPDATE prescriptions
SET duration = '14 días'
WHERE id_prescription = 4;

DELETE FROM appointments
WHERE id_appointment = 4;


COMMIT;


