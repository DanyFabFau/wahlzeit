package org.wahlzeit.model;

public class SphericCoordinate extends AbstractCoordinate {
    
    private double phi;     // latitude
    private double theta;   // longitude
    private double radius;

    public SphericCoordinate() {
        this.phi = 0;
        this.theta = 0;
        this.radius = 0;
    }

    /**
     * Radian-Values
     */
    public SphericCoordinate(double phi, double theta, double radius) {
        setPhi(phi);
        setTheta(theta);
        setRadius(radius);
    }

    @Override
    public void assertClassInvariants() {
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

        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(
            radius * Math.sin(phi) * Math.cos(theta),
            radius * Math.sin(phi) * Math.sin(theta),
            radius * Math.cos(phi)
        );
        
        assertClassInvariants();

        return cartesianCoordinate;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        return this;
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
    private void setPhi(double phi) {
        assertClassInvariants();
        assertIsValidPhi(phi);

        this.phi = phi;
        
        assertClassInvariants();
    }

    /**
     * @Precondition: 0 <= theta < 2*PI
     * @Postcondition: 0 <= theta < 2*PI
     */
    private void setTheta(double theta) {
        assertClassInvariants();
        assertIsValidTheta(theta);

        this.theta = theta;
        
        assertClassInvariants();
    }

    /**
     * @Precondition: radius >= 0
     * @Postcondition: radius >= 0
     */
    private void setRadius(double radius) {
        assertClassInvariants();
        assertIsValidRadius(radius);

        this.radius = radius;

        assertClassInvariants();
    }

    //#endregion SETTER

    private void assertIsValidPhi(double phi) {
        if (Double.compare(phi, 0.0d) < 0 ||
            Double.compare(phi, Math.PI) > 0) {
                throw new IllegalArgumentException("Phi is not valid");
            }
    }

    private void assertIsValidTheta(double theta) {
        if (Double.compare(theta, 0.0d) < 0 ||
            Double.compare(theta, 2 * Math.PI) > 0 ||
            Double.compare(theta, 2 * Math.PI) == 0) {
                throw new IllegalArgumentException("Theta is not valid");
            }
    }

    private void assertIsValidRadius(double radius) {
        if (Double.compare(radius, 0.0d) < 0) {
            throw new IllegalArgumentException("Radius is not valid");
        }
    }
}
