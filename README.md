# edu-jdbc-crud

> [Data Access Object](https://en.wikipedia.org/wiki/Data_access_object) (DAO) är ett mönster man ofta använder när man skapar persistensobjekt med jdbc.

## Instruktioner

```bash
git clone https://github.com/miwashi-edu/edu-jdbc-crud.git
git checkout level-1
idea .
```

## se.iths.persistency.CRUDInterface

```java
public interface CRUDInterface<T> {

    public Collection<T> findAll();
    public T findById();
    public T create(T object);

    public T update(T object);

    public boolean delete(T object);

}
```

## se.iths.persistency.ArtistDAO

```java
public class ArtistDAO implements CRUDInterface<Artist> {
    @Override
    public Collection<Artist> findAll() {
        return null;
    }

    @Override
    public Artist findById() {
        return null;
    }

    @Override
    public Artist create(Artist object) {
        return null;
    }

    @Override
    public Artist update(Artist object) {
        return null;
    }

    @Override
    public boolean delete(Artist object) {
        return false;
    }
}
```

## se.iths.persistency.AlbumDAO

```java
public class AlbumDAO implements CRUDInterface<Album> {

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
```
