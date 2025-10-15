package repository;

import java.util.List;

import java.util.Optional;

public interface ICRUD<T, ID> {
    T create(T entity);
    Optional<T> findById(ID id);
    List<T> getAll();
    T update(T entity);
    void delete(ID id);
}