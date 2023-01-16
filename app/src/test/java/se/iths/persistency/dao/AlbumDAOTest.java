package se.iths.persistency.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.iths.persistency.model.Album;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static se.iths.persistency.dao.DAOUtil.execute;

public class AlbumDAOTest {

    AlbumDAO albumDAO = new AlbumDAO();

    /**
     * After all tests are run, delete all created albums.
     * Chinooks last album id was 347 before tests run.
     */
    @AfterAll
    public static void tearDown(){
        execute("DELETE FROM Album WHERE AlbumId > 347");
    }

    /**
     * Test that creates an album.
     * Album has a foreign key constraint requiring an Artist to exist before album can be inserted.
     */
    @Test
    public void shouldCreateAlbum(){
        //Given
        Album album = new Album("A title");

        //When
        Album persistentAlbumm = albumDAO.create(album, 1L);

        //Then
        assertNotNull( persistentAlbumm.getId(), "Album id must not be null after create!");
        assertTrue(persistentAlbumm.getId() >0, "Album id must be greater than 0 after create!");
    }

    /**
     * Test that create method from CRUD interface isn't allowed.
     * Database design doesn't allow for orphan Albums.
     */
    @Test
    public void shouldNotCreateAlbumWithoutArtist(){
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            albumDAO.create(new Album("any title"));
        });
    }

    /**
     * Tests finding all Albums.
     */
    @Test
    public void shouldFindAllAlbums(){
        //Given

        //When
        Collection<Album> albums = albumDAO.findAll();
        //Then
        assertNotNull(albums, "Albums must not be null after find all!");
        assertTrue(albums.size() > 0 , "Albums must exist after find all!");
    }

    /**
     * Tests finding all albums for artist.
     * Assumes artist 1L exists and has albums. (bad testing strategy)
     */
    @Test
    public void shouldFindAllAlbumsForArtis(){
        //Given
        Long artistId = 1L;

        //When
        Collection<Album> albums = albumDAO.findByParentId(artistId);
        //Then
        assertNotNull(albums, "Albums must not be null after find all!");
        assertTrue(albums.size() > 0 , "Albums must exist after find all!");
    }

    /**
     * Test finding album by album id.
     */
    @Test
    public void shouldFindAlbumById(){
        //Given
        Long existingId = 1L;
        Long albumId = albumDAO.create(new Album("A title"), existingId).getId();

        //When
        Album album = albumDAO.findById(albumId);

        //Then
        assertNotNull(album);
    }

    /**
     * Test finding an album with an id that doesn't exist.
     */
    @Test
    public void shouldNotFindAlbumByFaultyId(){
        //Given
        Long nonExistingId = -1L;

        //When
        Album album = albumDAO.findById(nonExistingId);

        //Then
        assertNull(album, "Albums must not be found with faulty id!");
    }

    /**
     * Test updating an album.
     */
    @Test
    public void shouldUpdateAlbum(){
        //Given
        Long existingId = 1L;
        String oldTitle = "An old title";
        String newTitle = "A new title";
        Album album = albumDAO.create(new Album(oldTitle), existingId);

        //When
        album.setTitle(newTitle);
        album = albumDAO.update(album);

        //Then
        assertEquals(newTitle, album.getTitle());
    }

    /**
     * Test deleting one Album.
     */
    @Test
    public void shouldDeleteAlbum(){
        //Given
        Long existingId = 1L;
        Album album = albumDAO.create(new Album("A title"), existingId);

        //When
        albumDAO.delete(album);

        //Then
        assertNull(albumDAO.findById(album.getId()), "Album must not exist after delete");
    }
}