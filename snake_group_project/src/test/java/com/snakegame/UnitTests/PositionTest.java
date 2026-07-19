package com.snakegame.UnitTests;
import org.junit.jupiter.api.Test;

import com.snakegame.Position;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

public class PositionTest {
	private static Position p1;
	private static Position p2;
	private static Position p3;
	private static Position p4;
	
	@BeforeAll
	static void setUp() {
		p1 = new Position(0,0);
        p2 = new Position(0,0);
        p3 = new Position(13, 13);
        p4 = new Position(13, 13);
	}
    @Test
    void equalsTest(){ 
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
        assertTrue(p3.equals(p4));
        assertTrue(p4.equals(p3));
        assertFalse(p1.equals(p3));
        assertFalse(p3.equals(p1));
    }
    
    @Test
    void nonPositionTest() {
    	assertFalse(p1.equals(13));
    	assertFalse(p2.equals(5));
    	assertFalse(p3.equals("Hello"));
    	assertFalse(p4.equals("Hi"));
    }
    
}
