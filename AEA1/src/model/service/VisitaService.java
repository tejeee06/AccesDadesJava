package model.service;

import model.domain.VisitaMedica;
import model.service.encryption.AESSecurityService;
import model.service.encryption.MD5SecurityService;
import model.service.encryption.SHA256SecurityService;

public class VisitaService {

    private static VisitaService instance = null;
    private MD5SecurityService md5 = new MD5SecurityService();
    private SHA256SecurityService sha256 = new SHA256SecurityService();
    private AESSecurityService aes = new AESSecurityService();

    private VisitaService() {}

    public static VisitaService getInstance() {
        if (instance == null) instance = new VisitaService();
        return instance;
    }

    public void validaVisita(VisitaMedica visita) throws Exception {
        if (visita == null) throw new Exception("La visita es NULL");
        if (visita.getNomPacient() == null || visita.getNomPacient().isEmpty()) throw new Exception("Falta el nom del Pacient");
        if (visita.getNomMetge() == null || visita.getNomMetge().isEmpty()) throw new Exception("Falta el nom del Metge");
        if (visita.getData() == null) throw new Exception("Falta la Data");
        if (visita.getDiagnostic() == null || visita.getDiagnostic().isEmpty()) throw new Exception("Falta el Diagnostic");
    }

    private VisitaMedica clonar(VisitaMedica v) {
        return new VisitaMedica(v.getIdVisita(), v.getNomPacient(), v.getNomMetge(), v.getData(), v.getDiagnostic());
    }

    public VisitaMedica getEncryptedMD5(VisitaMedica original) throws Exception {
        validaVisita(original);
        VisitaMedica copia = clonar(original);
        copia.setNomPacient(md5.encripta(original.getNomPacient()));
        copia.setDiagnostic(md5.encripta(original.getDiagnostic()));
        return copia;
    }

    public VisitaMedica getEncryptedSHA256(VisitaMedica original) throws Exception {
        validaVisita(original);
        VisitaMedica copia = clonar(original);
        copia.setNomPacient(sha256.encripta(original.getNomPacient()));
        copia.setDiagnostic(sha256.encripta(original.getDiagnostic()));
        return copia;
    }

    public VisitaMedica getEncryptedAES(VisitaMedica original) throws Exception {
        validaVisita(original);
        VisitaMedica copia = clonar(original);
        copia.setNomPacient(aes.encripta(original.getNomPacient()));
        copia.setDiagnostic(aes.encripta(original.getDiagnostic()));
        return copia;
    }

    public VisitaMedica getDecryptedAES(VisitaMedica encrypted) throws Exception {
        VisitaMedica copia = clonar(encrypted);
        copia.setNomPacient(aes.desencripta(encrypted.getNomPacient()));
        copia.setDiagnostic(aes.desencripta(encrypted.getDiagnostic()));
        return copia;
    }
    
}