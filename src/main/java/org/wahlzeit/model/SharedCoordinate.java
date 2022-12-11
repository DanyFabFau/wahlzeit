package org.wahlzeit.model;

import java.util.HashMap;
import java.util.Map;

public class SharedCoordinate {

    private static final SharedCoordinate INSTANCE = new SharedCoordinate();

    private final Map<Integer, Coordinate> cartesianCache = new HashMap<>();
    private final Map<Integer, Coordinate> sphericalCache = new HashMap<>();

    private SharedCoordinate() {
        // do nothing
        // private, so no new instance can be created from outside
    }
    
    public static SharedCoordinate getInstance() {
        return INSTANCE;
    }

    public Coordinate getCoordinate(double d_1, double d_2, double d_3, CoordinateType coordinateType) {
        Coordinate newCoordinate = createNewCoordinate(d_1, d_2, d_3, coordinateType);

        if (newCoordinate.getCoordinateType() == CoordinateType.CARTESIAN) {
            Coordinate cartesianFromCache = cartesianCache.get(newCoordinate.hashCode());

            if (cartesianFromCache == null) {
                synchronized(this) {
                    Coordinate spheric = createSphericalCoordinateFromCartesian(d_1, d_2, d_3);
                    cartesianCache.put(newCoordinate.hashCode(), newCoordinate);
                    sphericalCache.put(spheric.hashCode(), spheric);
                    return newCoordinate;
                }
            }
            return cartesianFromCache;
        }

        // else: coordinateType == CoordinateType.SPHERICAL
        Coordinate sphericalFromCache = sphericalCache.get(newCoordinate.hashCode());

        if (sphericalFromCache == null) {
            synchronized (this) {
                Coordinate cartesian = createCartesianCoordinateFromSpherical(d_1, d_2, d_3);
                sphericalCache.put(newCoordinate.hashCode(), newCoordinate);
                cartesianCache.put(cartesian.hashCode(), cartesian);
                return newCoordinate;
            }
        }
        return sphericalFromCache;
    }

    protected Coordinate createCartesianCoordinateFromSpherical(double phi, double theta, double radius) {
        SphericCoordinate.assertIsValidPhi(phi);
        SphericCoordinate.assertIsValidTheta(theta);
        SphericCoordinate.assertIsValidRadius(radius);

        double x = radius * Math.sin(phi) * Math.cos(theta);
        double y = radius * Math.sin(phi) * Math.sin(theta);
        double z = radius * Math.cos(phi);

        return new CartesianCoordinate(x, y, z);
    }

    protected Coordinate createSphericalCoordinateFromCartesian(double x, double y, double z) {
        double phi = Math.atan2(Math.sqrt(x * x + y * y), z);
        double theta = Math.atan2(y, x);
        double radius = Math.sqrt(x * x + y * y + z * z);

        return new SphericCoordinate(phi, theta, radius);
    }

    private Coordinate createNewCoordinate(double d_1, double d_2, double d_3, CoordinateType coordinateType) {
        if (coordinateType == CoordinateType.SPHERICAL) {
            return new SphericCoordinate(d_1, d_2, d_3);
        }
        // since only 2 values are possible (for now), below is the 'else-case'
        return new CartesianCoordinate(d_1, d_2, d_3);
    }
}
