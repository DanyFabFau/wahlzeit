package org.wahlzeit.model.coordinates;

public class CartesianCoordinate extends AbstractCoordinate {
    
    private final double x;
    private final double y;
    private final double z;

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
            throw new IllegalStateException("The object is in an illegal state");
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

        double[] phi_theta_radius = SharedCoordinate.calculateSphericalCoordinateFromCartesian(getX(), getY(), getZ());

        SphericCoordinate sphericCoordinate = 
            (SphericCoordinate) SharedCoordinate.getInstance()
            .getCoordinate(phi_theta_radius[0], phi_theta_radius[1], phi_theta_radius[2], CoordinateType.SPHERICAL);
        
        assertClassInvariants();

        return sphericCoordinate;
    }

    @Override
    public CoordinateType getCoordinateType() {
        return CoordinateType.CARTESIAN;
    }

    @Override
    public Object clone() {
        return new CartesianCoordinate(getX(), getY(), getZ());
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


    //#region SETTER

    /**
	 * @methodtype set
	 */
	public CartesianCoordinate setX(double x) {
        assertClassInvariants();
        assertIsValidDouble(x);

		return SharedCoordinate.getInstance().getCoordinate(x, getY(), getZ(), getCoordinateType()).asCartesianCoordinate();
	}

    /**
	 * @methodtype set
	 */
	public CartesianCoordinate setY(double y) {
        assertClassInvariants();
        assertIsValidDouble(y);

		return SharedCoordinate.getInstance().getCoordinate(getX(), y, getZ(), getCoordinateType()).asCartesianCoordinate();
	}

    /**
	 * @methodtype set
	 */
	public CartesianCoordinate setZ(double z) {
        assertClassInvariants();
        assertIsValidDouble(z);

		return SharedCoordinate.getInstance().getCoordinate(getX(), getY(), z, getCoordinateType()).asCartesianCoordinate();
	}

    //#endregion SETTER


    private void assertIsValidDouble(double d) throws IllegalArgumentException {
        if (Double.isNaN(d)) {
                throw new IllegalArgumentException("Value is not a number");
            }
    }
}
