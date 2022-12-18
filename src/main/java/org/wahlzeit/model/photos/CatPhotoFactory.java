package org.wahlzeit.model.photos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.services.SysLog;

public class CatPhotoFactory extends PhotoFactory {

    /**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	private static CatPhotoFactory instance = null;

    /**
     * @methodtype constructor
     */
    public CatPhotoFactory() {
        // do nothing
    }
	
	/**
	 * Public singleton access method.
	 */
	public static synchronized PhotoFactory getInstance() {
		if (instance == null) {
			SysLog.logSysInfo("Setting generic CatPhotoFactory");
			setInstance(new CatPhotoFactory());
		}
		
		return instance;
	}
	
	/**
	 * Method to set the singleton instance of PhotoFactory.
	 */
	protected static synchronized void setInstance(CatPhotoFactory catPhotoFactory) throws IllegalArgumentException {
		if (instance != null) {
			throw new IllegalStateException("Attempt to initialize CatPhotoFactory twice");
		}
		
		instance = catPhotoFactory;
	}
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		getInstance(); // drops result due to getInstance() side-effects
	}

    @Override
    public CatPhoto createPhoto() {
        return new CatPhoto();
    }

    @Override
    public CatPhoto createPhoto(PhotoId id) {
        return new CatPhoto(id);
    }

    @Override
    public CatPhoto createPhoto(ResultSet rs) throws SQLException {
        return new CatPhoto(rs);
    }
}
