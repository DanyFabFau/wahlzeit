package org.wahlzeit.model.photos;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatPhotoManager extends PhotoManager {
    
    protected static final CatPhotoManager instance = new CatPhotoManager();

    public CatPhotoManager() {
        super();
        photoTagCollector = CatPhotoFactory.getInstance().createPhotoTagCollector();
    }

	public static CatPhotoManager getInstance() {
		return instance;
	}
	
	public static boolean hasPhoto(String id) throws IllegalArgumentException {
		assertIsNotNull(id, "ID");
		return hasPhoto(PhotoId.getIdFromString(id));
	}

	public static boolean hasPhoto(PhotoId id) throws IllegalArgumentException {
		assertIsNotNull(id, "ID");
		return getPhoto(id) != null;
	}
	
	public static Photo getPhoto(String id) throws IllegalArgumentException {
		assertIsNotNull(id, "ID");
		return getPhoto(PhotoId.getIdFromString(id));
	}
	
	public static Photo getPhoto(PhotoId id) throws IllegalArgumentException {
		assertIsNotNull(id, "ID");
		return instance.getPhotoFromId(id);
	}

    @Override
    protected Photo createObject(ResultSet rset) throws SQLException, IllegalArgumentException {
		assertIsNotNull(rset, "ID");
        return CatPhotoFactory.getInstance().createPhoto(rset);
    }

	private static void assertIsNotNull(Object o, String label) throws IllegalArgumentException {
		if (o == null) {
			throw new IllegalArgumentException(label + " cannot be null");
		}
	}
}
