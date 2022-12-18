package org.wahlzeit.model.coordinates;

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
        int hashCode = newCoordinate.hashCode();

        if (newCoordinate.getCoordinateType() == CoordinateType.CARTESIAN) {
            Coordinate cartesianFromCache = cartesianCache.get(hashCode);

            if (cartesianFromCache == null) {
                synchronized(this) {
                    double[] phi_theta_radius = calculateSphericalCoordinateFromCartesian(d_1, d_2, d_3);
                    Coordinate spheric = new SphericCoordinate(phi_theta_radius[0], phi_theta_radius[1], phi_theta_radius[2]);
                    cartesianCache.put(hashCode, newCoordinate);
                    sphericalCache.put(hashCode, spheric);
                    return newCoordinate;
                }
            }
            return cartesianFromCache;
        }

        // else: coordinateType == CoordinateType.SPHERICAL
        Coordinate sphericalFromCache = sphericalCache.get(hashCode);

        if (sphericalFromCache == null) {
            synchronized (this) {
                double[] x_y_z = calculateCartesianCoordinateFromSpherical(d_1, d_2, d_3);
                Coordinate cartesian = new CartesianCoordinate(x_y_z[0], x_y_z[1], x_y_z[2]);
                sphericalCache.put(hashCode, newCoordinate);
                cartesianCache.put(hashCode, cartesian);
                return newCoordinate;
            }
        }
        return sphericalFromCache;
    }

    protected static double[] calculateCartesianCoordinateFromSpherical(double phi, double theta, double radius) {
        SphericCoordinate.assertIsValidPhi(phi);
        SphericCoordinate.assertIsValidTheta(theta);
        SphericCoordinate.assertIsValidRadius(radius);

        double x = radius * Math.sin(phi) * Math.cos(theta);
        double y = radius * Math.sin(phi) * Math.sin(theta);
        double z = radius * Math.cos(phi);

        return new double[] {x, y, z};
    }

    protected static double[] calculateSphericalCoordinateFromCartesian(double x, double y, double z) {
        double phi = Math.atan2(Math.sqrt(x * x + y * y), z);
        double theta = Math.atan2(y, x);
        double radius = Math.sqrt(x * x + y * y + z * z);

        SphericCoordinate.assertIsValidPhi(phi);
        SphericCoordinate.assertIsValidTheta(theta);
        SphericCoordinate.assertIsValidRadius(radius);

        return new double[] {phi, theta, radius};
    }

    private Coordinate createNewCoordinate(double d_1, double d_2, double d_3, CoordinateType coordinateType) {
        if (coordinateType == CoordinateType.SPHERICAL) {
            return new SphericCoordinate(d_1, d_2, d_3);
        }
        // since only 2 values are possible (for now), below is the 'else-case'
        return new CartesianCoordinate(d_1, d_2, d_3);
    }
}
