package project.app;

import com.mongodb.client.MongoDatabase;
import project.config.Conex;
import project.presentation.AppointmentApp;
import project.presentation.DoctorApp;
import project.presentation.PatientApp;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Conex conex = new Conex();
        conex.conectar();
        MongoDatabase database = conex.getDatabase();

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n---- Menú Principal ----");
            System.out.println("1. Gestionar Citas");
            System.out.println("2. Gestionar Pacientes");
            System.out.println("3. Gestionar Doctores");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    AppointmentApp.run(database);
                    break;
                case 2:
                    PatientApp.run(database);
                    break;
                case 3:
                    DoctorApp.run(database);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (option != 0);

        conex.cerrar();
        scanner.close();
    }
}
