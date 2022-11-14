package org.wahlzeit.model;

import java.util.Objects;

public class SphericCoordinate implements Coordinate {
    
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

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return asCartesianCoordinate().getCartesianDistance(coordinate);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        double phi_1 = asDegree(this.getPhi());
        double phi_2 = asDegree(coordinate.asSphericCoordinate().getPhi());
        double lambdaDiff = absDiffDeg(asDegree(this.getTheta()), asDegree(coordinate.asSphericCoordinate().getTheta()));

        double sum_1 = Math.sin(phi_1) * Math.sin(phi_2);
        double sum_2 = Math.cos(phi_1) * Math.cos(phi_2) * Math.cos(lambdaDiff);

        double result = Math.acos(sum_1 + sum_2);

        return asDegree(result);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return isEqualSpheric(coordinate.asSphericCoordinate());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof SphericCoordinate &&
               isEqual((SphericCoordinate) object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phi, theta, radius);
    }

    private boolean isEqualSpheric(SphericCoordinate coordinate) {
        return Double.compare(this.getPhi(), coordinate.getPhi()) == 0 &&
               Double.compare(this.getTheta(), coordinate.getTheta()) == 0 &&
               Double.compare(this.getRadius(), coordinate.getRadius()) == 0;
    }

    protected double asDegree(double rad) {
        return Math.toDegrees(rad);
    }

    protected double absDiffDeg(double angle_1, double angle_2) {
        double normDeg = Math.abs(angle_1 - angle_2) % 360;
        return Math.min(360 - normDeg, normDeg);
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
