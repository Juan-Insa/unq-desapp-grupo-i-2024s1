package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.dto.IntentionDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("api/intention")
class IntentionController {

    @Autowired
    lateinit var  intentionService: IntentionService

    @PostMapping("/new")
    fun postNewIntention(@RequestBody intentionRequest: IntentionRequest): ResponseEntity<IntentionDTO> {
        val newIntention = intentionService.createIntention(intentionRequest.intention, intentionRequest.userId)
        val newIntentionDTO = IntentionDTO.fromModel(newIntention)
        return ResponseEntity.ok().body(newIntentionDTO)
    }

    @GetMapping("/all")
    fun getAllIntentions(): ResponseEntity<List<IntentionDTO>> {
        val allIntentions  = intentionService.getAllIntentions()
        val activeIntentionsDTO = allIntentions.map { IntentionDTO.fromModel(it) }
        return ResponseEntity.ok().body(activeIntentionsDTO)
    }

    @GetMapping("/active")
    fun getAllActiveIntentions(): ResponseEntity<List<IntentionDTO>> {
        val activeIntentions = intentionService.getActiveIntentions()
        val activeIntentionsDTO = activeIntentions.map { IntentionDTO.fromModel(it) }
        return ResponseEntity.ok().body(activeIntentionsDTO)
    }
}