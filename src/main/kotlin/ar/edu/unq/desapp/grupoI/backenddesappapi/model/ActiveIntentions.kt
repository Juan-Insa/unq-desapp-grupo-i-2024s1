package ar.edu.unq.desapp.grupoI.backenddesappapi.model

class ActiveIntentions {

    companion object {

        val intentions: MutableList<Intention> = mutableListOf()

        fun addIntention(intention: Intention) {
            intentions.add(intention)
        }

        fun removeIntention(intention: Intention) {
            this.intentions.remove(intention)
        }
    }
}