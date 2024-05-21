package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation as IntentionOperation
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.IntentionDTO
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("api/intention")
@Tag(name = "Intentions")
class IntentionController {

    @Autowired
    lateinit var  intentionService: IntentionService

    @Operation(
        summary = "Post a new intention",
        description = "Post a new intention to buy or sell a cryptoasset")
    @PostMapping("/new")
    fun postNewIntention(@RequestBody intentionRequest: IntentionRequest): ResponseEntity<IntentionDTO> {
        val newIntention = intentionService.createIntention(intentionRequest.intention, intentionRequest.userId)
        val newIntentionDTO = IntentionDTO.fromModel(newIntention)
        return ResponseEntity.ok().body(newIntentionDTO)
    }

    @Operation(
        summary = "Get all intentions",
        description = "Get a list of all active intentions")
    @GetMapping("/all")
    fun getAllIntentions(): ResponseEntity<List<IntentionDTO>> {
        val allIntentions  = intentionService.getAllIntentions()
        val activeIntentionsDTO = allIntentions.map { IntentionDTO.fromModel(it) }
        return ResponseEntity.ok().body(activeIntentionsDTO)
    }

    @Operation(
        summary = "Get all active intentions",
        description = "Get a list of all active intentions")
    @GetMapping("/active")
    fun getAllActiveIntentions(): ResponseEntity<List<IntentionDTO>> {
        val activeIntentions = intentionService.getActiveIntentions()
        val activeIntentionsDTO = activeIntentions.map { IntentionDTO.fromModel(it) }
        return ResponseEntity.ok().body(activeIntentionsDTO)
    }
}