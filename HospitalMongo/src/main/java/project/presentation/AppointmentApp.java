package project.presentation;

import com.mongodb.client.MongoDatabase;
import project.data.AppointmentDAO;
import project.domain.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
public class AppointmentApp {

    private static AppointmentDAO appointmentDAO;

    public static void run(MongoDatabase database) {
        appointmentDAO = new AppointmentDAO(database);

        var consola = new Scanner(System.in);
        var salir = false;

        while (!salir) {
            try {
                mostrarMenu();
                salir = ejecutarOpciones(consola);
            } catch (Exception e) {
                System.out.println("Ocurrió un error al ejecutar la operación: " + e.getMessage());
            }
            System.out.println();
        }

        consola.close();
    }

    private static void mostrarMenu() {
        System.out.print("""
                *** Sistema de Gestión de Citas ***
                1. Crear nueva cita
                2. Mostrar todas las citas
                3. Buscar cita por ID
                4. Actualizar cita
                5. Eliminar cita
                0. Volver al menú principal
                Elige una opción:\s
                """);
    }

    private static boolean ejecutarOpciones(Scanner consola) {
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;

        switch (opcion) {
            case 1 -> registrarCita(consola);
            case 2 -> listarCitas();
            case 3 -> buscarCitaPorId(consola);
            case 4 -> actualizarCita(consola);
            case 5 -> eliminarCita(consola);
            case 0 -> {
                System.out.println("Volviendo al menú principal...");
                salir = true;
            }
            default -> System.out.println("Opción no válida. Intenta nuevamente.");
        }
        return salir;
    }

    private static void registrarCita(Scanner consola) {
        System.out.println("Registrar Nueva Cita:");
        System.out.print("Fecha (YYYY-MM-DD): ");
        var fecha = LocalDate.parse(consola.nextLine());
        System.out.print("Especialidad: ");
        var especialidad = consola.nextLine();
        System.out.print("ID del Doctor: ");
        var idDoctor = consola.nextLine();
        System.out.print("ID del Paciente: ");
        var idPaciente = consola.nextLine();
        System.out.print("Observaciones: ");
        var observaciones = consola.nextLine();

        var cita = new Appointment(null, fecha, especialidad, idDoctor, idPaciente, observaciones);

        AppointmentApp.appointmentDAO.create(cita);

        System.out.println("Cita registrada exitosamente: " + cita);
    }

    private static void listarCitas() {
        System.out.println("Listado de Citas:");
        List<Appointment> citas = appointmentDAO.getAll();
        citas.forEach(System.out::println);
    }

    private static void buscarCitaPorId(Scanner consola) {
        System.out.println("Buscar Cita por ID:");
        System.out.print("ID de la Cita: ");
        var id = consola.nextLine();
        var cita = appointmentDAO.getById(id);
        if (cita != null) {
            System.out.println("Cita encontrada: " + cita);
        } else {
            System.out.println("No se encontró la cita con ID: " + id);
        }
    }

    private static void actualizarCita(Scanner consola) {
        System.out.println("Modificar Cita:");
        System.out.print("ID de la Cita a Modificar: ");
        var id = consola.nextLine();
        var cita = appointmentDAO.getById(id);

        if (cita != null) {
            System.out.print("Nueva fecha (YYYY-MM-DD): ");
            cita.setFecha(LocalDate.parse(consola.nextLine()));
            System.out.print("Nueva especialidad: ");
            cita.setEspecialidad(consola.nextLine());
            System.out.print("Nuevo ID del Doctor: ");
            cita.setIdDoctor(consola.nextLine());
            System.out.print("Nuevo ID del Paciente: ");
            cita.setIdPaciente(consola.nextLine());
            System.out.print("Nuevas observaciones: ");
            cita.setObservaciones(consola.nextLine());

            appointmentDAO.update(id, cita);
            System.out.println("Cita modificada exitosamente: " + cita);
        } else {
            System.out.println("No se encontró la cita con ID: " + id);
        }
    }

    private static void eliminarCita(Scanner consola) {
        System.out.println("Eliminar Cita:");
        System.out.print("ID de la Cita a Eliminar: ");
        var id = consola.nextLine();
        appointmentDAO.delete(id);
        System.out.println("Cita eliminada exitosamente.");
    }
}