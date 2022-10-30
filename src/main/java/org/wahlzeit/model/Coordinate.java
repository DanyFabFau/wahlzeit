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

    /**
     * Calculates the euclidian distance to a given coordinate
     * @param coordinate
     * @return euclidian distance
     */
    public double getDistance(Coordinate coordinate) {
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
    public boolean isEqual(Coordinate coordinate) {
        return this.getX() == coordinate.getX() &&
               this.getY() == coordinate.getY() &&
               this.getZ() == coordinate.getZ();
    }

    @Override
    public boolean equals(Object object) {
        // order can be important -> if it's not an instance of Coordinate, false is returned instantly
        return object instanceof Coordinate &&
               this.isEqual((Coordinate) object);
    }
}
