package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SharedCoordinateTest {
    
    private SharedCoordinate sharedCoordinate;

    @Before
	public void initCoordinate() {
        sharedCoordinate = SharedCoordinate.getInstance();
	}

	@Test
	public void testConstructors() {
        Coordinate cc_1 = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.CARTESIAN);
        Coordinate cc_2 = sharedCoordinate.getCoordinate(5, 5, 5, CoordinateType.CARTESIAN);
        Coordinate sc_1 = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.SPHERICAL);
        Coordinate sc_2 = sharedCoordinate.getCoordinate(5, 5, 5, CoordinateType.SPHERICAL);

        assertEquals(cc_1, sc_1);
        assertNotEquals(cc_2, sc_2);
        assertNotEquals(cc_1, cc_2);
        assertNotEquals(sc_1, sc_2);
	}
}
