package com.project.data;

import com.project.domain.Appointment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.project.config.conex.getConection;

public class AppointmentDAO {

    private static final String SQL_SELECT = "SELECT * FROM appointments ORDER BY id_appointment";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM appointments WHERE id_appointment = ?";
    private static final String SQL_INSERT = "INSERT INTO appointments (id_doctor, id_patient, appointment_date, status, reason) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE appointments SET id_doctor=?, id_patient=?, appointment_date=?, status=?, reason=? WHERE id_appointment=?";
    private static final String SQL_DELETE = "DELETE FROM appointments WHERE id_appointment=?";

    public List<Appointment> listar() {
        List<Appointment> appointmentList = new ArrayList<>();
        Connection con = getConection();
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("id_appointment"),
                        rs.getInt("id_doctor"),
                        rs.getInt("id_patient"),
                        rs.getTimestamp("appointment_date").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getString("reason")
                );
                appointmentList.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar citas: " + e.getMessage());
        }
        return appointmentList;
    }

    public boolean buscarPorId(Appointment appointment) {
        Connection con = getConection();
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setInt(1, appointment.getIdAppointment());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    appointment.setIdDoctor(rs.getInt("id_doctor"));
                    appointment.setIdPatient(rs.getInt("id_patient"));
                    appointment.setAppointmentDate(rs.getTimestamp("appointment_date").toLocalDateTime());
                    appointment.setStatus(rs.getString("status"));
                    appointment.setReason(rs.getString("reason"));
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cita por ID: " + e.getMessage());
        }
        return false;
    }

    public boolean agregar(Appointment appointment) {
        Connection con = getConection();
        try (PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, appointment.getIdDoctor());
            ps.setInt(2, appointment.getIdPatient());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(appointment.getAppointmentDate()));
            ps.setString(4, appointment.getStatus());
            ps.setString(5, appointment.getReason());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar cita: " + e.getMessage());
        }
        return false;
    }

    public boolean modificar(Appointment appointment) {
        Connection con = getConection();
        try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, appointment.getIdDoctor());
            ps.setInt(2, appointment.getIdPatient());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(appointment.getAppointmentDate()));
            ps.setString(4, appointment.getStatus());
            ps.setString(5, appointment.getReason());
            ps.setInt(6, appointment.getIdAppointment());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al modificar cita: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminar(Appointment appointment) {
        Connection con = getConection();
        try (PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, appointment.getIdAppointment());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cita: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        var appointmentDao = new AppointmentDAO();

        var nuevaCita = new Appointment(1, 2, LocalDateTime.now(), "PENDING", "Consulta general");
        var agregado = appointmentDao.agregar(nuevaCita);
        if (agregado)
            System.out.println("Cita agregada: " + nuevaCita);
        else
            System.out.println("No se pudo agregar la cita: " + nuevaCita);

        var citaModificar = new Appointment(1, 1, 2, LocalDateTime.now().plusDays(1), "CONFIRMED", "Cambio de horario");
        var modificado = appointmentDao.modificar(citaModificar);
        if (modificado)
            System.out.println("Cita modificada: " + citaModificar);
        else
            System.out.println("No se pudo modificar la cita: " + citaModificar);

        var citaEliminar = new Appointment(1);
        var eliminado = appointmentDao.eliminar(citaEliminar);
        if (eliminado)
            System.out.println("Cita eliminada: " + citaEliminar);
        else
            System.out.println("No se pudo eliminar la cita: " + citaEliminar);

        System.out.println("Listado de citas:");
        List<Appointment> citas = appointmentDao.listar();
        citas.forEach(System.out::println);

        var citaBuscar = new Appointment(1);
        var encontrado = appointmentDao.buscarPorId(citaBuscar);
        if (encontrado)
            System.out.println("Cita encontrada: " + citaBuscar);
        else
            System.out.println("No se encontró la cita con ID: " + citaBuscar.getIdAppointment());
    }


    public int getNumberOfPrescriptions(int patientId) {
        int numPrescriptions = 0;
        String sql = "{CALL GetNumberOfPrescriptions(?)}";
        try (Connection con = getConection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, patientId);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    numPrescriptions = rs.getInt("num_prescriptions");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el número de recetas: " + e.getMessage());
        }
        return numPrescriptions;
    }



}
