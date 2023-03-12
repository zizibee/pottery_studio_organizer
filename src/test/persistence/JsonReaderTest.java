package persistence;

import model.CeramicProject;
import model.CeramicProjectList;
import model.Studio;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Based off JsonReaderTest class in JsonSerializationDemo example application

// Test class for JsonReader
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Studio studio = reader.read();
            fail("IOException not thrown");
        } catch (IOException e) {
            System.out.println("Expected exception was thrown");
        }
    }

    @Test
    void testReaderEmptyStudio() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStudio.json");
        try {
            Studio studio = reader.read();
            assertEquals("My studio", studio.getName());
            assertEquals(0, studio.numProjects());
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }

    @Test
    void testReaderGeneralStudio() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStudio.json");
        try {
            Studio studio = reader.read();
            assertEquals("my studio", studio.getName());
            CeramicProjectList finishedProjects = studio.getFinishedProjects();
            assertEquals(1, finishedProjects.length());
            CeramicProject fp1 = finishedProjects.getProjectFromIndex(0);
            checkProject("pFinished", "stoneware", "bisqueware", "NONE", fp1);

            CeramicProjectList inProgressProjects = studio.getInProgressProjects();
            assertEquals(2, inProgressProjects.length());
            CeramicProject ip1 = inProgressProjects.getProjectFromIndex(0);
            checkProject("pInProgress1", "earthenware", "greenware", "bisque fire", ip1);
            CeramicProject ip2 = inProgressProjects.getProjectFromIndex(1);
            checkProject("pInProgress2", "porcelain", "glazeware", "post-glaze work", ip2);
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }
}
