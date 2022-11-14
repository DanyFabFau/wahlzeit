package org.wahlzeit.model;

import java.util.Objects;

public class CartesianCoordinate implements Coordinate {
    
    private double x;
    private double y;
    private double z;

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
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        double phi = Math.atan2(Math.sqrt(x * x + y * y), z);
        double theta = Math.atan2(y, x);
        double radius = Math.sqrt(x * x + y * y + z * z);

        return new SphericCoordinate(phi, theta, radius);
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        return getDistance(coordinate.asCartesianCoordinate());
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) {
        return asSphericCoordinate().getCentralAngle(coordinate);
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return isEqualCartesian(coordinate.asCartesianCoordinate());
    }

    @Override
    public boolean equals(Object object) {
        // order can be important -> if it's not an instance of Coordinate, false is returned instantly
        return object instanceof CartesianCoordinate &&
               isEqual((CartesianCoordinate) object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    /**
     * Calculates the euclidian distance to a given coordinate
     * @param coordinate
     * @return euclidian distance
     */
    private double getDistance(CartesianCoordinate coordinate) {
        double distX = coordinate.getX() - this.getX();
        double distY = coordinate.getY() - this.getY();
        double distZ = coordinate.getZ() - this.getZ();

        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2) + Math.pow(distZ, 2));
    }

    /**
     * Checks if this coordinate is equal to the given one
     * @param coordinate
     * @return true if all 3 values (x, y, z) are identical to the given coordinate
     */
    private boolean isEqualCartesian(CartesianCoordinate coordinate) {
        return Double.compare(this.getX(), coordinate.getX()) == 0 &&
               Double.compare(this.getY(), coordinate.getY()) == 0 &&
               Double.compare(this.getZ(), coordinate.getZ()) == 0;
    }


    //#region GETTER

    /**
	 * @methodtype get
	 */
	public double getX() {
		return x;
	}

    /**
	 * @methodtype get
	 */
	public double getY() {
		return y;
	}

    /**
	 * @methodtype get
	 */
	public double getZ() {
		return z;
	}

    //#endregion GETTER
}
