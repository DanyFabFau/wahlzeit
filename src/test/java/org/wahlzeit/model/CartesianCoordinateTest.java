package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CartesianCoordinateTest {
    
    private CartesianCoordinate coordinate;
    private CartesianCoordinate coordinateWithValues;
    private SharedCoordinate sharedCoordinate;

    @Before
	public void initCoordinate() {
		coordinate = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();
        coordinateWithValues = sharedCoordinate.getCoordinate(1, 2, 3, CoordinateType.CARTESIAN).asCartesianCoordinate();
        sharedCoordinate = SharedCoordinate.getInstance();
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
    public void testGetCartesianDistance() {
        CartesianCoordinate c1 = sharedCoordinate.getCoordinate(5, 5, 5, CoordinateType.CARTESIAN).asCartesianCoordinate();
        CartesianCoordinate c2 = sharedCoordinate.getCoordinate(-5, -5, -5, CoordinateType.CARTESIAN).asCartesianCoordinate();
        CartesianCoordinate c3 = sharedCoordinate.getCoordinate(1, 2, 3, CoordinateType.CARTESIAN).asCartesianCoordinate();
        CartesianCoordinate c4 = sharedCoordinate.getCoordinate(-10, 0, 10, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertEquals(Math.sqrt(75), coordinate.getCartesianDistance(c1), 0.001);
        assertEquals(Math.sqrt(75), coordinate.getCartesianDistance(c2), 0.001);
        assertEquals(0, coordinateWithValues.getCartesianDistance(c3), 0.001);
        assertEquals(Math.sqrt(200), coordinate.getCartesianDistance(c4), 0.001);
    }

    @Test
    public void testIsEqual() {
        CartesianCoordinate c1 = sharedCoordinate.getCoordinate(1, 2, 3, CoordinateType.CARTESIAN).asCartesianCoordinate();
        assertTrue(coordinateWithValues.isEqual(c1));
        assertFalse(coordinate.isEqual(c1));
    }

    @Test
    public void testEquals() {
        CartesianCoordinate c1 = sharedCoordinate.getCoordinate(1, 2, 3, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertEquals(coordinateWithValues, c1);
        assertNotEquals(coordinate, c1);
    }

    @Test
    public void testHashCode() {
        CartesianCoordinate c1 = sharedCoordinate.getCoordinate(1, 2, 3, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertEquals(coordinateWithValues.hashCode(), c1.hashCode());
        assertNotEquals(coordinate.hashCode(), c1.hashCode());
    }

    @Test
    public void testAsSphericCoordinate() {
        CartesianCoordinate c1 = sharedCoordinate.getCoordinate(1, 2, 3, CoordinateType.CARTESIAN).asCartesianCoordinate();
        SphericCoordinate s1 = c1.asSphericCoordinate();

        // expected values from an online calculator:
        // https://keisan.casio.com/exec/system/1359533867
        assertTrue(s1 instanceof SphericCoordinate);
        assertEquals(0.64052, s1.getPhi(), 0.00001);
        assertEquals(1.10715, s1.getTheta(), 0.00001);
        assertEquals(3.74166, s1.getRadius(), 0.00001);
    } 

    @Test
    public void testGetCentralAngle() {
        // first need to fix SphericCoordinate.getCentralAngle
    }
}
