package org.wahlzeit.model;

import java.util.Objects;

public abstract class AbstractCoordinate implements Coordinate {
    
    protected abstract void assertClassInvariants() throws IllegalStateException;

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        assertClassInvariants();
        assertIsNonNullCoordinate(coordinate);

        double distance = calculateEuclidianDistance(coordinate.asCartesianCoordinate());

        assertIsGreaterOrEqualZero(distance);
        assertClassInvariants();

        return distance;
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        assertClassInvariants();
        assertIsNonNullCoordinate(coordinate);

        SphericCoordinate sc_1 = this.asSphericCoordinate();
        SphericCoordinate sc_2 = coordinate.asSphericCoordinate();

        double phi_1 = asDegree(sc_1.getPhi());
        double phi_2 = asDegree(sc_2.asSphericCoordinate().getPhi());
        double lambdaDiff = absDiffDeg(asDegree(sc_1.getTheta()), asDegree(sc_2.asSphericCoordinate().getTheta()));

        double sum_1 = Math.sin(phi_1) * Math.sin(phi_2);
        double sum_2 = Math.cos(phi_1) * Math.cos(phi_2) * Math.cos(lambdaDiff);

        double result = Math.acos(sum_1 + sum_2);

        assertIsGreaterOrEqualZero(result);
        assertClassInvariants();

        return asDegree(result);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        assertClassInvariants();
        assertIsNonNullCoordinate(coordinate);

        boolean res = isEqualCartesian(coordinate.asCartesianCoordinate());

        assertClassInvariants();

        return res;
    }

    @Override
    public boolean equals(Object obj) {
        assertClassInvariants();

        boolean res = obj instanceof Coordinate && isEqual((Coordinate) obj);

        assertClassInvariants();

        return res;
    }

    @Override
    public int hashCode() {
        assertClassInvariants();

        CartesianCoordinate cartesianCoordinate = asCartesianCoordinate();
        int hashCode =  Objects.hash(cartesianCoordinate.getX(), cartesianCoordinate.getY(), cartesianCoordinate.getZ());
        
        assertClassInvariants();

        return hashCode;
    }

    protected double asDegree(double rad) {
        assertClassInvariants();

        double deg = Math.toDegrees(rad);

        assertClassInvariants();

        return deg;
    }

    protected double absDiffDeg(double angle_1, double angle_2) {
        assertClassInvariants();

        double normDeg = Math.abs(angle_1 - angle_2) % 360;
        double res = Math.min(360 - normDeg, normDeg);

        assertIsGreaterOrEqualZero(res);
        assertClassInvariants();

        return res;
    }

    /**
     * Calculates the euclidian distance to a given coordinate
     * @param CartesianCoordinate
     * @return euclidian distance
     */
    private double calculateEuclidianDistance(CartesianCoordinate cartesianCoordinate) {
        CartesianCoordinate thisCartesianCoordinate = this.asCartesianCoordinate();

        double distX = cartesianCoordinate.getX() - thisCartesianCoordinate.getX();
        double distY = cartesianCoordinate.getY() - thisCartesianCoordinate.getY();
        double distZ = cartesianCoordinate.getZ() - thisCartesianCoordinate.getZ();

        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2) + Math.pow(distZ, 2));
    }

    private boolean isEqualCartesian(CartesianCoordinate cartesianCoordinate) {
        CartesianCoordinate thisCartesianCoordinate = this.asCartesianCoordinate();

        return Double.compare(thisCartesianCoordinate.getX(), cartesianCoordinate.getX()) == 0 &&
               Double.compare(thisCartesianCoordinate.getY(), cartesianCoordinate.getY()) == 0 &&
               Double.compare(thisCartesianCoordinate.getZ(), cartesianCoordinate.getZ()) == 0;
    }

    private void assertIsNonNullCoordinate(Coordinate coordinate) throws IllegalStateException {
        if (coordinate == null) {
            throw new IllegalArgumentException("Coordinate cannot be null");
        }
    }

    private void assertIsGreaterOrEqualZero(double d) throws ArithmeticException {
        if (d < 0) {
            throw new ArithmeticException("Result cannot be smaller than zero");
        }
    }
}
