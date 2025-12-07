package model.domain;

public class VisitaMedicaLog {

    private String algoritme;
    private String accio;
    private VisitaMedica visita;

    public VisitaMedicaLog() {}

    public VisitaMedicaLog(String algoritme, String accio, VisitaMedica visita) {
        this.algoritme = algoritme;
        this.accio = accio;
        this.visita = visita;
    }

    public String getAlgoritme() { return algoritme; }
    public void setAlgoritme(String algoritme) { this.algoritme = algoritme; }

    public String getAccio() { return accio; }
    public void setAccio(String accio) { this.accio = accio; }

    public VisitaMedica getVisita() { return visita; }
    public void setVisita(VisitaMedica visita) { this.visita = visita; }

    @Override
    public String toString() {
        return "LOG [" + algoritme + " - " + accio + "] -> " + visita;
    }
    
}