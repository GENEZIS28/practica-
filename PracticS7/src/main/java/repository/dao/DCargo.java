package repository.dao;

import config.JPAUtil;
import entities.Cargo;
import jakarta.persistence.EntityManager;
import repository.ICRUD;

import java.util.List;
import java.util.Optional;

public class DCargo implements ICRUD<Cargo, Long> {

    @Override
    public Cargo create(Cargo entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Cargo> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Cargo.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cargo> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("select c from Cargo c order by c.nombre", Cargo.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Cargo update(Cargo entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Cargo merged = em.merge(entity);
            em.getTransaction().commit();
            return merged;
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Cargo c = em.find(Cargo.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
