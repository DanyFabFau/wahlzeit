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
	
	public static CatPhoto getPhoto(String id) throws IllegalArgumentException {
		assertIsNotNull(id, "ID");
		return getPhoto(PhotoId.getIdFromString(id));
	}
	
	public static CatPhoto getPhoto(PhotoId id) throws IllegalArgumentException {
		assertIsNotNull(id, "ID");
		return (CatPhoto) getInstance().getPhotoFromId(id);
	}

	/**
	 * Creates a CatPhoto based on a result set from a database by calling the creation method of the CatPhotoFactory
	 * @param rset the result set from a database
	 * @return an instance of CatPhoto with all the attributes from the result set
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
    @Override
    protected CatPhoto createObject(ResultSet rset) throws SQLException, IllegalArgumentException {
		assertIsNotNull(rset, "ID");
        return CatPhotoFactory.getInstance().createPhoto(rset);
    }

	private static void assertIsNotNull(Object o, String label) throws IllegalArgumentException {
		if (o == null) {
			throw new IllegalArgumentException(label + " cannot be null");
		}
	}
}
