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
	public static synchronized CatPhotoFactory getInstance() {
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

	/**
	 * Creates a new CatPhoto by calling the default constructor
	 * @return a new CatPhoto
	 */
    @Override
    public CatPhoto createPhoto() {
        return new CatPhoto();
    }

	/**
	 * Creates a new CatPhoto with a specific ID by calling the corresponding constructor
	 * @param id
	 * @return a new CatPhoto
	 */
    @Override
    public CatPhoto createPhoto(PhotoId id) {
        return new CatPhoto(id);
    }

	/**
	 * Creates a new CatPhoto from a database result set by calling the corresponding constructor
	 * @param rset the result set from a database
	 * @return a new CatPhoto
	 * @throws SQLException
	 */
    @Override
    public CatPhoto createPhoto(ResultSet rset) throws SQLException {
        return new CatPhoto(rset);
    }
}
