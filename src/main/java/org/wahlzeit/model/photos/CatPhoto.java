package org.wahlzeit.model.photos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.wahlzeit.model.LocationManager;
import org.wahlzeit.model.Tags;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.utils.StringUtil;

public class CatPhoto extends Photo {
    
    protected String catName;
    protected String catBreed;
    protected String furColor;
    protected String furPattern;
    protected Cat cat;

    /**
     * Constructor for creating a new default CatPhoto.
     * Calls the super constructor of its super type Photo.
     * @throws SQLException
     */
    public CatPhoto() {
        super();
    }

    /**
     * Constructor for creating a new CatPhoto with a specific ID.
     * Calls the super constructor of its super type Photo.
     * @param id
     */
    public CatPhoto(PhotoId id) {
        super(id);
    }

    /**
     * Constructor for creating a new CatPhoto from a database result set.
     * Calls the super constructor of its super type Photo.
     * @param rset the result set from a database
     * @throws SQLException
     */
    public CatPhoto(ResultSet rset) throws SQLException {
        super(rset);
    }

	public void readFrom(ResultSet rset) throws SQLException, IllegalArgumentException {
        assertIsNotNull(rset, "ResultSet");

		id = PhotoId.getIdFromInt(rset.getInt("id"));

		ownerId = rset.getInt("owner_id");
		ownerName = rset.getString("owner_name");
		
		ownerNotifyAboutPraise = rset.getBoolean("owner_notify_about_praise");
		ownerEmailAddress = EmailAddress.getFromString(rset.getString("owner_email_address"));
		ownerLanguage = Language.getFromInt(rset.getInt("owner_language"));
		ownerHomePage = StringUtil.asUrl(rset.getString("owner_home_page"));

		width = rset.getInt("width");
		height = rset.getInt("height");

		tags = new Tags(rset.getString("tags"));

		status = PhotoStatus.getFromInt(rset.getInt("status"));
		praiseSum = rset.getInt("praise_sum");
		noVotes = rset.getInt("no_votes");

		creationTime = rset.getLong("creation_time");

		maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);

		location = LocationManager.getInstance().getLocation(rset.getInt("location"));

        catName = rset.getString("cat_name");
        catBreed = rset.getString("cat_breed");
        furColor = rset.getString("fur_color");
        furPattern = rset.getString("fur_pattern");
        cat = CatManager.getInstance().getCat(rset.getInt("cat_id"));
	}

	public void writeOn(ResultSet rset) throws SQLException {
		rset.updateInt("id", id.asInt());
		rset.updateInt("owner_id", ownerId);
		rset.updateString("owner_name", ownerName);
		rset.updateBoolean("owner_notify_about_praise", ownerNotifyAboutPraise);
		rset.updateString("owner_email_address", ownerEmailAddress.asString());
		rset.updateInt("owner_language", ownerLanguage.asInt());
		rset.updateString("owner_home_page", ownerHomePage.toString());
		rset.updateInt("width", width);
		rset.updateInt("height", height);
		rset.updateString("tags", tags.asString());
		rset.updateInt("status", status.asInt());
		rset.updateInt("praise_sum", praiseSum);
		rset.updateInt("no_votes", noVotes);
		rset.updateLong("creation_time", creationTime);	
		rset.updateInt("location", location.getId());
        rset.updateString("cat_name", catName);
        rset.updateString("cat_breed", catBreed);
        rset.updateString("fur_color", furColor);
        rset.updateString("fur_pattern", furPattern);
        rset.updateInt("cat_id", cat.getId());
	}

    /**
     * @methodtype get
     */
    public String getCatName() {
        return catName;
    }

    /**
     * @methodtype get
     */
    public String getCatBreed() {
        return catBreed;
    }

    /**
     * @methodtype get
     */
    public String getFurColor() {
        return furColor;
    }

    /**
     * @methodtype get
     */
    public String getFurPattern() {
        return furPattern;
    }

    /**
     * @methodtype get
     */
    public Cat getCat() {
        return cat;
    }

    /**
     * @methodtyoe set
     */
    public void setCatName(String catName) {
        this.catName = catName;
        incWriteCount();
    }

    /**
     * @methodtyoe set
     */
    public void setCatBreed(String catBreed) {
        this.catBreed = catBreed;
        incWriteCount();
    }

    /**
     * @methodtyoe set
     */
    public void setFurColor(String furColor) {
        this.furColor = furColor;
        incWriteCount();
    }

    /**
     * @methodtyoe set
     */
    public void setFurPattern(String furPattern) {
        this.furPattern = furPattern;
        incWriteCount();
    }

    /**
     * @methodtyoe set
     */
    public void setCat(Cat cat) {
        this.cat = cat;
        incWriteCount();
    }
}
