package org.wahlzeit.model.coordinates;

public interface Coordinate {

    CartesianCoordinate asCartesianCoordinate();
    SphericCoordinate asSphericCoordinate();

    double getCartesianDistance(Coordinate coordinate);
    double getCentralAngle(Coordinate coordinate);
    
    boolean isEqual(Coordinate coordinate);

    CoordinateType getCoordinateType();
}
