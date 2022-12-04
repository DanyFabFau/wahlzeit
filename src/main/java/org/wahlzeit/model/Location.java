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
    public Location(double x, double y, double z) throws IllegalArgumentException {
        assertIsValidDouble(x);
        assertIsValidDouble(y);
        assertIsValidDouble(z);
        
        coordinate = new CartesianCoordinate(x, y, z);
        incWriteCount();
    }

    /**
     * @methodtype constructor
     */
    public Location(Integer id, double x, double y, double z) throws IllegalArgumentException {
        assertIsGreaterOrEqualZero(id);
        assertIsValidDouble(x);
        assertIsValidDouble(y);
        assertIsValidDouble(z);

        this.id = id;
        coordinate = new CartesianCoordinate(x, y, z);
        incWriteCount();
    }

    /**
     * @methodtype constructor
     */
    public Location(ResultSet rset) throws SQLException, IllegalArgumentException {
        assertIsNotNull(rset, "ResultSet");

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
    public void setCoordinate(Coordinate newCoordinate) throws IllegalArgumentException {
        assertIsNotNull(newCoordinate, "Coordinate");

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
    public void readFrom(ResultSet rset) throws SQLException, IllegalArgumentException {
        assertIsNotNull(rset, "ResultSet");

        id = rset.getInt("id");
        coordinate = new CartesianCoordinate(
            rset.getDouble("coordinate_x"),
            rset.getDouble("coordinate_y"),
            rset.getDouble("coordinate_z")
        );
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException, IllegalArgumentException {
        assertIsNotNull(rset, "ResultSet");

        rset.updateInt("id", id);
        rset.updateDouble("coordinate_x", coordinate.asCartesianCoordinate().getX());
        rset.updateDouble("coordinate_y", coordinate.asCartesianCoordinate().getY());
        rset.updateDouble("coordinate_z", coordinate.asCartesianCoordinate().getZ());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException, IllegalArgumentException {
        assertIsNotNull(stmt, "PreparedStatement");

        stmt.setInt(pos, id);
    }
}
