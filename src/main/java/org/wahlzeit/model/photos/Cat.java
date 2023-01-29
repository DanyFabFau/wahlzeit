package org.wahlzeit.model.photos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.services.DataObject;

/**
 *  public collaboration CatPhotoCat {
 *      public role CatPhoto {
 *          // Client
 *          // Owner of Cat
 *          Cat getCat();
 *          void setCat(Cat cat);
 *      }
 *      public role Cat {
 *          // Service
 *          // Provides functionality/information to CatPhoto
 *      }
 *  }
 *  // binds CatPhotoCat.Cat
 * 
 * 
 *  public collaboration CatCatType {
 *      public role Cat {
 *          // Base Object
 *          // Receives information from CatType
 *          CatType getCatType();
 *      }
 *      public role CatType {
 *          // Type Object
 *          // Provides functionality/information to Cat
 *          Cat createInstance(Integer id);
 *          boolean hasInstance(Cat cat);
 *      }
 *  }
 *  // binds CatCatType.Cat
 * 
 * 
 *  public collaboration CatManagerCat {
 *      public role CatManager {
 *          // Manager
 *          // Centralizes object management in one place
 *          Cat getCat(Integer id);
 *          void addCat(Cat cat);
 *          Cat createCat(String catTypeName);
 *          void loadCats(Collection<Cat> result);
 *      }
 *      public role Cat {
 *          // Element
 *          // Gets managed and stored by CatManager
 *          // No methods, acts like a client
 *      }
 *  }
 *  // binds CatManagerCat.Cat
 */

public class Cat extends DataObject {

    private Integer id = null;
    private CatType catType;

    /**
     * Constructor for creating a new Cat with a specific ID and CatType.
     * @param id
     * @param catType
     */
    protected Cat(Integer id, CatType catType) {
        this.id = id;
        this.catType = catType;
        incWriteCount();
    }

    /**
     * Constructor for creating a new Cat from a database result set by using the readFrom method.
     * @param rset the result set from a database
     * @throws SQLException
     */
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
