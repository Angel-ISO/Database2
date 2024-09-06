-- Active: 1711459746437@@127.0.0.1@3306@hospitalx

--creacion
CREATE PROCEDURE Pacienteby_id( IN id INT )
BEGIN
    SELECT * FROM patient WHERE patient_id = id;
END;

CALL `Pacienteby_id`(1);

--actualizacion

CREATE PROCEDURE actualizar_peso_paciente (IN paciente_id INT, IN nuevo_peso DECIMAL(5,2))
BEGIN
    UPDATE patient
    SET weight = nuevo_peso
    WHERE patient_id = paciente_id;
END;


CALL actualizar_peso_paciente(1, 90);



-- con exepciones
CREATE PROCEDURE actualizar_peso_paciente (IN paciente_id INT, IN nuevo_peso DECIMAL(5,2))
BEGIN
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'paso un error, no se guardaron los cambios';
    END;

    START TRANSACTION;

    IF nuevo_peso > 100 THEN
        SELECT 'el peso no puede ser mayor a 100, necesitaras un hospital mas grande';
    END IF;

    IF nuevo_peso < 40 THEN
        SELECT 'el peso no puede ser menor a 40, necesitaras un tratamiento para subir el peso';
    END IF;

    UPDATE patient
    SET weight = nuevo_peso
    WHERE patient_id = paciente_id;

    COMMIT;
END;

CALL actualizar_peso_paciente(1, 36);


-- consultar en base a una vista

CREATE VIEW citas_pacientes AS
SELECT pa.patient_id, pa.full_name, pa.blood_type, app.appointment_date, app.reason FROM patient AS pa
INNER JOIN appointments AS app ON pa.patient_id = app.id_patient

CREATE PROCEDURE citas_pacienteby_id(IN id INT)
BEGIN
    SELECT * FROM citas_pacientes AS pa
    WHERE pa.patient_id = id;
    END;

CALL citas_pacienteby_id(3);



