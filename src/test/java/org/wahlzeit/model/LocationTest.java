package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LocationTest {
    
    private Location location;

    @Before
    public void initLocation() {
        location = new Location();
    }

    @Test
    public void testConstructor() {
        assertNotNull(location);

        // Check property after creation
		assertNotNull(location.coordinate);
        assertEquals(0, location.coordinate.getX(), 0.001);
        assertEquals(0, location.coordinate.getY(), 0.001);
        assertEquals(0, location.coordinate.getZ(), 0.001);
    }
}
