package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation as IntentionOperation
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
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
    fun postNewIntention(
        @RequestBody intentionRequest: IntentionRequest
    ): ResponseEntity<Intention> {
        val asset: Asset
        val oper: IntentionOperation
        try {
            asset = Asset.valueOf(intentionRequest.cryptoAsset)
            oper = IntentionOperation.valueOf(intentionRequest.operation)
        } catch (ex: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }
        val newIntention = intentionService.createIntention(intentionRequest.userEmail, asset, intentionRequest.amount, oper, intentionRequest.price)
        return ResponseEntity.ok().body(newIntention)
    }
    @Operation(
        summary = "Get all active intentions",
        description = "Get a list of all active intentions")
    @GetMapping("/all")
    fun getAllActiveIntentions(): ResponseEntity<MutableList<Intention>> {
        val activeIntentions = intentionService.getAllIntentions()
        return ResponseEntity.ok().body(activeIntentions)
    }
}