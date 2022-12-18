package org.wahlzeit.model.coordinates;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.coordinates.Coordinate;
import org.wahlzeit.model.coordinates.CoordinateType;
import org.wahlzeit.model.coordinates.SharedCoordinate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SharedCoordinateTest {
    
    private SharedCoordinate sharedCoordinate;

    @Before
    public void initCoordinate() {
        sharedCoordinate = SharedCoordinate.getInstance();
    }

    @Test
    public void testgetCoordinate() {
        Coordinate c_1 = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.CARTESIAN);
        Coordinate c_2 = sharedCoordinate.getCoordinate(5, 5, 5, CoordinateType.CARTESIAN);
        Coordinate s_1 = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.SPHERICAL);
        Coordinate s_2 = sharedCoordinate.getCoordinate(Math.PI / 2, Math.PI, 5, CoordinateType.SPHERICAL);

        assertEquals(c_1, s_1);
        assertNotEquals(c_2, s_2);
        assertNotEquals(c_1, c_2);
        assertNotEquals(s_1, s_2);
    }

    @Test
    public void testHashCodes() {
        Coordinate c_1 = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.CARTESIAN);
        Coordinate s_1 = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.SPHERICAL);

        assertEquals(c_1.hashCode(), s_1.hashCode());
    }
}
