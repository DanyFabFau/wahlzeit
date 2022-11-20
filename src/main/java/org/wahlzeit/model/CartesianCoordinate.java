package org.wahlzeit.model;

public class CartesianCoordinate extends AbstractCoordinate {
    
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
