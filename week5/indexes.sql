CREATE INDEX idx_nombre_apellido_id ON Enfermera(nombres, apellidos, id_enfermera);

CREATE INDEX idx_nombre_apellido ON Enfermera(nombres, apellidos);

CREATE INDEX idx_documento_identificacion ON Enfermera(documento_identificacion);
CREATE INDEX idx_enfermera ON Enfermera(id_enfermera);