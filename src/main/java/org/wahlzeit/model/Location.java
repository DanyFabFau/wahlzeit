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
    public Location(double d_1, double d_2, double d_3, CoordinateType coordinateType) throws IllegalArgumentException {
        assertIsValidDouble(d_1);
        assertIsValidDouble(d_2);
        assertIsValidDouble(d_3);
        
        coordinate = SharedCoordinate.getInstance().getCoordinate(d_1, d_2, d_3, coordinateType);
        incWriteCount();
    }

    /**
     * @methodtype constructor
     */
    public Location(Integer id, double d_1, double d_2, double d_3, CoordinateType coordinateType) throws IllegalArgumentException {
        assertIsGreaterOrEqualZero(id);
        assertIsValidDouble(d_1);
        assertIsValidDouble(d_2);
        assertIsValidDouble(d_3);

        this.id = id;
        coordinate = SharedCoordinate.getInstance().getCoordinate(d_1, d_2, d_3, coordinateType);
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
        coordinate = SharedCoordinate.getInstance().getCoordinate(
            rset.getDouble("coordinate_x"),
            rset.getDouble("coordinate_y"),
            rset.getDouble("coordinate_z"),
            CoordinateType.CARTESIAN
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
