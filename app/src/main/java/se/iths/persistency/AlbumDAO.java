package se.iths.persistency;

import se.iths.persistency.model.Album;

import java.util.Collection;

public class AlbumDAO implements CRUDInterface<Album>  {
    @Override
    public Collection<Album> findAll() {
        return null;
    }

    @Override
    public Album findById() {
        return null;
    }

    public Album findByArtistId() {
        return null;
    }

    @Override
    public Album create(Album object) {
        return null;
    }

    @Override
    public Album update(Album object) {
        return null;
    }

    @Override
    public boolean delete(Album object) {
        return false;
    }
}
