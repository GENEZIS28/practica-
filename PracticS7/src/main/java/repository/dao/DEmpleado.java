package repository.dao;

import config.JPAUtil;
import entities.Cargo;
import entities.Empleado;
import jakarta.persistence.EntityManager;
import repository.ICRUD;

import java.util.List;
import java.util.Optional;

public class DEmpleado implements ICRUD<Empleado, Long> {

    @Override
    public Empleado create(Empleado entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }

    @Override
    public List<Empleado> getAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("""
                    select e from Empleado e
                    join fetch e.cargo
                    order by e.apellido, e.nombre
                    """, Empleado.class).getResultList();
        } finally { em.close(); }
    }

    public Optional<Empleado> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            var list = em.createQuery("""
                    select e from Empleado e
                    join fetch e.cargo
                    where e.id = :id
                    """, Empleado.class)
                    .setParameter("id", id)
                    .getResultList();
            return list.stream().findFirst();
        } finally { em.close(); }
    }

    public Empleado update(Empleado entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Empleado merged = em.merge(entity);
            em.getTransaction().commit();
            return merged;
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Empleado e = em.find(Empleado.class, id);
            if (e != null) em.remove(e);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }

    /** Cambiar cargo del empleado (sin histórico) */
    public void changeCargo(Long empleadoId, Long nuevoCargoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Empleado emp = em.find(Empleado.class, empleadoId);
            Cargo car = em.find(Cargo.class, nuevoCargoId);
            if (emp == null || car == null) {
                throw new IllegalArgumentException("Empleado o Cargo no existe");
            }
            emp.setCargo(car);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); }
    }

    /** Buscar por nombre o apellido (sin email) */
    public List<Empleado> searchByNombreApellido(String q) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("""
                    select e from Empleado e
                    join fetch e.cargo
                    where lower(e.nombre) like :q
                       or lower(e.apellido) like :q
                    order by e.apellido, e.nombre
                    """, Empleado.class)
                    .setParameter("q", "%" + q.toLowerCase() + "%")
                    .getResultList();
        } finally { em.close(); }
    }

    /** Listar por cargo específico */
    public List<Empleado> listByCargo(Long cargoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("""
                    select e from Empleado e
                    join fetch e.cargo c
                    where c.id = :cid
                    order by e.apellido, e.nombre
                    """, Empleado.class)
                    .setParameter("cid", cargoId)
                    .getResultList();
        } finally { em.close(); }
    }
}
