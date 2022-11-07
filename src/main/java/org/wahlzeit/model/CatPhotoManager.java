package org.wahlzeit.model;

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
	
	public static boolean hasPhoto(String id) {
		return hasPhoto(PhotoId.getIdFromString(id));
	}

	public static boolean hasPhoto(PhotoId id) {
		return getPhoto(id) != null;
	}
	
	public static Photo getPhoto(String id) {
		return getPhoto(PhotoId.getIdFromString(id));
	}
	
	public static Photo getPhoto(PhotoId id) {
		return instance.getPhotoFromId(id);
	}

    @Override
    protected Photo createObject(ResultSet rset) throws SQLException {
        return CatPhotoFactory.getInstance().createPhoto(rset);
    }

}
