package project.presentation;

import com.mongodb.client.MongoDatabase;
import project.data.DoctorDAO;
import project.domain.Doctor;
import java.util.List;

import java.util.Scanner;

public class DoctorApp {

    private static DoctorDAO doctorDAO;

    public static void run(MongoDatabase database) {
        doctorDAO = new DoctorDAO(database);

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n---- Menú de Doctores ----");
            System.out.println("1. Crear nuevo doctor");
            System.out.println("2. Mostrar todos los doctores");
            System.out.println("3. Buscar doctor por ID");
            System.out.println("4. Actualizar doctor");
            System.out.println("5. Eliminar doctor");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createDoctor(scanner);
                    break;
                case 2:
                    showAllDoctors();
                    break;
                case 3:
                    findDoctorById(scanner);
                    break;
                case 4:
                    updateDoctor(scanner);
                    break;
                case 5:
                    deleteDoctor(scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (option != 0);
    }

    private static void createDoctor(Scanner scanner) {
        System.out.println("\n--- Crear Nuevo Doctor ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();
        System.out.print("Años de experiencia: ");
        int añosExperiencia = scanner.nextInt();

        Doctor doctor = new Doctor(null, nombre, especialidad, añosExperiencia);
        doctorDAO.create(doctor);
    }

    private static void showAllDoctors() {
        System.out.println("\n--- Mostrar Todos los Doctores ---");
        List<Doctor> doctors = doctorDAO.getAll();
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    private static void findDoctorById(Scanner scanner) {
        System.out.println("\n--- Buscar Doctor por ID ---");
        System.out.print("Ingrese el ID del doctor: ");
        String id = scanner.nextLine();
        Doctor doctor = doctorDAO.getById(id);
        if (doctor != null) {
            System.out.println(doctor);
        } else {
            System.out.println("Doctor no encontrado.");
        }
    }

    private static void updateDoctor(Scanner scanner) {
        System.out.println("\n--- Actualizar Doctor ---");
        System.out.print("Ingrese el ID del doctor a actualizar: ");
        String id = scanner.nextLine();
        Doctor doctor = doctorDAO.getById(id);

        if (doctor != null) {
            System.out.print("Nuevo nombre: ");
            doctor.setNombre(scanner.nextLine());
            System.out.print("Nueva especialidad: ");
            doctor.setEspecialidad(scanner.nextLine());
            System.out.print("Nuevos años de experiencia: ");
            doctor.setAñosexperiencia(scanner.nextInt());

            doctorDAO.update(id, doctor);
        } else {
            System.out.println("Doctor no encontrado.");
        }
    }

    private static void deleteDoctor(Scanner scanner) {
        System.out.println("\n--- Eliminar Doctor ---");
        System.out.print("Ingrese el ID del doctor a eliminar: ");
        String id = scanner.nextLine();
        doctorDAO.delete(id);
    }
}
