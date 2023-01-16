package se.iths.persistency.model;

import java.util.ArrayList;
import java.util.Collection;

public class Artist {
    Long id;
    String name;
    Collection<Album> albums;

    public Artist(String name) {
        this.name = name;
    }

    public Artist(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Album> getAlbums() {
        albums = albums==null?new ArrayList<Album>():albums;
        return albums;
    }

    public void add(Album album) {
        albums = albums==null?new ArrayList<Album>():albums;
        albums.add(album);
    }

    public void add(Collection<Album> albums) {
        this.albums = this.albums==null?new ArrayList<Album>():this.albums;
        this.albums.addAll(albums);
    }

    public void remove(Album album) {
        if (albums==null) return;
        albums.remove(album);
    }

    public String toString(){
        StringBuffer buf = new StringBuffer(getId() + " - " + getName());
        buf.append("\n");
        buf.append("\tNumber of albums: ");
        buf.append(getAlbums().size());
        buf.append("\n");
        for(Album album : getAlbums()){
            buf.append("\t");
            buf.append(album);
            buf.append("\n");
        }
        return buf.toString();
    }
}