package com.ua.foxminded.university.dao;


import java.io.Serializable;
import java.util.List;

public interface DaoEntity<T extends Serializable> {

    T getById( long id);

    List<T> getAll();

    void create( T entity);

    void update( T entity);

    void delete( T entity);

    void deleteById( long entityId);
}
