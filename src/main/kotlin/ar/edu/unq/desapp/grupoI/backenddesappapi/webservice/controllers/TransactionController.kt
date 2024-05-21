package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.TransactionDTO
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(
        summary = "Post a new transaction",
        description = "Generates a transaction from a provided intention and interested user id's")
    @PostMapping("/create")
    fun createTransaction(@RequestBody transactionRequest: TransactionRequest): ResponseEntity<TransactionDTO> {
        val transaction = transactionService.createTransaction(transactionRequest.intentionId, transactionRequest.userId)
        val transactionDTO = TransactionDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionDTO)
    }

    @Operation(
        summary = "Retrieve a transaction",
        description = "Retrieves a transaction correspondig to the given id")
    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Long): ResponseEntity<TransactionDTO> {
        val transaction = transactionService.getTransactionById(id)
        val transactionDTO = TransactionDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionDTO)
    }

    @Operation(
        summary = "Finish the transaction",
        description = "Finishes the transaction by the given id setting its state to inactive and adding the corresponding reputation points to users")
    @PutMapping("/{id}/finish")
    fun finishTransaction(@PathVariable id: Long): ResponseEntity<TransactionDTO> {
        val transaction = transactionService.finishTransaction(id)
        val transactionDTO = TransactionDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionDTO)
    }

    @Operation(
        summary = "Cancels a transaction",
        description = "Cancel the transaction by the given id setting its state to inactive and resting the corresponding reputation points to users")
    @PutMapping("/{transactionId}/{userId}/cancel")
    fun cancelTransaction(
        @PathVariable transactionId: Long,
        @PathVariable userId: Long
    ): ResponseEntity<TransactionDTO> {
        val transaction = transactionService.cancelTransaction(transactionId, userId)
        val transactionDTO = TransactionDTO.fromModel(transaction)
        return ResponseEntity.ok().body(transactionDTO)
    }


}