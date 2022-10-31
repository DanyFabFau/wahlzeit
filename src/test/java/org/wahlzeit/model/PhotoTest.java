package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PhotoTest {

    private Photo photo;
    
    @Before
    public void initPhoto() {
        photo = new Photo();
    }

    @Test
    public void testLocationProperty() {
        assertNotNull(photo);
        assertNull(photo.location);
        
        photo.location = new Location(0, 0, 0);
        assertNotNull(photo.location);
    }
}
