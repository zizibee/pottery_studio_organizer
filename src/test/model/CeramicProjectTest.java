package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for CeramicProject class
class CeramicProjectTest {
    private CeramicProject testProject;
    private CeramicProject testProjectBsq;

    @BeforeEach
    void setUp() {
        testProject = new CeramicProject("test", "stoneware", "greenware", null);
        testProjectBsq = new CeramicProject("test e", "earthenware", "bisqueware", null);
    }

    @Test
    void testConstructorGreenware() {
        assertEquals("test", testProject.getTitle());
        assertEquals("stoneware", testProject.getClayType());
        assertEquals("greenware", testProject.getStage());
        assertEquals("bisque fire", testProject.getNextStep());
    }

    @Test
    void testConstructorBisqueware() {
        assertEquals("test e", testProjectBsq.getTitle());
        assertEquals("earthenware", testProjectBsq.getClayType());
        assertEquals("bisqueware", testProjectBsq.getStage());
        assertEquals("glaze fire", testProjectBsq.getNextStep());
    }

    @Test
    void testConstructorGlazeware() {
        CeramicProject testProjectGlazed;
        testProjectGlazed = new CeramicProject("test p", "porcelain", "glazeware", null);
        assertEquals("test p", testProjectGlazed.getTitle());
        assertEquals("porcelain", testProjectGlazed.getClayType());
        assertEquals("glazeware", testProjectGlazed.getStage());
        assertEquals("post-glaze work", testProjectGlazed.getNextStep());
    }

    @Test
    void testUpdateBisque() {
        testProject.update();
        assertEquals("bisqueware", testProject.getStage());
        assertEquals("glaze fire", testProject.getNextStep());
    }

    @Test
    void testUpdateGlaze() {
        testProjectBsq.update();
        assertEquals("glazeware", testProjectBsq.getStage());
        assertEquals("post-glaze work", testProjectBsq.getNextStep());
    }

    @Test
    void testUpdatePostGlaze() {
        CeramicProject testProjectPostGlaze = new CeramicProject("test p", "porcelain",
                "glazeware", null);
        testProjectPostGlaze.update();
        assertEquals("glazeware", testProjectPostGlaze.getStage());
        assertEquals("NONE", testProjectPostGlaze.getNextStep());
    }

    @Test
    void testFinish() {
        testProject.finish();
        assertEquals("NONE", testProject.getNextStep());
    }
}