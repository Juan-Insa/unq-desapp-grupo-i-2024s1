package ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: CrudRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.interestedUser.id = :userId")
    fun findByInterestedUserId(
        @Param("userId") userId: Long,
    ): List<Transaction>
}
