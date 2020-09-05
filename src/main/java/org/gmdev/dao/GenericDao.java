package org.gmdev.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao< T > {

    void setEntityClass(Class< T > classToSet);

    List< T > findAll();

    Optional< T > findById(Long id);

    T create(T entity);

    T update(T entity);

    void deleteById(Long id);
}
