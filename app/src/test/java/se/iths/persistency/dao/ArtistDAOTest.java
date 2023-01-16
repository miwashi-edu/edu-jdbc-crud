package se.iths.persistency.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import se.iths.persistency.model.Artist;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static se.iths.persistency.dao.DAOUtil.execute;

public class ArtistDAOTest {

    ArtistDAO artistDAO = new ArtistDAO();
    @AfterAll
    public static void tearDown(){
        execute("DELETE FROM Artist WHERE ArtistId > 275");
    }

    @Disabled("Not now!")
    @Test
    public void shouldFindAllArtists(){
        //Given

        //When
        Collection<Artist> Artists = artistDAO.findAll();
        //Then
        assertNotNull(Artists, "Artists must not be null after find all!");
        assertTrue(Artists.size() > 0 , "Artists must exist after find all!");
    }

    @Disabled("Not now!")
    @Test
    public void shouldFindArtistById(){
        //Given
        Long ArtistId = artistDAO.create(new Artist("A name")).getId();

        //When
        Artist Artist = artistDAO.findById(ArtistId);

        //Then
        assertNotNull(Artist);
    }

    /**
     * Test finding an Artist with an id that doesn't exist.
     */
    @Test
    public void shouldNotFindArtistByFaultyId(){
        //Given
        Long nonExistingId = -1L;

        //When
        Artist Artist = artistDAO.findById(nonExistingId);

        //Then
        assertNull(Artist, "Artists must not be found with faulty id!");
    }

    @Disabled("Not now!")
    @Test
    public void shouldCreateArtist(){
        //Given
        Artist Artist = new Artist("A Name");

        //When
        Artist persistentArtistm = artistDAO.create(Artist);

        //Then
        assertNotNull( persistentArtistm.getId(), "Artist id must not be null after create!");
        assertTrue(persistentArtistm.getId() >0, "Artist id must be greater than 0 after create!");
    }

    @Disabled("Not now!")
    @Test
    public void shouldUpdateArtist(){
        //Given
        Long existingId = 1L;
        String oldName = "An old Name";
        String newName = "A new Name";
        Artist Artist = artistDAO.create(new Artist(oldName));

        //When
        Artist.setName(newName);
        Artist = artistDAO.update(Artist);

        //Then
        assertEquals(newName, Artist.getName());
    }

    @Disabled("Not now!")
    @Test
    public void shouldDeleteArtist(){
        //Given
        Artist Artist = artistDAO.create(new Artist("A Name"));

        //When
        artistDAO.delete(Artist);

        //Then
        assertNull(artistDAO.findById(Artist.getId()), "Artist must not exist after delete");
    }
}