package org.wahlzeit.model.photos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.services.DataObject;

public class Cat extends DataObject {

    private Integer id = null;
    private CatType catType;

    protected Cat(Integer id, CatType catType) {
        this.id = id;
        this.catType = catType;
        incWriteCount();
    }

    protected Cat(ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    @Override
    public String getIdAsString() {
        return String.valueOf(id);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = rset.getInt("id");
        catType = CatManager.getInstance().getCatType(rset.getInt("cat_type"));
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id);
        rset.updateInt("cat_type", catType.getId());
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        stmt.setInt(pos, id);
    }

    /**
	 * @methodtype get
	 */
    public Integer getId() {
        return id;
    }

    /**
	 * @methodtype get
	 */
    public CatType getCatType() {
        return catType;
    }

    /**
	 * @methodtype set
	 */
    public void setId(Integer id) {
        this.id = id;
        incWriteCount();
    }
}
