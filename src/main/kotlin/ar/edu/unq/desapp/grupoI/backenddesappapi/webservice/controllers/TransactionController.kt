package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.OperatedVolume
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.TransactionDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.query.Param
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@Transactional
@RequestMapping("api/transaction")
@Tag(name = "Transactions")
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
    @Operation(
        summary = "Get the operated volume for user ID and date",
        description = "Given a user ID and a start and end date, return all transactions operated by the interested user.")
    @GetMapping("/{userId}/{start}/{end}")
    fun getOperatedVolumeFor(
        @Parameter(description = "User ID", required = true, example = "1")
        @PathVariable userId: Long,
        @Parameter(description = "Start date in format dd-MM-yyyy HH:mm:ss", required = true, example = "27-05-2024 15:00:00")
        @PathVariable
        @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") start: LocalDateTime,
        @Parameter(description = "End date in format dd-MM-yyyy HH:mm:ss", required = true, example = "27-05-2024 18:00:00")
        @PathVariable
        @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") end: LocalDateTime
    ): ResponseEntity<OperatedVolume> {
        val operatedVolume = transactionService.getOperatedVolumeFor(userId, start, end)
        return ResponseEntity.ok().body(operatedVolume)
    }

}