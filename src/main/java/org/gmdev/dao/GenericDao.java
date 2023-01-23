package org.gmdev.dao;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import java.util.List;
import java.util.Optional;

public interface GenericDao< T > {

    void setEntityClass(Class< T > classToSet);

    JpaEntityInformation<T, ? > getEntityInfo();

    List< T > findAll();

    Optional< T > findById(Long id);

    T create(T entity);

    T update(T entity);

    void deleteById(Long id);

    void deleteAll();
}
