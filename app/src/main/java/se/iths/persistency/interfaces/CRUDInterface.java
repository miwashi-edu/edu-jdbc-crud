package se.iths.persistency.interfaces;

import java.util.Collection;

public interface CRUDInterface<T> {

    public T create(T object);
    public Collection<T> findAll();
    public T findById(Long id);

    public T update(T object);

    public boolean delete(T object);
}
