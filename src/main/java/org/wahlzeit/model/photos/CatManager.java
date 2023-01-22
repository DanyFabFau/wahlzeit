package org.wahlzeit.model.photos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;
import org.wahlzeit.services.SysLog;

public class CatManager extends ObjectManager {

    protected static final CatManager INSTANCE = new CatManager();

    private static final int START_ID = 1;

    protected Map<Integer, Cat> catCache = new HashMap<>();
    protected Map<Integer, CatType> catTypeCache = new HashMap<>();

    protected CatManager() {
        Collection<Cat> loadedCats = new ArrayList<>();
        Collection<CatType> loadedCatTypes = new ArrayList<>();
        loadCats(loadedCats);
        loadCatTypes(loadedCatTypes);
        for (Cat cat : loadedCats) {
            catCache.put(cat.getId(), cat);
        }
        for (CatType catType : loadedCatTypes) {
            catTypeCache.put(catType.getId(), catType);
        }
    }

    public static CatManager getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a Cat based on a result set from a database by calling the corresponding constructor
     * @param rset the result set from a database
     * @return a new Cat from a database result set
     * @throws SQLException
     */
    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException {
        assertIsNonNullArgument(rset, "ResultSet");
        return new Cat(rset);
    }

    public Cat getCat(Integer id) {
        assertIsGreaterOrEqualZero(id);

        Cat cat = catCache.get(id);
        if (cat == null) {
            try {
                PreparedStatement stmt = getReadingStatement("SELECT * FROM cats WHERE id = ?");
                cat = (Cat) readObject(stmt, id);
            } catch (SQLException sex) {
                SysLog.logThrowable(sex);
            }
        }

        return cat;
    }

    public CatType getCatType(Integer id) {
        assertIsGreaterOrEqualZero(id);

        CatType catType = catTypeCache.get(id);
        if (catType == null) {
            try {
                PreparedStatement stmt = getReadingStatement("SELECT * FROM cat_types WHERE id = ?");
                catType = (CatType) readObject(stmt, id);
            } catch (SQLException sex) {
                SysLog.logThrowable(sex);
            }
        }

        return catType;
    }

    public void addCat(Cat cat) throws IllegalArgumentException {
        assertIsNonNullArgument(cat, "Cat");

        catCache.put(cat.getId(), cat);
        try {
            PreparedStatement stmt = getReadingStatement("INSERT INTO cats(id) VALUES(?)");
            createObject(cat, stmt, cat.getId());
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    public void addCatType(CatType catType) throws IllegalArgumentException {
        assertIsNonNullArgument(catType, "CatType");

        catTypeCache.put(catType.getId(), catType);
        try {
            PreparedStatement stmt = getReadingStatement("INSERT INTO cat_types(id) VALUES(?)");
            createObject(catType, stmt, catType.getId());
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    /**
     * Creates an instance of a Cat based on the CatTypeName by first searching for it in the database using a private method.
     * Calls the createInstance method of the corresponding CatType.
     * Inserts the Cat instance into the database.
     * @param catTypeName the name of the CatType
     * @return a new Cat with the given CatType
     * @throws IllegalArgumentException
     */
    public Cat createCat(String catTypeName) throws IllegalArgumentException {
        assertIsNonNullArgument(catTypeName, "CatTypeName");

        CatType catType = getCatType(catTypeName);
		int maxId = START_ID;
        for (Map.Entry<Integer, Cat> entry : catCache.entrySet()) {
            if (entry.getKey() > maxId) {
                maxId = entry.getKey();
            }
        }
		Cat cat = catType.createInstance(maxId + 1);
		addCat(cat);
		return cat;
	}

    private void loadCats(Collection<Cat> result) {
        try {
            PreparedStatement stmt = getReadingStatement("SELECT * FROM cats");
            readObjects(result, stmt);
        } catch (SQLException ex) {
            SysLog.logThrowable(ex);
        }

        SysLog.logSysInfo("loaded all cats");
    }

    private void loadCatTypes(Collection<CatType> result) {
        try {
            PreparedStatement stmt = getReadingStatement("SELECT * FROM cat_types");
            readObjects(result, stmt);
        } catch (SQLException ex) {
            SysLog.logThrowable(ex);
        }

        SysLog.logSysInfo("loaded all cats");
    }

    /**
     * Private helper method which tries to find the given CatTypeName in the cached database.
     * If the CatType exists in the database, it will be returned.
     * Otherwise, a new CatType will be created.
     * @param catTypeName
     * @return an existing/new CatType
     */
    private CatType getCatType(String catTypeName) {
        CatType catType = catTypeCache.values().stream().filter(ct -> ct.getName().equals(catTypeName)).findFirst().orElse(null);

        if (catType == null) {
            int maxId = START_ID;
            for (Map.Entry<Integer, CatType> entry : catTypeCache.entrySet()) {
                if (entry.getKey() > maxId) {
                    maxId = entry.getKey();
                }
            }
            catType = new CatType(maxId + 1, catTypeName);
            addCatType(catType);
        }
        return catType;
    }
}
