package view;

import model.domain.VisitaMedica;
import model.domain.VisitaMedicaLog;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class VisitaConsoleView {
    private static VisitaConsoleView instance;
    private Scanner scanner;

    private VisitaConsoleView() {
        scanner = new Scanner(System.in);
    }

    public static VisitaConsoleView getInstance() {
        if (instance == null) instance = new VisitaConsoleView();
        return instance;
    }

    public void showMessage(String msg, boolean isError) {
        if (isError) System.err.println("ERROR: " + msg);
        else System.out.println(msg);
    }

    public VisitaMedica getVisitaForm() {
        try {
            showMessage("--- NOVA VISITA ---", false);
            System.out.print("ID Visita (int): ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Nom Pacient: ");
            String pacient = scanner.nextLine();

            System.out.print("Nom Metge: ");
            String metge = scanner.nextLine();

            System.out.print("Data (YYYY-MM-DD): ");
            String dataStr = scanner.nextLine();
            LocalDate data = LocalDate.parse(dataStr);

            System.out.print("Diagnòstic: ");
            String diagnostic = scanner.nextLine();

            return new VisitaMedica(id, pacient, metge, data, diagnostic);

        } catch (NumberFormatException e) {
            showMessage("El ID ha de ser un número.", true);
            return null;
        } catch (DateTimeParseException e) {
            showMessage("Format de data invalid. Utilitza YYYY-MM-DD", true);
            return null;
        }
    }

    public void showVisita(VisitaMedica v) {
        if (v != null) showMessage(v.toString(), false);
    }

    public void showLogs(ArrayList<VisitaMedicaLog> logs) {
        showMessage("\n==== AUDITORÍA DE LOGS ====", false);
        if (logs.isEmpty()) {
            showMessage("No hi ha logs registrats.", false);
        } else {
            for (VisitaMedicaLog log : logs) {
                System.out.println(log);
            }
        }
        System.out.println("===========================\n");
    }

    public boolean pedirContinuar() {
        System.out.print("\nProcesar un altre visita? (s/n): ");
        String resp = scanner.nextLine();
        return resp.equalsIgnoreCase("s");
    }
}