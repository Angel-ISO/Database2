package com.project.domain;

import java.time.LocalDateTime;

public class Appointment {
    private int idAppointment;
    private int idDoctor;
    private int idPatient;
    private LocalDateTime appointmentDate;
    private String status;
    private String reason;

    public Appointment() {}

    public Appointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public Appointment(int idDoctor, int idPatient, LocalDateTime appointmentDate, String status, String reason) {
        this.idDoctor = idDoctor;
        this.idPatient = idPatient;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.reason = reason;
    }

    public Appointment(int idAppointment, int idDoctor, int idPatient, LocalDateTime appointmentDate, String status, String reason) {
        this.idAppointment = idAppointment;
        this.idDoctor = idDoctor;
        this.idPatient = idPatient;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.reason = reason;
    }

    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "idAppointment=" + idAppointment +
                ", idDoctor=" + idDoctor +
                ", idPatient=" + idPatient +
                ", appointmentDate=" + appointmentDate +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}

