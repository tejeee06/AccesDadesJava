package model.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.domain.VisitaMedicaLog;
import util.LocalDateAdapter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

public class VisitaLogJsonRepository implements IVisitaLogJsonRepository {
    private static VisitaLogJsonRepository instance;
    private ArrayList<VisitaMedicaLog> logs;
    private static final String FILE_PATH = "resources/visitalog.json";
    private Gson gson;

    private VisitaLogJsonRepository() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        
        File file = new File(FILE_PATH);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        logs = loadFromFile();
    }

    public static VisitaLogJsonRepository getInstance() {
        if (instance == null) {
            instance = new VisitaLogJsonRepository();
        }
        return instance;
    }

    @Override
    public void addLog(VisitaMedicaLog log) {
        logs.add(log);
        saveToFile();
    }

    @Override
    public ArrayList<VisitaMedicaLog> getAll() {
        return logs;
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(logs, writer);
        } catch (IOException e) {
            System.err.println("Error guardando JSON: " + e.getMessage());
        }
    }

    private ArrayList<VisitaMedicaLog> loadFromFile() {
        if (!Files.exists(Path.of(FILE_PATH))) return new ArrayList<>();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<VisitaMedicaLog>>() {}.getType();
            ArrayList<VisitaMedicaLog> data = gson.fromJson(reader, listType);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
