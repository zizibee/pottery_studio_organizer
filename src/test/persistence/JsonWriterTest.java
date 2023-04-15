package persistence;

import model.CeramicProject;
import model.CeramicProjectList;
import model.Studio;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Test class for JsonWriter
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            new Studio("my studio");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException not thrown");
        } catch (IOException e) {
            System.out.println("Expected exception thrown");
        }
    }

    @Test
    void testWriterEmptyStudio() {
        try {
            Studio studio = new Studio("my studio");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStudio.json");
            writer.open();
            writer.write(studio);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStudio.json");
            studio = reader.read();
            assertEquals("my studio", studio.getName());
            assertEquals(0, studio.numProjects());
        } catch (IOException e) {
            fail("Unexpected exception thrown");
        }
    }

    @Test
    void testWriterGeneralStudio() {
        try {
            Studio studio = new Studio("my studio");
            CeramicProject fp1;
            fp1 = new CeramicProject("pF1", "stoneware", "bisqueware", "NONE");
            studio.addProject(fp1);
            CeramicProject ip1;
            ip1 = new CeramicProject("pInP1", "earthenware", "greenware", "bisque fire");
            studio.addProject(ip1);
            CeramicProject ip2;
            ip2 = new CeramicProject("pInP2", "porcelain", "glazeware", "post-glaze work");
            studio.addProject(ip2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudio.json");
            writer.open();
            writer.write(studio);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStudio.json");
            studio = reader.read();

            assertEquals("my studio", studio.getName());
            CeramicProjectList finishedProjects = studio.getFinishedProjects();
            assertEquals(1, finishedProjects.length());
            CeramicProject fP = finishedProjects.getProjectFromIndex(0);
            checkProject("pF1", "stoneware", "bisqueware", "NONE", fP);

            CeramicProjectList inProgressProjects = studio.getInProgressProjects();
            assertEquals(2, inProgressProjects.length());
            CeramicProject inP1 = inProgressProjects.getProjectFromIndex(0);
            checkProject("pInP1", "earthenware", "greenware", "bisque fire", inP1);
            CeramicProject inP2 = inProgressProjects.getProjectFromIndex(1);
            checkProject("pInP2", "porcelain", "glazeware", "post-glaze work", inP2);
        } catch (IOException e) {
            fail("Unexpected exception thrown");
        }
    }
}
