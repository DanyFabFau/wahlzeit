package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CoordinateTest {
    
    private Coordinate coordinate;
    private Coordinate coordinateWithValues;

    @Before
	public void initCoordinate() {
		coordinate = new Coordinate();
        coordinateWithValues = new Coordinate(1.0, 2.0, 3.0);
	}

	@Test
	public void testConstructors() {
		assertNotNull(coordinate);
        assertNotNull(coordinateWithValues);

		// Check properties after creation
		assertEquals(0.0, coordinate.getX(), 0.001);
        assertEquals(0.0, coordinate.getY(), 0.001);
        assertEquals(0.0, coordinate.getZ(), 0.001);

        assertEquals(1.0, coordinateWithValues.getX(), 0.001);
        assertEquals(2.0, coordinateWithValues.getY(), 0.001);
        assertEquals(3.0, coordinateWithValues.getZ(), 0.001);
	}

    @Test
    public void testGetDistance() {
        Coordinate c1 = new Coordinate(5, 5, 5);
        Coordinate c2 = new Coordinate(-5, -5, -5);
        Coordinate c3 = new Coordinate(1, 2, 3);
        Coordinate c4 = new Coordinate(-10, 0, 10);

        assertEquals(Math.sqrt(75), coordinate.getDistance(c1), 0.001);
        assertEquals(Math.sqrt(75), coordinate.getDistance(c2), 0.001);
        assertEquals(0, coordinateWithValues.getDistance(c3), 0.001);
        assertEquals(Math.sqrt(200), coordinate.getDistance(c4), 0.001);
    }

    @Test
    public void testIsEqual() {
        Coordinate c1 = new Coordinate(1, 2, 3);

        assertTrue(coordinateWithValues.isEqual(c1));
        assertFalse(coordinate.isEqual(c1));
    }

    @Test
    public void testEquals() {
        Coordinate c1 = new Coordinate(1, 2, 3);

        assertEquals(coordinateWithValues, c1);
        assertNotEquals(coordinate, c1);
    }

    @Test
    public void testHashCode() {
        Coordinate c1 = new Coordinate(1, 2, 3);

        assertEquals(coordinateWithValues.hashCode(), c1.hashCode());
        assertNotEquals(coordinate.hashCode(), c1.hashCode());
    }
}
