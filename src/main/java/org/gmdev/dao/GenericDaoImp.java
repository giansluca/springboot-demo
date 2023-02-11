package org.gmdev.dao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericDaoImp< T > implements GenericDao< T > {

    private Class< T > entityClass;

    private JpaEntityInformation< T, ? > entityInformation;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void setEntityClass(Class< T > classToSet) {
        this.entityClass = classToSet;
        setEntityInformation();
    }

    public JpaEntityInformation<T, ?> getEntityInfo() {
        return entityInformation;
    }

    @Override
    public List< T > findAll() {
        return em.createQuery(String.format("FROM %s", entityClass.getName()), entityClass).getResultList();
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    @Override
    public T create(T entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public void deleteById(Long id) {
        T entity = findById(id)
                .orElseThrow( () -> new EmptyResultDataAccessException(
                        String.format("No entity with id %d exists!", id), 1));

        delete(entity);
    }

    private void delete(T entity) {
        em.remove(entity);
    }

    @Override
    public void deleteAll() {
        em.createQuery(String.format("DELETE FROM %s", entityClass.getName())).executeUpdate();
    }

    private void setEntityInformation() {
        entityInformation =  JpaEntityInformationSupport.getEntityInformation(entityClass, em);
    }


}
