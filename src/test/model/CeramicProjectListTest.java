package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Tests for CeramicProjectList class
public class CeramicProjectListTest {
    private CeramicProjectList testList;
    private CeramicProject testProjectOne = new CeramicProject("test 1", "porcelain",
            "bisqueware");
    private CeramicProject testProjectTwo = new CeramicProject("test 2", "stoneware",
            "greenware");
    private CeramicProject testProjectTwoA = new CeramicProject("test 2", "earthenware",
            "glazeware");
    private CeramicProject testProjectThree = new CeramicProject("test 3", "earthenware",
            "glazeware");

    @BeforeEach
    void setUp() {
        testList = new CeramicProjectList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testList.length());
    }

    @Test
    void testAddProjectEmpty() {
        testList.addProject(testProjectOne);
        assertEquals("test 1", testList.getProjectFromIndex(0).getTitle());
    }

    @Test
    void testAddProjectUnique() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        assertEquals("test 2", testList.getProjectFromIndex(1).getTitle());
    }

    @Test
    void testAddProjectUsedTitle() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        testList.addProject(testProjectTwoA);
        assertEquals(null, testList.getProjectFromIndex(2));
        assertEquals(2, testList.length());
    }

    @Test
    void testRemoveProject() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        ArrayList<CeramicProject> result = testList.removeProject("test 1");
        assertEquals(1, result.size());
        assertEquals("test 2", result.get(0).getTitle());
        assertEquals(1, testList.length());
        assertEquals("test 2", testList.getProjectFromIndex(0).getTitle());
    }

    @Test
    void testRemoveProjectDNE() {
        testList.addProject(testProjectOne);
        ArrayList<CeramicProject> result = testList.removeProject("test 2");
        assertEquals(null, result);
        assertEquals(1, testList.length());
        assertEquals("test 1", testList.getProjectFromIndex(0).getTitle());
    }

    @Test
    void testGetProjectFromTitle() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        CeramicProject result = testList.getProjectFromTitle("test 2");
        assertEquals("test 2", result.getTitle());
    }

    @Test
    void testGetProjectFromTitleDNE() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        CeramicProject result = testList.getProjectFromTitle("test 3");
        assertEquals(null, result);
    }

    @Test
    void testGetProjectFromIndex() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        CeramicProject result = testList.getProjectFromIndex(1);
        assertEquals("test 2", result.getTitle());
    }

    @Test
    void testGetProjectFromIndexDNE() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        CeramicProject result = testList.getProjectFromIndex(2);
        assertEquals(null, result);
    }

    @Test
    void testGroupForFiringNoMatches() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        ArrayList<CeramicProject> result = testList.groupForFiring("earthenware", "post-glaze work");
        assertEquals(0, result.size());
    }

    @Test
    void testGroupForFiringSomeMatches() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        testList.addProject(testProjectThree);
        CeramicProject testProjectFour = new CeramicProject("test 4", "stoneware", "greenware");
        CeramicProject testProjectFive = new CeramicProject("test 5", "stoneware", "bisqueware");
        testList.addProject(testProjectFour);
        testList.addProject(testProjectFive);

        ArrayList<CeramicProject> result = testList.groupForFiring("stoneware", "bisque fire");
        assertEquals(2, result.size());
        assertEquals("test 2", result.get(0).getTitle());
        assertEquals("test 4", result.get(1).getTitle());
    }

    @Test
    void testLength() {
        testList.addProject(testProjectOne);
        testList.addProject(testProjectTwo);
        assertEquals(2, testList.length());
    }

    @Test
    void testLengthEmpty() {
        assertEquals(0, testList.length());
    }
}
