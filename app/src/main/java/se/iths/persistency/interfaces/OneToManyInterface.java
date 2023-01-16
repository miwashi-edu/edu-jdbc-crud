package se.iths.persistency.interfaces;

import se.iths.persistency.model.Album;

import java.util.Collection;

public interface OneToManyInterface<T> {
    public Collection<T> findByParentId(Long parentId);
    public T create(T album, Long parentId);
}
