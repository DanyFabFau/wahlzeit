package org.wahlzeit.model;

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

public class LocationManager extends ObjectManager {

    protected static final LocationManager instance = new LocationManager();

    public static LocationManager getInstance() {
        return instance;
    }

    protected Map<Integer, Location> locationCache = new HashMap<>();

    protected LocationManager() {
        // do nothing
    }

    @Override
    protected Persistent createObject(ResultSet rset) throws SQLException {
        return new Location(rset);
    }

    public Location getLocation(Integer id) {
        // TODO wofür genau wird hier in PhotoManager primitive methods verwendet? 
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

    public void addLocation(Location location) {
        locationCache.put(location.getId(), location);
        try {
            PreparedStatement stmt = getReadingStatement("INSERT INTO locations(id) VALUES(?)");
            createObject(location, stmt, location.getId());
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
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

    //public Photo createLocation(double x, double y, double z) throws Exception {
	//	LocationId id = 
	//	Location location = new Location();
	//	addLocation(location);
	//	return location;
	//}
}