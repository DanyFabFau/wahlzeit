package org.wahlzeit.model;

public class CartesianCoordinate extends AbstractCoordinate {
    
    private double x;
    private double y;
    private double z;

    /**
     * @methodtype constructor
     */
    public CartesianCoordinate() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * @methodtype constructor
     */
    public CartesianCoordinate(double x, double y, double z) {
        assertClassInvariants();

        this.x = x;
        this.y = y;
        this.z = z;

        assertClassInvariants();
    }

    @Override
    protected void assertClassInvariants() {
        try {
            assertIsValidDouble(x);
            assertIsValidDouble(y);
            assertIsValidDouble(z);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("The object is in an illegal state. Double value is not a number");
        }
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();

        double phi = Math.atan2(Math.sqrt(x * x + y * y), z);
        double theta = Math.atan2(y, x);
        double radius = Math.sqrt(x * x + y * y + z * z);
        SphericCoordinate sphericCoordinate = new SphericCoordinate(phi, theta, radius);

        assertClassInvariants();

        return sphericCoordinate;
    }

    //#region GETTER

    /**
	 * @methodtype get
	 */
	public double getX() {
        assertClassInvariants();
		return x;
	}

    /**
	 * @methodtype get
	 */
	public double getY() {
        assertClassInvariants();
		return y;
	}

    /**
	 * @methodtype get
	 */
	public double getZ() {
        assertClassInvariants();
		return z;
	}

    //#endregion GETTER


    private void assertIsValidDouble(double d) throws IllegalArgumentException {
        if (Double.isNaN(d)) {
                throw new IllegalArgumentException("Value is not a number");
            }
    }
}
