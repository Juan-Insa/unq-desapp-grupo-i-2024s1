package ar.edu.unq.desapp.grupoI.backenddesappapi.persistence.repository

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionRepository: CrudRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.interestedUser.id = :userId AND t.initTime BETWEEN :start AND :end")
    fun findByInterestedUserIdAndDate(
        @Param("userId") userId: Long,
        @Param("start") start: LocalDateTime,
        @Param("end") end: LocalDateTime,
    ): List<Transaction>
}
