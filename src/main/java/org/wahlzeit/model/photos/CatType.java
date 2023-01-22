package org.wahlzeit.model.photos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.wahlzeit.services.DataObject;

public class CatType extends DataObject {

    private Integer id;
    private String name;
    private CatType superType;
    private Set<CatType> subTypes;

    protected CatType(Integer id, String name) {
        this.name = name;
        this.id = id;
        superType = null;
        subTypes = new HashSet<>();
        incWriteCount();
    }

    /**
     * Creates a new Cat with a given ID and 'this' CatType by calling corresponding constructor
     * @param id
     * @return a new Cat
     */
    public Cat createInstance(Integer id) {
        return new Cat(id, this);
    }

    public boolean hasInstance(Cat cat) {
        assertIsNotNull(cat, "Cat");

        if (cat.getCatType() == this) {
            return true;
        }

        for (CatType subType : subTypes) {
            if (subType.hasInstance(cat)) {
                return true;
            }
        }

        return false;
    }

    public void addSubType(CatType catType) {
        assertIsNotNull(catType, "CatType");

        catType.setSuperType(this);
        subTypes.add(catType);
        // incWriteCount(); is already done by setSuperType()
    }

    @Override
    public String getIdAsString() {
        return String.valueOf(id);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = rset.getInt("id");
        name = rset.getString("name");
        Integer superTypeId = rset.getInt("supertype_id");
        CatType superType = CatManager.getInstance().getCatType(superTypeId);
        setSuperType(superType);
        superType.addSubType(this);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id);
        rset.updateString("name", name);
        rset.updateInt("supertype_id", superType.getId());
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
    public String getName() {
        return name;
    }

    /**
	 * @methodtype get
	 */
    public CatType getSuperType() {
        return superType;
    }

    /**
	 * @methodtype get
	 */
    public Iterator<CatType> getSubTypeIterator() {
        return subTypes.iterator();
    }

    /**
	 * @methodtype set
	 */
    public void setSuperType(CatType superType) {
        if (this.superType != null) {
            throw new IllegalStateException("A SuperType already exists");
        }

        this.superType = superType;
        incWriteCount();
    }
}
