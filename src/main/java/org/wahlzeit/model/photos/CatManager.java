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

    public Cat createCat(String catTypeName) throws IllegalArgumentException {
        assertIsNonNullArgument(catTypeName, "CatTypeName");

        CatType catType = getCatType(catTypeName);
		int maxId = START_ID;
        for (Map.Entry<Integer, Cat> entry : catCache.entrySet()) {
            if (entry.getKey() > maxId) {
                maxId = entry.getKey();
            }
        }
		Cat cat = catType.createInstance(maxId);
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

    private CatType getCatType(String catTypeName) {
        CatType catType = catTypeCache.values().stream().filter(ct -> ct.getName().equals(catTypeName)).findFirst().orElse(null);

        if (catType == null) {
            int maxId = START_ID;
            for (Map.Entry<Integer, CatType> entry : catTypeCache.entrySet()) {
                if (entry.getKey() > maxId) {
                    maxId = entry.getKey();
                }
            }
            catType = new CatType(maxId, catTypeName);
            addCatType(catType);
        }
        return catType;
    }
}
