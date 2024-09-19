package project.domain;

import org.bson.Document;

import java.time.LocalDate;

public class Appointment {

    private String id;
    private LocalDate fecha;
    private String especialidad;
    private String idDoctor;
    private String idPaciente;
    private String observaciones;

    public Appointment() {}

    public Appointment(String id, LocalDate fecha, String especialidad, String idDoctor, String idPaciente, String observaciones) {
        this.id = id;
        this.fecha = fecha;
        this.especialidad = especialidad;
        this.idDoctor = idDoctor;
        this.idPaciente = idPaciente;
        this.observaciones = observaciones;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getIdDoctor() { return idDoctor; }
    public void setIdDoctor(String idDoctor) { this.idDoctor = idDoctor; }

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }


    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", fecha=" + fecha +
                ", especialidad='" + especialidad + '\'' +
                ", idDoctor='" + idDoctor + '\'' +
                ", idPaciente='" + idPaciente + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}