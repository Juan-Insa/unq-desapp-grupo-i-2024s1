package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Asset
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Operation
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "intention")
class Intention(
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    var user: User? = null,
    @Enumerated(EnumType.STRING)
    val cryptoAsset: Asset,
    val amount: Double,
    @Enumerated(EnumType.STRING)
    val operation: Operation,
    val price: Double = 0.0,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var priceInPesos: Double? = null

    @Enumerated(EnumType.STRING)
    var state: OperationState = OperationState.ACTIVE

    fun deactivate() {
        this.state = OperationState.INACTIVE
    }
}