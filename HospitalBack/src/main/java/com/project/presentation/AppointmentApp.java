package com.project.presentation;

import com.project.data.AppointmentDAO;
import com.project.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AppointmentApp {
    public static void main(String[] args) {
        var salir = false;
        var consola = new Scanner(System.in);
        var appointmentDAO = new AppointmentDAO();

        while (!salir) {
            try {
                mostrarMenu();
                salir = ejecutarOpciones(consola, appointmentDAO);
            } catch (Exception e) {
                System.out.println("Ocurrió un error al ejecutar operación: " + e.getMessage());
            }
            System.out.println();
        }
        consola.close();
    }

    private static void mostrarMenu() {
        System.out.print("""
                *** Sistema de Gestión de Citas ***
                1. Listar Citas
                2. Registrar Cita
                3. Modificar Cita
                4. Eliminar Cita
                5. Buscar Cita por ID
                6. buscar recetas de mayores de 18 años
                7. Salir
                Elige una opción:\s
                """);
    }

    private static boolean ejecutarOpciones(Scanner consola, AppointmentDAO appointmentDAO) {
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;

        switch (opcion) {
            case 1 -> {
                System.out.println("Listado de Citas:");
                List<Appointment> citas = appointmentDAO.listar();
                citas.forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("Registrar Cita:");
                System.out.print("ID del Doctor: ");
                var idDoctor = Integer.parseInt(consola.nextLine());
                System.out.print("ID del Paciente: ");
                var idPatient = Integer.parseInt(consola.nextLine());
                System.out.print("Fecha y Hora de la Cita (yyyy-MM-ddTHH:mm:ss): ");
                var appointmentDate = LocalDateTime.parse(consola.nextLine());
                System.out.print("Estado (PENDING/CONFIRMED/CANCELLED): ");
                var status = consola.nextLine();
                System.out.print("Motivo: ");
                var reason = consola.nextLine();

                var cita = new Appointment(0, idDoctor, idPatient, appointmentDate, status, reason);
                var registrado = appointmentDAO.agregar(cita);
                if (registrado)
                    System.out.println("Cita registrada: " + cita);
                else
                    System.out.println("No se pudo registrar la cita.");
            }
            case 3 -> {
                System.out.println("Modificar Cita:");
                System.out.print("ID de la Cita a Modificar: ");
                var idAppointment = Integer.parseInt(consola.nextLine());
                System.out.print("ID del Doctor: ");
                var idDoctor = Integer.parseInt(consola.nextLine());
                System.out.print("ID del Paciente: ");
                var idPatient = Integer.parseInt(consola.nextLine());
                System.out.print("Fecha y Hora de la Cita (yyyy-MM-ddTHH:mm:ss): ");
                var appointmentDate = LocalDateTime.parse(consola.nextLine());
                System.out.print("Estado (PENDING/CONFIRMED/CANCELLED): ");
                var status = consola.nextLine();
                System.out.print("Motivo: ");
                var reason = consola.nextLine();

                var cita = new Appointment(idAppointment, idDoctor, idPatient, appointmentDate, status, reason);
                var modificado = appointmentDAO.modificar(cita);
                if (modificado)
                    System.out.println("Cita modificada: " + cita);
                else
                    System.out.println("No se pudo modificar la cita.");
            }
            case 4 -> {
                System.out.println("Eliminar Cita:");
                System.out.print("ID de la Cita a Eliminar: ");
                var idAppointment = Integer.parseInt(consola.nextLine());
                var cita = new Appointment(idAppointment);
                var eliminado = appointmentDAO.eliminar(cita);
                if (eliminado)
                    System.out.println("Cita eliminada: " + cita);
                else
                    System.out.println("No se pudo eliminar la cita.");
            }
            case 5 -> {
                System.out.println("Buscar Cita por ID:");
                System.out.print("ID de la Cita: ");
                var idAppointment = Integer.parseInt(consola.nextLine());
                var cita = new Appointment(idAppointment);
                var encontrado = appointmentDAO.buscarPorId(cita);
                if (encontrado)
                    System.out.println("Cita encontrada: " + cita);
                else
                    System.out.println("No se encontró la cita con ID: " + idAppointment);
            }
            case 6 -> {
                System.out.println("Buscar recetas de mayores de 18 años:");
                var numPrescriptions = appointmentDAO.getNumberOfPrescriptions(18);
                System.out.println("Número de recetas: " + numPrescriptions);
            }
            case 7 -> {
                System.out.println("Hasta luego!");
                salir = true;
            }
            default -> System.out.println("Opción no reconocida");
        }
        return salir;
    }

}
