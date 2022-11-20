package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SphericCoordinateTest {
    
    private SphericCoordinate coordinate;
    private SphericCoordinate coordinateWithValues;

    @Before
	public void initCoordinate() {
		coordinate = new SphericCoordinate();
        coordinateWithValues = new SphericCoordinate(Math.PI / 2, Math.PI, 5);
	}

	@Test
	public void testConstructors() {
		assertNotNull(coordinate);
        assertNotNull(coordinateWithValues);

        assertEquals(0.0d, coordinate.getPhi(), 0.001);
        assertEquals(0.0d, coordinate.getTheta(), 0.001);
        assertEquals(0.0d, coordinate.getRadius(), 0.001);

        assertEquals(Math.PI / 2, coordinateWithValues.getPhi(), 0.001);
        assertEquals(Math.PI, coordinateWithValues.getTheta(), 0.001);
        assertEquals(5.0, coordinateWithValues.getRadius(), 0.001);
	}

    @Test(expected = IllegalArgumentException.class)
    public void testSetPhi() {
        SphericCoordinate sc = new SphericCoordinate(2 * Math.PI, Math.PI, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTheta() {
        SphericCoordinate sc = new SphericCoordinate(Math.PI, 2 * Math.PI, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRadius() {
        SphericCoordinate sc = new SphericCoordinate(Math.PI, Math.PI, -1);
    }

    @Test
    public void testGetCartesianDistance() {
        Coordinate c = new CartesianCoordinate(1, 0, 0);

        assertEquals(6.0, coordinateWithValues.getCartesianDistance(c), 0.001);
    }

    @Test
    public void testIsEqual() {
        SphericCoordinate s = new SphericCoordinate();
        assertTrue(coordinate.isEqual(s));
        assertFalse(coordinateWithValues.isEqual(s));
    }

    @Test
    public void testEquals() {
        SphericCoordinate s = new SphericCoordinate();

        assertEquals(coordinate, s);
        assertNotEquals(coordinateWithValues, s);
    }

    @Test
    public void testHashCode() {
        SphericCoordinate s = new SphericCoordinate();

        assertEquals(coordinate.hashCode(), s.hashCode());
        assertNotEquals(coordinateWithValues.hashCode(), s.hashCode());
    }

    @Test
    public void testAsDegree() {
        assertEquals(180.0, coordinate.asDegree(Math.PI), 0.001);
    }

    @Test
    public void testAbsDiffDeg() {
        assertEquals(120, coordinate.absDiffDeg(60, 180), 0.001);
        assertEquals(160, coordinate.absDiffDeg(250, 50), 0.001);
    }

    @Test
    public void testAsCartesianCoordinate() {
        CartesianCoordinate cc = coordinateWithValues.asCartesianCoordinate();

        assertEquals(-5.0, cc.getX(), 0.001);
        assertEquals(0, cc.getY(), 0.001);
        assertEquals(0, cc.getZ(), 0.001);
    } 

    @Test
    public void testGetCentralAngle() {
        SphericCoordinate sc_1 = new SphericCoordinate(Math.PI / 2, Math.PI, 1);
        SphericCoordinate sc_2 = new SphericCoordinate(Math.PI / 4, Math.PI / 2, 1);
        
        //TODO
        // expected value approved with an online calculator:
        // https://www.vcalc.com/wiki/MichaelBartmess/Great+Circle+Central+Angle
        //assertEquals(45.0, sc_1.getCentralAngle(sc_2), 0.000001);
    }
}
