# edu-jdbc-crud

> Vi använder javas strategi mot null pointer exception, Optional. 

> Optional har funnits sedan java 11

## Instruktioner

```bash
git clone https://github.com/miwashi-edu/edu-jdbc-crud.git
git checkout level-2
idea .
```

## se.iths.persistency.CRUDInterface

```java
package se.iths.persistency;

import java.util.Collection;
import java.util.Optional;

public interface CRUDInterface<T> {
    public Collection<T> findAll();
    public Optional<T> findById();
    public Optional<T> create(T object);

    public Optional<T> update(T object);

    public boolean delete(T object);
}

```

## se.iths.persistency.ArtistDAO

```java
package se.iths.persistency;

import se.iths.persistency.model.Album;
import se.iths.persistency.model.Artist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ArtistDAO implements CRUDInterface<Artist> {
    @Override
    public Collection<Artist> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Artist> findById() {
        Optional<Artist> optionalArtist = Optional.empty();
        return optionalArtist;
    }

    @Override
    public Optional<Artist> create(Artist object) {
        Optional<Artist> optionalArtist = Optional.empty();
        return optionalArtist;
    }

    @Override
    public Optional<Artist> update(Artist object) {
        Optional<Artist> optionalArtist = Optional.empty();
        return optionalArtist;
    }

    @Override
    public boolean delete(Artist object) {
        return false;
    }
}

```

## se.iths.persistency.AlbumDAO

```java
package se.iths.persistency;

import se.iths.persistency.model.Album;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class AlbumDAO implements CRUDInterface<Album>  {
    @Override
    public Collection<Album> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Album> findById() {
        Optional<Album> optionalAlbum = Optional.empty();
        return optionalAlbum;
    }

    public Collection<Album> findByArtistId() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Album>  create(Album object) {
        Optional<Album> optionalAlbum = Optional.empty();
        return optionalAlbum;
    }

    @Override
    public Optional<Album>  update(Album object) {
        Optional<Album> optionalAlbum = Optional.empty();
        return optionalAlbum;
    }

    @Override
    public boolean delete(Album object) {
        return false;
    }
}
```
