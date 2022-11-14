package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocationTest {
    
    private Location location;

    @Before
    public void initLocation() {
        location = new Location(0, 0, 0);
    }

    @Test
    public void testConstructor() {
        assertNotNull(location);

        // Check properties after creation
		assertNotNull(location.getCoordinate());
        assertEquals(0, location.getCoordinate().asCartesianCoordinate().getX(), 0.001);
        assertEquals(0, location.getCoordinate().asCartesianCoordinate().getY(), 0.001);
        assertEquals(0, location.getCoordinate().asCartesianCoordinate().getZ(), 0.001);
    }
}
