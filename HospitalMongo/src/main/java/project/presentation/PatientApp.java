package project.presentation;

import com.mongodb.client.MongoDatabase;
import project.data.PatientDAO;
import project.domain.Patient;

import java.util.List;
import java.util.Scanner;

public class PatientApp {

    private static PatientDAO patientDAO;

    public static void run(MongoDatabase database) {
        patientDAO = new PatientDAO(database);

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n---- Menú de Pacientes ----");
            System.out.println("1. Crear nuevo paciente");
            System.out.println("2. Mostrar todos los pacientes");
            System.out.println("3. Buscar paciente por ID");
            System.out.println("4. Actualizar paciente");
            System.out.println("5. Eliminar paciente");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createPatient(scanner);
                    break;
                case 2:
                    showAllPatients();
                    break;
                case 3:
                    findPatientById(scanner);
                    break;
                case 4:
                    updatePatient(scanner);
                    break;
                case 5:
                    deletePatient(scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (option != 0);
    }

    private static void createPatient(Scanner scanner) {
        System.out.println("\n--- Crear Nuevo Paciente ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Género: ");
        String genero = scanner.nextLine();

        Patient patient = new Patient(null, nombre, edad, genero);
        patientDAO.create(patient);
    }

    private static void showAllPatients() {
        System.out.println("\n--- Mostrar Todos los Pacientes ---");
        List<Patient> patients = patientDAO.getAll();
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    private static void findPatientById(Scanner scanner) {
        System.out.println("\n--- Buscar Paciente por ID ---");
        System.out.print("Ingrese el ID del paciente: ");
        String id = scanner.nextLine();
        Patient patient = patientDAO.getById(id);
        if (patient != null) {
            System.out.println(patient);
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    private static void updatePatient(Scanner scanner) {
        System.out.println("\n--- Actualizar Paciente ---");
        System.out.print("Ingrese el ID del paciente a actualizar: ");
        String id = scanner.nextLine();
        Patient patient = patientDAO.getById(id);

        if (patient != null) {
            System.out.print("Nuevo nombre: ");
            patient.setNombre(scanner.nextLine());
            System.out.print("Nueva edad: ");
            patient.setEdad(scanner.nextInt());
            scanner.nextLine();  // Consumir el salto de línea
            System.out.print("Nuevo género: ");
            patient.setGenero(scanner.nextLine());

            patientDAO.update(id, patient);
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    private static void deletePatient(Scanner scanner) {
        System.out.println("\n--- Eliminar Paciente ---");
        System.out.print("Ingrese el ID del paciente a eliminar: ");
        String id = scanner.nextLine();
        patientDAO.delete(id);
    }
}
