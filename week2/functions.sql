-- Active: 1711459746437@@127.0.0.1@3306@hospitalx

CREATE FUNCTION calcular_imc(peso DOUBLE, altura DOUBLE) RETURNS DOUBLE
DETERMINISTIC
BEGIN           
    DECLARE imc DOUBLE;  
    SET imc = peso / (altura * altura); 
    RETURN imc;
END;

SELECT calcular_imc(70, 1.75);


CREATE FUNCTION calcular_edad(fecha_nacimiento DATE)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE edad INT;
    SET edad = TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE());
    RETURN edad;
END;

SELECT calcular_edad('1980-05-15');


CREATE FUNCTION calcular_duracion_prescripcion(prescription_id INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE duracion_total INT;
    DECLARE fecha_prescripcion DATE;
    DECLARE duracion_prescripcion INT;

    SELECT prescription_date, duration 
    INTO fecha_prescripcion, duracion_prescripcion
    FROM prescriptions 
    WHERE id_prescription = prescription_id;

    SET duracion_total = DATEDIFF(DATE_ADD(fecha_prescripcion, INTERVAL duracion_prescripcion DAY), fecha_prescripcion);

    RETURN duracion_total;
END;

SELECT calcular_duracion_prescripcion(1);