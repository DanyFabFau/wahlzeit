package org.wahlzeit.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.services.DataObject;

public class Location extends DataObject {
    
    private Integer id = null;

    /**
     * Location has always exactly one coordinate
     */
    private Coordinate coordinate;

    /**
     * @methodtype constructor
     */
    public Location(double x, double y, double z) {
        coordinate = new Coordinate(x, y, z);
        incWriteCount();
    }

    /**
     * @methodtype constructor
     */
    public Location(Integer id, double x, double y, double z) {
        this.id = id;
        coordinate = new Coordinate(x, y, z);
        incWriteCount();
    }

    /**
     * @methodtype constructor
     */
    public Location(ResultSet rset) throws SQLException {
        readFrom(rset);
        incWriteCount();
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
        incWriteCount();
    }

    /**
	 * @methodtype get
	 */
	public Integer getId() {
		return id;
	}

    @Override
    public String getIdAsString() {
        return Integer.toString(id);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = rset.getInt("id");
        coordinate = new Coordinate(
            rset.getDouble("coordinate_x"),
            rset.getDouble("coordinate_y"),
            rset.getDouble("coordinate_z")
        );
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id);
        rset.updateDouble("coordinate_x", coordinate.getX());
        rset.updateDouble("coordinate_y", coordinate.getY());
        rset.updateDouble("coordinate_z", coordinate.getZ());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        stmt.setInt(pos, id);
    }
}
