package org.wahlzeit.model;

public class Location {
    
    /**
     * Location has always exactly one coordinate
     */
    private Coordinate coordinate;

    /**
     * @methodtype constructor
     */
    public Location() {
        coordinate = new Coordinate();
    }

    /**
     * @methodtype constructor
     */
    public Location(Coordinate newCoordinate) {
        coordinate = newCoordinate;
    }

    /**
	 * @methodtype get
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

    /**
     * @methodtype set
     */
    public void setCoordinate(Coordinate newCoordinate) {
        if (coordinate == null) {
            throw new IllegalStateException("coordinate cannot be null");
        }

        coordinate = newCoordinate;
    }
}
