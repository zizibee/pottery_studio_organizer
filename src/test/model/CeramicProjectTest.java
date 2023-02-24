package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for CeramicProject
class CeramicProjectTest {
    private CeramicProject testProject;
    private CeramicProject testProjectBisque;

    @BeforeEach
    void setUp() {
        testProject = new CeramicProject("test", "stoneware", "greenware");
        testProjectBisque = new CeramicProject("test e", "earthenware", "bisqueware");

    }

    @Test
    void testConstructorGreenware() {
        assertEquals("test", testProject.getTitle());
        assertEquals("stoneware", testProject.getClayType());
        assertEquals("greenware", testProject.getStatus());
        assertEquals("bisque fire", testProject.getNextStep());
    }

    @Test
    void testConstructorBisqueware() {
        assertEquals("test e", testProjectBisque.getTitle());
        assertEquals("earthenware", testProjectBisque.getClayType());
        assertEquals("bisqueware", testProjectBisque.getStatus());
        assertEquals("glaze fire", testProjectBisque.getNextStep());
    }

    @Test
    void testConstructorGlazeware() {
        CeramicProject testProjectGlazed = new CeramicProject("test p", "porcelain", "glazeware");
        assertEquals("test p", testProjectGlazed.getTitle());
        assertEquals("porcelain", testProjectGlazed.getClayType());
        assertEquals("glazeware", testProjectGlazed.getStatus());
        assertEquals("post-glaze work", testProjectGlazed.getNextStep());
    }

    @Test
    void testUpdateBisque() {
        testProject.update();
        assertEquals("bisqueware", testProject.getStatus());
        assertEquals("glaze fire", testProject.getNextStep());
    }

    @Test
    void testUpdateGlaze() {
        testProjectBisque.update();
        assertEquals("glazeware", testProjectBisque.getStatus());
        assertEquals("post-glaze work", testProjectBisque.getNextStep());
    }

    @Test
    void testFinish() {
        testProject.finish();
        assertEquals("COMPLETE", testProject.getNextStep());
    }
}