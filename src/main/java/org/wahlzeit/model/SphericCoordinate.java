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
    public CartesianCoordinate asCartesianCoordinate() {
        return new CartesianCoordinate(
            radius * Math.sin(phi) * Math.cos(theta),
            radius * Math.sin(phi) * Math.sin(theta),
            radius * Math.cos(phi)
        );
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    //#region GETTER

    public double getPhi() {
        return phi;
    }

    public double getTheta() {
        return theta;
    }

    public double getRadius() {
        return radius;
    }

    //#endregion GETTER


    //#region SETTER

    private void setPhi(double phi) {
        if (Double.compare(phi, 0.0d) < 0 ||
            Double.compare(phi, Math.PI) > 0) {
                throw new IllegalArgumentException("phi is not valid");
            }

        // 0 <= phi <= PI
        this.phi = phi;
    }

    private void setTheta(double theta) {
        if (Double.compare(theta, 0.0d) < 0 ||
            Double.compare(theta, 2 * Math.PI) > 0 ||
            Double.compare(theta, 2 * Math.PI) == 0) {
                throw new IllegalArgumentException("theta is not valid");
            }

        // 0 <= theta < 2*PI
        this.theta = theta;
    }

    private void setRadius(double radius) {
        if (Double.compare(radius, 0.0d) < 0) throw new IllegalArgumentException("radius is not valid");

        // radius >= 0
        this.radius = radius;
    }

    //#endregion SETTER
}
