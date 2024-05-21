package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.TransactionDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("api/transaction")
class TransactionController {

    @Autowired
    lateinit var transactionService: TransactionService

    @PostMapping("/create")
    fun createTransaction(@RequestBody transactionRequest: TransactionRequest): ResponseEntity<TransactionDTO> {
        val transaction = transactionService.createTransaction(transactionRequest.intentionId, transactionRequest.userId)
        val transactionDTO = TransactionDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionDTO)
    }

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Long): ResponseEntity<TransactionDTO> {
        val transaction = transactionService.getTransactionById(id)
        val transactionDTO = TransactionDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionDTO)
    }

    @PutMapping("/{id}/finish")
    fun finishTransaction(@PathVariable id: Long): ResponseEntity<TransactionDTO> {
        val transaction = transactionService.finishTransaction(id)
        val transactionDTO = TransactionDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionDTO)
    }

    @PutMapping("/{transactionId}/{userId}/cancel")
    fun cancelTransaction(
        @PathVariable transactionId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<Transaction> {
        val transaction = transactionService.cancelTransaction(transactionId, userId)
        return ResponseEntity.ok().body(transaction)
    }


}