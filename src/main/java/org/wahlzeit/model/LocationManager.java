package org.wahlzeit.model;

import org.wahlzeit.annotations.PatternInstance;
import org.wahlzeit.model.coordinates.CoordinateType;
import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;
import org.wahlzeit.services.SysLog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@PatternInstance(
    patternName = "Singleton",
    participants = {
        "Location"
    }
)
public class LocationManager extends ObjectManager {

    protected static final LocationManager INSTANCE = new LocationManager();

    private static final int START_ID = 1;

    protected final Map<Integer, Location> locationCache = new HashMap<>();

    public static LocationManager getInstance() {
        return INSTANCE;
    }

    protected LocationManager() {
        Collection<Location> loadedLocations = new ArrayList<>();
        loadLocations(loadedLocations);
        for (Location location : loadedLocations) {
            locationCache.put(location.getId(), location);
        }
    }

    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException, IllegalArgumentException {
        assertIsNonNullArgument(rset, "ResultSet");

        return new Location(rset);
    }

    public Location getLocation(Integer id) throws IllegalArgumentException {
        assertIsGreaterOrEqualZero(id);

        Location location = locationCache.get(id);
        if (location == null) {
            try {
                PreparedStatement stmt = getReadingStatement("SELECT * FROM locations WHERE id = ?");
                location = (Location) readObject(stmt, id);
            } catch (SQLException sex) {
                SysLog.logThrowable(sex);
            }
        }

        return location;
    }

    public void addLocation(Location location) throws IllegalArgumentException {
        assertIsNonNullArgument(location, "Location");

        locationCache.put(location.getId(), location);
        try {
            PreparedStatement stmt = getReadingStatement("INSERT INTO locations(id) VALUES(?)");
            createObject(location, stmt, location.getId());
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    public Location createLocation(double x, double y, double z) throws IllegalArgumentException {
        assertIsValidDouble(x);
        assertIsValidDouble(y);
        assertIsValidDouble(z);

		int maxId = START_ID;
        for (Map.Entry<Integer, Location> entry : locationCache.entrySet()) {
            if (entry.getKey() > maxId) {
                maxId = entry.getKey();
            }
        }
		Location location = new Location(maxId + 1, x, y, z, CoordinateType.CARTESIAN);
		addLocation(location);
		return location;
	}

    private void loadLocations(Collection<Location> result) {
        try {
            PreparedStatement stmt = getReadingStatement("SELECT * FROM locations");
            readObjects(result, stmt);
        } catch (SQLException ex) {
            SysLog.logThrowable(ex);
        }

        SysLog.logSysInfo("loaded all locations");
    }
}