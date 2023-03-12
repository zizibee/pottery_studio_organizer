package persistence;

import model.CeramicProject;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Class that gives inheritors access to checkProject method
public class JsonTest {

    protected void checkProject(String title, String clayType, String stage, String nextStep, CeramicProject project) {
        assertEquals(title, project.getTitle());
        assertEquals(clayType, project.getClayType());
        assertEquals(stage, project.getStage());
        assertEquals(nextStep, project.getNextStep());
    }
}
