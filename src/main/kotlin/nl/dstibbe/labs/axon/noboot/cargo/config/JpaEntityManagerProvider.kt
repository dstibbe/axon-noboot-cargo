package nl.dstibbe.labs.axon.noboot.cargo.config

import org.axonframework.common.jpa.EntityManagerProvider
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
class JpaEntityManagerProvider : EntityManagerProvider {

    private var entityManager: EntityManager? = null

    @PersistenceContext(unitName = "events")
    fun setEntityManager(entityManager: EntityManager) {
        this.entityManager = entityManager
    }

    override fun getEntityManager(): EntityManager? {
        return entityManager
    }
}
