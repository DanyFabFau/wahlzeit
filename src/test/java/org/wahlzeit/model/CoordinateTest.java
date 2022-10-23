package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
}
