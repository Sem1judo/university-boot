package com.ua.foxminded.university.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;



public abstract class AbstractDaoEntity<T extends Serializable> {
    private Class< T > clazz;

    @PersistenceContext
    EntityManager entityManager;

    public void setClazz( Class< T > clazzToSet ) {
        this.clazz = clazzToSet;
    }

    public T getById( long id ){
        return entityManager.find( clazz, id );
    }
    public List< T > getAll(){
        return entityManager.createQuery( "from " + clazz.getName() )
                .getResultList();
    }

    public void create( T entity ){
        entityManager.persist( entity );
    }

    public void update( T entity ){
        entityManager.merge( entity );
    }

    public void delete( T entity ){
        entityManager.remove( entity );
    }
    public void deleteById( long entityId ){
        T entity = getById( entityId );
        delete( entity );
    }
}
