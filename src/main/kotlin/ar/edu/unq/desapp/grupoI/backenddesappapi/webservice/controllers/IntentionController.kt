package ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.ActiveIntentions
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.Intention
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.service.IntentionService
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
    fun postNewIntention(
        @RequestBody intentionRequest: IntentionRequest
    ): ResponseEntity<Intention> {
        val asset: Asset
        val oper: Operation
        try {
            asset = Asset.valueOf(intentionRequest.cryptoAsset)
            oper = Operation.valueOf(intentionRequest.operation)
        } catch (ex: IllegalArgumentException) {
            return ResponseEntity.badRequest().build()
        }
        val newIntention = intentionService.createIntention(intentionRequest.userEmail, asset, intentionRequest.amount, oper, intentionRequest.price)
        return ResponseEntity.ok().body(newIntention)
    }
    @GetMapping("/all")
    fun getAllActiveIntentions(): ResponseEntity<MutableList<Intention>> {
        val activeIntentions = intentionService.getAllIntentions()
        return ResponseEntity.ok().body(activeIntentions)
    }
}