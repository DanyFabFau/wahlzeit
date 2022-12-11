package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatPhotoTest {

    private CatPhoto photo;
    private CatPhotoFactory factory;
    private CatPhotoManager manager;
    private ResultSet rset;
    
    @Before
    public void init() {
        photo = new CatPhoto();
        factory = new CatPhotoFactory();
        manager = new CatPhotoManager();
        rset = Mockito.mock(ResultSet.class);

    }

    @Test
    public void testLocationProperty() {
        assertNotNull(photo);
        assertNull(photo.location);
        
        photo.location = new Location(0, 0, 0, CoordinateType.CARTESIAN);
        assertNotNull(photo.location);
    }

    @Test
    public void testSerialization() throws SQLException {
        // photo.setCatName("Waffels");
        // photo.setCatBreed("Britisch Kurzhaar");
        // photo.setFurColor("grau");
        // photo.setFurPattern("einfarbig");

        // photo.writeOn(rset);
        
        // verify(rset, Mockito.times(1)).updateString("id", photo.getIdAsString());
        // verify(rset, Mockito.times(1)).updateInt("owner_id", photo.getOwnerId());
        // verify(rset, Mockito.times(1)).updateString("owner_name", photo.getOwnerName());
        // verify(rset, Mockito.times(1)).updateBoolean("owner_notify_about_praise", photo.getOwnerNotifyAboutPraise());
        // verify(rset, Mockito.times(1)).updateString("owner_email_address", photo.getOwnerEmailAddress().asString());
        // verify(rset, Mockito.times(1)).updateInt("owner_language", photo.getOwnerLanguage().asInt());
        // verify(rset, Mockito.times(1)).updateString("owner_home_page", photo.getOwnerHomePage().toString());
        // verify(rset, Mockito.times(1)).updateInt("width", photo.getWidth());
        // verify(rset, Mockito.times(1)).updateInt("height", photo.getHeight());
        // verify(rset, Mockito.times(1)).updateString("tags", photo.getTags().asString());
        // verify(rset, Mockito.times(1)).updateInt("status", photo.getStatus().asInt());
        // verify(rset, Mockito.times(1)).updateDouble("praise_sum", photo.getPraise());
        // verify(rset, Mockito.times(1)).updateLong("creation_time", photo.getCreationTime());

        // verify(rset, Mockito.times(1)).updateString("cat_name", photo.getCatName());
        // verify(rset, Mockito.times(1)).updateString("cat_breed", photo.getCatBreed());
        // verify(rset, Mockito.times(1)).updateString("fur_color", photo.getFurColor());
        // verify(rset, Mockito.times(1)).updateString("fur_pattern", photo.getFurPattern());
    }

    @Test
    public void testDeserialization() throws SQLException {
        // CatPhoto newPhoto = new CatPhoto();

        // newPhoto.readFrom(rset);

        // assertEquals("Waffels", newPhoto.getCatName());
        // assertEquals("Britisch Kurzhaar", newPhoto.getCatBreed());
        // assertEquals("grau", newPhoto.getFurColor());
        // assertEquals("einfarbig", newPhoto.getFurPattern());
    }
}
