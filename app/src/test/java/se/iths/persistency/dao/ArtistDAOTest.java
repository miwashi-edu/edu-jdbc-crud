package se.iths.persistency.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import se.iths.persistency.model.Album;
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

    @Test
    public void shouldFindAllArtists(){
        //Given

        //When
        Collection<Artist> Artists = artistDAO.findAll();
        //Then
        assertNotNull(Artists, "Artists must not be null after find all!");
        assertTrue(Artists.size() > 0 , "Artists must exist after find all!");
    }

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
        Artist artist = artistDAO.findById(nonExistingId);

        //Then
        assertNull(artist, "Artists must not be found with faulty id!");
    }

    @Test
    public void shouldCreateArtist(){
        //Given
        Artist artist = new Artist("A Name");

        //When
        Artist persistentArtistm = artistDAO.create(artist);

        //Then
        assertNotNull( persistentArtistm.getId(), "Artist id must not be null after create!");
        assertTrue(persistentArtistm.getId() >0, "Artist id must be greater than 0 after create!");
    }

    @Test
    public void shouldCreateArtistWithAlbums(){
        //Given
        Artist artist = new Artist("A Name");
        artist.add(new Album("Title 1"));
        artist.add(new Album("Title 2"));
        artist.add(new Album("Title 3"));

        //When
        Artist persistentArtistm = artistDAO.create(artist);

        //Then
        assertNotNull( persistentArtistm.getId(), "Artist id must not be null after create!");
        assertTrue(persistentArtistm.getId() >0, "Artist id must be greater than 0 after create!");
    }

    @Test
    public void shouldUpdateArtist(){
        //Given
        Long existingId = 1L;
        String oldName = "An old Name";
        String newName = "A new Name";
        Artist artist = artistDAO.create(new Artist(oldName));

        //When
        artist.setName(newName);
        artist = artistDAO.update(artist);

        //Then
        assertEquals(newName, artist.getName());
    }

    @Test
    public void shouldUpdateArtistAndAlbums(){
        //Given
        Long existingId = 1L;
        String oldName = "An old Name";
        String newName = "A new Name";

        Artist artist = new Artist(oldName);
        artist.add(new Album("Title 4"));
        artist.add(new Album("Title 5"));
        artist.add(new Album("Title 6"));

        artist = artistDAO.create(artist);

        //When
        artist.setName(newName);
        for(Album album : artist.getAlbums()){
            album.setTitle(album.getTitle() + "_updated");
        }
        artist = artistDAO.update(artist);

        //Then
        assertEquals(newName, artist.getName());
    }

    @Test
    public void shouldDeleteArtist(){
        //Given
        Artist artist = artistDAO.create(new Artist("A Name"));

        //When
        artistDAO.delete(artist);

        //Then
        assertNull(artistDAO.findById(artist.getId()), "Artist must not exist after delete");
    }

    @Test
    public void shouldDeleteArtistWithAlbums(){
        //Given
        Artist artist = artistDAO.create(new Artist("A Name"));

        //When
        artistDAO.delete(artist);

        //Then
        assertNull(artistDAO.findById(artist.getId()), "Artist must not exist after delete");
    }
}