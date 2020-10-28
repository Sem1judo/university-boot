package com.ua.foxminded.university.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;


public abstract class AbstractDaoEntity<T extends Serializable> {
    private Class<T> clazz;

    @Autowired
    private SessionFactory sessionFactory;

    public void setClazz( Class<T> clazzToSet) {
        clazz = clazzToSet;
    }

    public T getById(long id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    public List getAll() {
        return getCurrentSession().createQuery("from " + clazz.getName()).list();
    }

//    public List<T> getAll() {
//        CriteriaQuery<T> query = getCurrentSession().getCriteriaBuilder().createQuery(clazz);
//        query.select(query.from(clazz));
//        return getCurrentSession().createQuery(query).getResultList();
//    }

    public void create( T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    public T update( T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById( long id) {
        final T entity = getById(id);
        delete(entity);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
