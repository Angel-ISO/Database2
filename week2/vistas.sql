-- Active: 1711459746437@@127.0.0.1@3306@hospitalx

CREATE VIEW vista_paciente
AS
SELECT * FROM patient

Create View hospital_doctors
AS
SELECT d.full_name AS doctor, h.address_hospital AS direccion_hospital, f.name_speciality AS nombre_especialidad
FROM doctor AS d
INNER JOIN hospitals AS h ON d.hospital_id = h.id_hospital
INNER JOIN focus AS f ON d.id_speciality = f.id_speciality;


--actualizacion

CREATE OR REPLACE VIEW vista_paciente AS 
SELECT pa.* , ap.appointment_date, ap.reason, ap.status 
FROM patient AS pa
INNER JOIN appointments AS ap ON pa.patient_id= ap.id_patient


-- actualizar basado en tabla

CREATE VIEW paciente_basico AS
SELECT * FROM patient

UPDATE paciente_basico AS pa
SET pa.weight = 75
WHERE pa.patient_id = 1;


--elininacion
DROP VIEW paciente_basico;


