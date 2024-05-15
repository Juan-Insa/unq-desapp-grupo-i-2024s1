package ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
@Repository
interface IntentionRepository: CrudRepository<Intention, Long> {
}
