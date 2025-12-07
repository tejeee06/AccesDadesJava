package model.repository;

import model.domain.VisitaMedicaLog;
import java.util.ArrayList;

public interface IVisitaLogJsonRepository {
   void addLog(VisitaMedicaLog log);
    ArrayList<VisitaMedicaLog> getAll(); 
}