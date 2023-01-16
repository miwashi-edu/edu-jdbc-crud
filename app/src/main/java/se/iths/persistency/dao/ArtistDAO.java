package se.iths.persistency.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.persistency.interfaces.CRUDInterface;
import se.iths.persistency.model.Album;
import se.iths.persistency.model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static se.iths.persistency.dao.DAOUtil.close;
import static se.iths.persistency.dao.DAOUtil.getConnection;

public class ArtistDAO implements CRUDInterface<Artist> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistDAO.class);
    private static final String TABLE_NAME = "Artist";
    private static final String COL_ID = "ArtistId";
    private static final String COL_NAME = "Name";
    private static final String SQL_SELECT_BY_ID = String.format("SELECT %s, %s FROM %s WHERE %s = ?", COL_ID, COL_NAME, TABLE_NAME, COL_ID);
    private static final String SQL_SELECT_ALL = String.format("SELECT %s, %s FROM %s", COL_ID, COL_NAME, TABLE_NAME);
    private static final String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES(?)", TABLE_NAME, COL_NAME);
    private static final String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COL_ID);
    private static final String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLE_NAME, COL_NAME, COL_ID);

    static {
        LOGGER.info("Prepared statement \"{}\" created!", SQL_SELECT_BY_ID);
        LOGGER.info("Prepared statement \"{}\" created!", SQL_SELECT_ALL);
        LOGGER.info("Prepared statement \"{}\" created!", SQL_INSERT);
        LOGGER.info("Prepared statement \"{}\" created!", SQL_DELETE);
        LOGGER.info("Prepared statement \"{}\" created!", SQL_UPDATE);
    }
    @Override
    public Collection<Artist> findAll() {
        Collection<Artist> result =  new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            while(rs.next()){
                Long artistId = rs.getLong(COL_ID);
                String name = rs.getString(COL_NAME);
                result.add(new Artist(artistId,  name));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to findByID", e);
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        AlbumDAO albumDAO = new AlbumDAO();
        for(Artist artist : result){
            Collection<Album> albums = albumDAO.findByParentId(artist.getId());
            artist.add(albums);
        }
        return result;
    }

    @Override
    public Artist findById(Long id) {
         Artist artist = null;
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if(rs.next()){
                Long artistId = rs.getLong(COL_ID);
                String name = rs.getString(COL_NAME);
                artist = new Artist(artistId,  name);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to findByID", e);
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        if(artist==null) return artist;

        AlbumDAO albumDAO = new AlbumDAO();
        Collection<Album> albums = albumDAO.findByParentId(artist.getId());
        artist.add(albums);

        return artist;
    }

    @Override
    public Artist create(Artist artist) {
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, artist.getName());
            stmt.execute();
            rs = stmt.getGeneratedKeys();
            rs.next();
            artist.setId(rs.getLong(1));
        } catch (SQLException e) {
            LOGGER.error("Failed to create", e);
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        AlbumDAO albumDAO = new AlbumDAO();
        for(Album album : artist.getAlbums()){
            albumDAO.create(album, artist.getId());
        }

        return artist;
    }

    @Override
    public Artist update(Artist artist) {
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_UPDATE);
            stmt.setString(1, artist.getName());
            stmt.setLong(2, artist.getId());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.error("Failed to update", e);
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        AlbumDAO albumDAO = new AlbumDAO();
        for(Album album : artist.getAlbums()){
            if(album.getId()!=null){
                albumDAO.update(album);
            }else {
                albumDAO.create(album, artist.getId());
            }
        }
        return artist;
    }

    @Override
    public boolean delete(Artist artist) {
        boolean result = false;
        Connection con = null;
        PreparedStatement  stmt = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_DELETE);
            stmt.setLong(1, artist.getId());
            stmt.execute();
            result = true;
        } catch (SQLException e) {
            LOGGER.error("Failed to delete", e);
            throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(con);
        }
        return result;
    }
}
