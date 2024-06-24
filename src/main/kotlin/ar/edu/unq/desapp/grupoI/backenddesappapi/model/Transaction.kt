package ar.edu.unq.desapp.grupoI.backenddesappapi.model

import ar.edu.unq.desapp.grupoI.backenddesappapi.utils.CurrentDateTime.getNewLocalDateTime
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.Action
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.enums.OperationState
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name= "transactions")
class Transaction (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interested_user_id", nullable = false)
    var interestedUser: User,

    var action: Action,
){
    val initTime: LocalDateTime = getNewLocalDateTime() //CurrentDateTime.getNewDateString()

    var state = OperationState.ACTIVE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "intention_id", nullable = false)
    lateinit var intention : Intention

    fun changeAction(action: Action) {
        this.action = action
    }

    fun changeState(state: OperationState) {
        this.state = state
    }

    fun intentionUser(): User {
        return intention.user!!
    }

}