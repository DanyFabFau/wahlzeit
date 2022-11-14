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

    @Before
	public void initCoordinate() {
		coordinate = new CartesianCoordinate();
        coordinateWithValues = new CartesianCoordinate(1.0, 2.0, 3.0);
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
        CartesianCoordinate c1 = new CartesianCoordinate(5, 5, 5);
        CartesianCoordinate c2 = new CartesianCoordinate(-5, -5, -5);
        CartesianCoordinate c3 = new CartesianCoordinate(1, 2, 3);
        CartesianCoordinate c4 = new CartesianCoordinate(-10, 0, 10);

        assertEquals(Math.sqrt(75), coordinate.getCartesianDistance(c1), 0.001);
        assertEquals(Math.sqrt(75), coordinate.getCartesianDistance(c2), 0.001);
        assertEquals(0, coordinateWithValues.getCartesianDistance(c3), 0.001);
        assertEquals(Math.sqrt(200), coordinate.getCartesianDistance(c4), 0.001);
    }

    @Test
    public void testIsEqual() {
        CartesianCoordinate c1 = new CartesianCoordinate(1, 2, 3);
        assertTrue(coordinateWithValues.isEqual(c1));
        assertFalse(coordinate.isEqual(c1));

        CartesianCoordinate c2 = new CartesianCoordinate(-1, 0, 0);
        SphericCoordinate sc = new SphericCoordinate(Math.PI / 2, Math.PI, 1);
        assertTrue(c2.isEqual(sc));
    }

    @Test
    public void testEquals() {
        CartesianCoordinate c1 = new CartesianCoordinate(1, 2, 3);

        assertEquals(coordinateWithValues, c1);
        assertNotEquals(coordinate, c1);
    }

    @Test
    public void testHashCode() {
        CartesianCoordinate c1 = new CartesianCoordinate(1, 2, 3);

        assertEquals(coordinateWithValues.hashCode(), c1.hashCode());
        assertNotEquals(coordinate.hashCode(), c1.hashCode());
    }

    @Test
    public void testAsSphericCoordinate() {
        CartesianCoordinate c1 = new CartesianCoordinate(1, 2,3);
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
