package org.wahlzeit.model;

import java.util.Objects;

public abstract class AbstractCoordinate implements Coordinate {
    
    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return calculateEuclidianDistance(coordinate.asCartesianCoordinate());
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        SphericCoordinate sc_1 = this.asSphericCoordinate();
        SphericCoordinate sc_2 = coordinate.asSphericCoordinate();

        double phi_1 = asDegree(sc_1.getPhi());
        double phi_2 = asDegree(sc_2.asSphericCoordinate().getPhi());
        double lambdaDiff = absDiffDeg(asDegree(sc_1.getTheta()), asDegree(sc_2.asSphericCoordinate().getTheta()));

        double sum_1 = Math.sin(phi_1) * Math.sin(phi_2);
        double sum_2 = Math.cos(phi_1) * Math.cos(phi_2) * Math.cos(lambdaDiff);

        double result = Math.acos(sum_1 + sum_2);

        return asDegree(result);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return isEqualCartesian(coordinate.asCartesianCoordinate());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coordinate && isEqual((Coordinate) obj);
    }

    @Override
    public int hashCode() {
        CartesianCoordinate cartesianCoordinate = asCartesianCoordinate();
        return Objects.hash(cartesianCoordinate.getX(), cartesianCoordinate.getY(), cartesianCoordinate.getZ());
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

    protected double asDegree(double rad) {
        return Math.toDegrees(rad);
    }

    protected double absDiffDeg(double angle_1, double angle_2) {
        double normDeg = Math.abs(angle_1 - angle_2) % 360;
        return Math.min(360 - normDeg, normDeg);
    }
}
