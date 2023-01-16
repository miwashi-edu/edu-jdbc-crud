package se.iths.persistency.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.persistency.interfaces.CRUDInterface;
import se.iths.persistency.interfaces.OneToManyInterface;
import se.iths.persistency.model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


import static se.iths.persistency.dao.DAOUtil.close;
import static se.iths.persistency.dao.DAOUtil.getConnection;

public class AlbumDAO implements CRUDInterface<Album>, OneToManyInterface<Album> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumDAO.class);
    private static final String TABLE_NAME = "Album";
    private static final String COL_ID = "AlbumId";
    private static final String COL_ARTIST_ID = "ArtistId";
    private static final String COL_TITLE = "Title";
    private static final String SQL_SELECT_BY_ID = String.format("SELECT %s, %s FROM %s WHERE %s = ?", COL_ID, COL_TITLE, TABLE_NAME, COL_ID);
    private static final String SQL_SELECT_BY_PARENT_ID = String.format("SELECT %s, %s FROM %s WHERE %S = ?", COL_ID, COL_TITLE, TABLE_NAME, COL_ARTIST_ID);
    private static final String SQL_SELECT_ALL = String.format("SELECT %s, %s FROM %s", COL_ID, COL_TITLE, TABLE_NAME);
    private static final String SQL_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES(?, ?)", TABLE_NAME, COL_TITLE, COL_ARTIST_ID);
    private static final String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COL_ID);
    private static final String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLE_NAME, COL_TITLE, COL_ID);

    static {
        LOGGER.info("Prepared statement \"{}\" created!", SQL_SELECT_BY_ID);
        LOGGER.info("Prepared statement \"{}\" created!", SQL_SELECT_BY_PARENT_ID);
        LOGGER.info("Prepared statement \"{}\" created!", SQL_SELECT_ALL);
        LOGGER.info("Prepared statement \"{}\" created!", SQL_INSERT);
        LOGGER.info("Prepared statement \"{}\" created!", SQL_DELETE);
        LOGGER.info("Prepared statement \"{}\" created!", SQL_UPDATE);
    }

    @Override
    public Album create(Album object) {
        throw new RuntimeException("Album can't be created without Artist!");
    }

    /**
     * Finds all albums in database
     * @return all albums.
     */
    public Collection<Album> findAll() {
        Collection<Album> result =  new ArrayList<>();
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            while(rs.next()){
                Long albumId = rs.getLong(COL_ID);
                String title = rs.getString(COL_TITLE);
                result.add(new Album(albumId,  title));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to findByID", e);
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        return result;
    }

    /**
     * Finds one Album by id.
     * @param id Id of album.
     * @return One Album.
     */
    @Override
    public Album findById(Long id) {
        Album album = null;
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if(rs.next()){
                Long albumId = rs.getLong(COL_ID);
                String title = rs.getString(COL_TITLE);
                album = new Album(albumId,  title);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to findByID", e);
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        return album;
    }

    /**
     * Finds all Albums belonging to artist identified by parentId
     * @return
     */
    @Override
    public Collection<Album> findByParentId(Long parentId) {
        Collection<Album> result =  new ArrayList<>();
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_SELECT_BY_PARENT_ID);
            stmt.setLong(1, parentId);
            rs = stmt.executeQuery();
            while(rs.next()){
                Long albumId = rs.getLong(COL_ID);
                String title = rs.getString(COL_TITLE);
                result.add(new Album(albumId,  title));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to findByID", e);
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        return result;
    }

    @Override
    public Album create(Album album, Long parentId) {
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, album.getTitle());
            stmt.setLong(2, parentId);
            stmt.execute();
            rs = stmt.getGeneratedKeys();
            rs.next();
            album.setId(rs.getLong(1));
        } catch (SQLException e) {
            LOGGER.error("Failed to create", e);
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        return album;
    }

    @Override
    public Album update(Album album) {
        Connection con = null;
        PreparedStatement  stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_UPDATE);
            stmt.setString(1, album.getTitle());
            stmt.setLong(2, album.getId());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.error("Failed to update", e);
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        return album;
    }

    @Override
    public boolean delete(Album album) {
        boolean result = false;
        Connection con = null;
        PreparedStatement  stmt = null;
        try {
            con = getConnection();
            stmt = con.prepareStatement(SQL_DELETE);
            stmt.setLong(1, album.getId());
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
