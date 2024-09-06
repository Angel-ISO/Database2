-- Active: 1711459746437@@127.0.0.1@3306@hospitalx
CREATE TRIGGER validar_altura_peso 
BEFORE INSERT ON patient
FOR EACH ROW
BEGIN
    IF NEW.height < 0.5 OR NEW.height > 2.5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: La altura debe estar entre 0.5 y 2.5 metros.';
    END IF;

    IF NEW.weight < 3 OR NEW.weight > 300 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El peso debe estar entre 3 y 300 kg.';
    END IF;
END;

CREATE TRIGGER validar_altura_peso_update 
BEFORE UPDATE ON patient
FOR EACH ROW
BEGIN
    IF NEW.height < 0.5 OR NEW.height > 2.5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: La altura debe estar entre 0.5 y 2.5 metros.';
    END IF;

    IF NEW.weight < 3 OR NEW.weight > 300 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El peso debe estar entre 3 y 300 kg.';
    END IF;
END;

--pruebas

INSERT INTO patient (full_name, gender, birth_date, blood_type, medical_insurance, height, weight) 
VALUES ('Juliana', 'FEMALE', '1990-04-15', 'O+', 'Sanitas', 1.8, 350);


UPDATE patient 
SET height = 3.0 
WHERE patient_id = 1;



-- validaciones de citas


CREATE TRIGGER validar_cita 
BEFORE INSERT ON appointments
FOR EACH ROW
BEGIN
    DECLARE citas_existentes INT;

    IF NEW.appointment_date < CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: La cita no puede ser anterior a la fecha actual.';
    END IF;

    IF NEW.appointment_date > CURDATE() + INTERVAL 14 DAY THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: La cita no puede ser posterior a 14 días.';
    END IF;

    SELECT COUNT(*) INTO citas_existentes
    FROM appointments
    WHERE DATE(appointment_date) = DATE(NEW.appointment_date);

    IF citas_existentes >= 4 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: No se pueden registrar más de 4 citas en la misma fecha.';
    END IF;
END;


CREATE TRIGGER validar_cita_update 
BEFORE UPDATE ON appointments
FOR EACH ROW
BEGIN
    DECLARE citas_existentes INT;

    IF NEW.appointment_date < CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: La cita no puede ser anterior a la fecha actual.';
    END IF;

    IF NEW.appointment_date > CURDATE() + INTERVAL 14 DAY THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: La cita no puede ser posterior a 14 días.';
    END IF;

    SELECT COUNT(*) INTO citas_existentes
    FROM appointments
    WHERE DATE(appointment_date) = DATE(NEW.appointment_date);

    IF citas_existentes >= 4 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: No se pueden registrar más de 4 citas en la misma fecha.';
    END IF;
END;


CREATE TRIGGER validar_cita_delete 
BEFORE DELETE ON appointments
FOR EACH ROW
BEGIN
    DECLARE horas_faltantes INT;

    IF OLD.status = 'CONFIRMED' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: No se pueden eliminar citas confirmadas.';
    END IF;

    SET horas_faltantes = TIMESTAMPDIFF(HOUR, NOW(), OLD.appointment_date);

    IF horas_faltantes < 24 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: No se pueden eliminar citas que ocurren en menos de 24 horas.';
    END IF;
END;

INSERT INTO appointments (id_doctor, id_patient, appointment_date, status) 
VALUES (1, 1, '2023-09-01 10:00:00', 'PENDING');

INSERT INTO appointments (id_doctor, id_patient, appointment_date, status) 
VALUES (1, 1, CURDATE() + INTERVAL 15 DAY, 'PENDING');



DELETE FROM appointments WHERE id_appointment = 5;



-- manejar historial de cambios en los pacientes

CREATE TABLE patients_history (
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    action_type VARCHAR(10),  
    old_full_name VARCHAR(50),
    old_gender ENUM('MALE', 'FEMALE', 'OTHER'),
    old_birth_date DATE,
    old_blood_type VARCHAR(3),
    old_medical_insurance VARCHAR(30),
    old_height DOUBLE,
    old_weight DOUBLE,
    new_full_name VARCHAR(50),
    new_gender ENUM('MALE', 'FEMALE', 'OTHER'),
    new_birth_date DATE,
    new_blood_type VARCHAR(3),
    new_medical_insurance VARCHAR(30),
    new_height DOUBLE,
    new_weight DOUBLE,
    action_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- si un paciente se registra entonces el registro se guarda en la tabla de historial tambien 
CREATE TRIGGER patients_history_insert
AFTER INSERT ON patient
FOR EACH ROW
BEGIN
    INSERT INTO patients_history (
        patient_id, action_type, new_full_name, new_gender, new_birth_date, 
        new_blood_type, new_medical_insurance, new_height, new_weight
    )
    VALUES (
        NEW.patient_id, 'INSERT', NEW.full_name, NEW.gender, NEW.birth_date, 
        NEW.blood_type, NEW.medical_insurance, NEW.height, NEW.weight
    );
END;


CREATE TRIGGER patients_history_update
AFTER UPDATE ON patient
FOR EACH ROW
BEGIN
    INSERT INTO patients_history (
        patient_id, action_type, old_full_name, old_gender, old_birth_date, old_blood_type, 
        old_medical_insurance, old_height, old_weight, new_full_name, new_gender, new_birth_date, 
        new_blood_type, new_medical_insurance, new_height, new_weight
    )
    VALUES (
        OLD.patient_id, 'UPDATE', OLD.full_name, OLD.gender, OLD.birth_date, OLD.blood_type, 
        OLD.medical_insurance, OLD.height, OLD.weight, NEW.full_name, NEW.gender, 
        NEW.birth_date, NEW.blood_type, NEW.medical_insurance, NEW.height, NEW.weight
    );
END;


CREATE TRIGGER patients_history_delete
AFTER DELETE ON patient
FOR EACH ROW
BEGIN
    INSERT INTO patients_history (
        patient_id, action_type, old_full_name, old_gender, old_birth_date, old_blood_type, 
        old_medical_insurance, old_height, old_weight
    )
    VALUES (
        OLD.patient_id, 'DELETE', OLD.full_name, OLD.gender, OLD.birth_date, OLD.blood_type, 
        OLD.medical_insurance, OLD.height, OLD.weight
    );
END;


--ejemplos

INSERT INTO patient (full_name, gender, birth_date, blood_type, medical_insurance, height, weight) 
VALUES ('Angel UwU', 'MALE', '2005-05-10', 'O+', 'sura', 1.69, 56);


UPDATE patient 
SET full_name = 'Angel Battler', weight = 70 
WHERE patient_id = 7;

DELETE FROM patient WHERE patient_id = 7;
