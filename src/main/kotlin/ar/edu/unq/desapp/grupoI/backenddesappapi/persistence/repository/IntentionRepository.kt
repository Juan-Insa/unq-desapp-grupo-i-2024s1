package ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
@Repository
interface IntentionRepository: CrudRepository<Intention, Long> {
    fun findByState(state: OperationState): List<Intention>
    fun findByUserIdAndState(userId: Long, state: OperationState): List<Intention>
}
