package org.wahlzeit.model.coordinates;

public class SphericCoordinate extends AbstractCoordinate {
    
    private final double phi;     // latitude
    private final double theta;   // longitude
    private final double radius;

    public SphericCoordinate() {
        this.phi = 0;
        this.theta = 0;
        this.radius = 0;
    }

    /**
     * Radian-Values
     */
    public SphericCoordinate(double phi, double theta, double radius) {
        assertClassInvariants();
        assertIsValidPhi(phi);
        assertIsValidTheta(theta);
        assertIsValidRadius(radius);

        this.phi = phi;
        this.theta = theta;
        this.radius = radius;

        assertClassInvariants();
    }

    @Override
    public void assertClassInvariants() throws IllegalStateException {
        try {
            assertIsValidPhi(this.phi);
            assertIsValidTheta(this.theta);
            assertIsValidRadius(this.radius);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("The object is in an illegal state");
        }
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();

        double[] x_y_z = SharedCoordinate.calculateCartesianCoordinateFromSpherical(getPhi(), getTheta(), getRadius());

        CartesianCoordinate cartesianCoordinate = 
            (CartesianCoordinate) SharedCoordinate.getInstance()
            .getCoordinate(x_y_z[0], x_y_z[1], x_y_z[2], CoordinateType.CARTESIAN);
    
        assertClassInvariants();

        return cartesianCoordinate;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        return this;
    }

    @Override
    public CoordinateType getCoordinateType() {
        return CoordinateType.SPHERICAL;
    }

    @Override
    public Object clone() {
        return new SphericCoordinate(getPhi(), getTheta(), getRadius());
    }

    //#region GETTER

    public double getPhi() {
        assertClassInvariants();
        return phi;
    }

    public double getTheta() {
        assertClassInvariants();
        return theta;
    }

    public double getRadius() {
        assertClassInvariants();
        return radius;
    }

    //#endregion GETTER


    //#region SETTER

    /**
     * @Precondition: 0 <= phi <= PI
     * @Postcondition: 0 <= phi <= PI
     */
    private SphericCoordinate setPhi(double phi) {
        assertClassInvariants();
        assertIsValidPhi(phi);

        return SharedCoordinate.getInstance().getCoordinate(phi, getTheta(), getRadius(), getCoordinateType()).asSphericCoordinate();
    }

    /**
     * @Precondition: 0 <= theta < 2*PI
     * @Postcondition: 0 <= theta < 2*PI
     */
    private SphericCoordinate setTheta(double theta) {
        assertClassInvariants();
        assertIsValidTheta(theta);

        return SharedCoordinate.getInstance().getCoordinate(getPhi(), theta, getRadius(), getCoordinateType()).asSphericCoordinate();
    }

    /**
     * @Precondition: radius >= 0
     * @Postcondition: radius >= 0
     */
    private SphericCoordinate setRadius(double radius) {
        assertClassInvariants();
        assertIsValidRadius(radius);

        return SharedCoordinate.getInstance().getCoordinate(getPhi(), getTheta(), radius, getCoordinateType()).asSphericCoordinate();
    }

    //#endregion SETTER


    protected static void assertIsValidPhi(double phi) throws IllegalArgumentException {
        if (Double.isNaN(phi) ||
            Double.compare(phi, 0.0d) < 0 ||
            Double.compare(phi, Math.PI) > 0) {
                throw new IllegalArgumentException("Phi is not valid");
            }
    }

    protected static void assertIsValidTheta(double theta) throws IllegalArgumentException {
        if (Double.isNaN(theta) ||
            Double.compare(theta, 0.0d) < 0 ||
            Double.compare(theta, 2 * Math.PI) > 0 ||
            Double.compare(theta, 2 * Math.PI) == 0) {
                throw new IllegalArgumentException("Theta is not valid");
            }
    }

    protected static void assertIsValidRadius(double radius) throws IllegalArgumentException {
        if (Double.isNaN(radius) ||
            Double.compare(radius, 0.0d) < 0) {
            throw new IllegalArgumentException("Radius is not valid");
        }
    }
}
