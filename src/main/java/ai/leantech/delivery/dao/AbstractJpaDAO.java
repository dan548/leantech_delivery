package ai.leantech.delivery.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public abstract class AbstractJpaDAO<T> {

    private Class<T> clazz;

    @PersistenceContext
    EntityManager entityManager;

    public final void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public Optional<T> findOne(long id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery( "from " + clazz.getName())
                .getResultList();
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(long entityId) {
        Optional<T> entity = findOne(entityId);
        delete(entity.orElse(null));
    }
}
