package org.wahlzeit.model;

public class Coordinate {

    private double x;
    private double y;
    private double z;

    /**
     * @methodtype constructor
     */
    public Coordinate() {
        x = 0;
        y = 0;
        z = 0;
    }

    /**
     * @methodtype constructor
     */
    public Coordinate(double newX, double newY, double newZ) {
        x = newX;
        y = newY;
        z = newZ;
    }

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
}
