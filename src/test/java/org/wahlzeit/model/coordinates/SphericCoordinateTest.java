package org.wahlzeit.model.coordinates;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.coordinates.CartesianCoordinate;
import org.wahlzeit.model.coordinates.Coordinate;
import org.wahlzeit.model.coordinates.CoordinateType;
import org.wahlzeit.model.coordinates.SharedCoordinate;
import org.wahlzeit.model.coordinates.SphericCoordinate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SphericCoordinateTest {
    
    private SphericCoordinate coordinate;
    private SphericCoordinate coordinateWithValues;
    private SharedCoordinate sharedCoordinate;

    @Before
	public void initCoordinate() {
        sharedCoordinate = SharedCoordinate.getInstance();
        coordinate = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.SPHERICAL).asSphericCoordinate();
        coordinateWithValues = sharedCoordinate.getCoordinate(Math.PI / 2, Math.PI, 5, CoordinateType.SPHERICAL).asSphericCoordinate();
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
        sharedCoordinate.getCoordinate(2 * Math.PI, Math.PI, 5, CoordinateType.SPHERICAL).asSphericCoordinate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTheta() {
        sharedCoordinate.getCoordinate(Math.PI, 2 * Math.PI, 5, CoordinateType.SPHERICAL).asSphericCoordinate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRadius() {
        sharedCoordinate.getCoordinate(Math.PI, Math.PI, -1, CoordinateType.SPHERICAL).asSphericCoordinate();
    }

    @Test
    public void testGetCartesianDistance() {
        Coordinate c = sharedCoordinate.getCoordinate(1, 0, 0, CoordinateType.CARTESIAN).asCartesianCoordinate();

        assertEquals(6.0, coordinateWithValues.getCartesianDistance(c), 0.001);
    }

    @Test
    public void testIsEqual() {
        SphericCoordinate s = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.SPHERICAL).asSphericCoordinate();
        assertTrue(coordinate.isEqual(s));
        assertFalse(coordinateWithValues.isEqual(s));
    }

    @Test
    public void testEquals() {
        SphericCoordinate s = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.SPHERICAL).asSphericCoordinate();

        assertEquals(coordinate, s);
        assertNotEquals(coordinateWithValues, s);
    }

    @Test
    public void testHashCode() {
        SphericCoordinate s = sharedCoordinate.getCoordinate(0, 0, 0, CoordinateType.SPHERICAL).asSphericCoordinate();

        assertEquals(coordinate.hashCode(), s.hashCode());
        assertNotEquals(coordinateWithValues.hashCode(), s.hashCode());
    }

    @Test
    public void testClone() {
        SphericCoordinate s = (SphericCoordinate) coordinateWithValues.clone();

        assertEquals(coordinateWithValues.hashCode(), s.hashCode());
        assertNotEquals(coordinate.hashCode(), s.hashCode());
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
        SphericCoordinate sc_1 = sharedCoordinate.getCoordinate(Math.PI / 2, Math.PI, 1, CoordinateType.SPHERICAL).asSphericCoordinate();
        SphericCoordinate sc_2 = sharedCoordinate.getCoordinate(Math.PI / 4, Math.PI / 2, 1, CoordinateType.SPHERICAL).asSphericCoordinate();
        
        //TODO
        // expected value approved with an online calculator:
        // https://www.vcalc.com/wiki/MichaelBartmess/Great+Circle+Central+Angle
        //assertEquals(45.0, sc_1.getCentralAngle(sc_2), 0.000001);
    }
}