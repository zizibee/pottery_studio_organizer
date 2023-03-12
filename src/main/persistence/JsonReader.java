package persistence;

// Based on JsonReader class from JsonSerializationDemo example application

import model.CeramicProject;
import model.Studio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader reading a studio app from stored JSON data in file
public class JsonReader {
    private String sourceFile;

    // EFFECTS: constructs a reader and sets source file to read as given source file
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    // EFFECTS: reads studio app from file and returns it
    // throws IOException if an error occurs while reading data from file
    public Studio read() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudio(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String sourceFile) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses studio from JSON object and returns it
    private Studio parseStudio(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Studio studio = new Studio(name);
        addInProgressProjects(studio, jsonObject);
        addFinishedProjects(studio, jsonObject);
        return studio;
    }

    // MODIFIES: studio
    // EFFECTS: parses in-progress projects from JSON object and adds it to studio
    private void addInProgressProjects(Studio studio, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("in-progress projects");
        for (Object json : jsonArray) {
            JSONObject nextProject = (JSONObject) json;
            addProject(studio, nextProject);
        }
    }

    // MODIFIES: studio
    // EFFECTS: parses finished projects from JSON object and adds it to studio
    private void addFinishedProjects(Studio studio, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("finished projects");
        for (Object json : jsonArray) {
            JSONObject nextProject = (JSONObject) json;
            addProject(studio, nextProject);
        }
    }

    // MODIFIES: studio
    // EFFECTS: parses project from JSON object and adds it to studio
    private void addProject(Studio studio, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String clayType = jsonObject.getString("clay type");
        String stage = jsonObject.getString("stage");
        String nextStep = jsonObject.getString("next step");
        CeramicProject project = new CeramicProject(title, clayType, stage, nextStep);
        studio.addProject(project);
    }
}
