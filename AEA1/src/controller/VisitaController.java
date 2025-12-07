package controller;

import model.domain.VisitaMedica;
import model.domain.VisitaMedicaLog;
import model.repository.IVisitaLogJsonRepository;
import model.repository.VisitaLogJsonRepository;
import model.service.VisitaService;
import view.VisitaConsoleView;
import java.util.ArrayList;

public class VisitaController {
    private VisitaConsoleView view = VisitaConsoleView.getInstance();
    private VisitaService service = VisitaService.getInstance();
    private IVisitaLogJsonRepository logRepository = VisitaLogJsonRepository.getInstance();

    public void init() {
        boolean continuar = true;
        
        while (continuar) {
            VisitaMedica visita = view.getVisitaForm();
            
            if (visita != null) {
                try {
                    view.showMessage("--- Aplicant MD5 ---", false);
                    VisitaMedica vMD5 = service.getEncryptedMD5(visita);
                    view.showVisita(vMD5);
                    logRepository.addLog(new VisitaMedicaLog("MD5", "ENC", vMD5));

                    view.showMessage("--- Aplicant SHA256 ---", false);
                    VisitaMedica vSHA = service.getEncryptedSHA256(visita);
                    view.showVisita(vSHA);
                    logRepository.addLog(new VisitaMedicaLog("SHA256", "ENC", vSHA));

                    view.showMessage("--- Aplicant AES (Encriptar) ---", false);
                    VisitaMedica vAES = service.getEncryptedAES(visita);
                    view.showVisita(vAES);
                    logRepository.addLog(new VisitaMedicaLog("AES", "ENC", vAES));

                    view.showMessage("--- Aplicant AES (Desencriptar) ---", false);
                    VisitaMedica vAESDes = service.getDecryptedAES(vAES);
                    view.showVisita(vAESDes);
                    logRepository.addLog(new VisitaMedicaLog("AES", "DESENC", vAESDes));

                    ArrayList<VisitaMedicaLog> logs = logRepository.getAll();
                    view.showLogs(logs);

                } catch (Exception e) {
                    view.showMessage("Error en el process: " + e.getMessage(), true);
                    e.printStackTrace();
                }
            }

            continuar = view.pedirContinuar();
        }
        view.showMessage("Tancant la aplicació...", false);
    }
}