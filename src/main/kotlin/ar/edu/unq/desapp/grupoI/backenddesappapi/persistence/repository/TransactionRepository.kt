package ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: CrudRepository<Transaction, Long> {
    //fun findBy
}
