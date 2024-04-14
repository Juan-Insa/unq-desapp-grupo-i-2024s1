package ar.edu.unq.desapp.grupoI.backenddesappapi.model

class ActiveIntentions {
    val intentions: MutableList<ActiveIntention> = mutableListOf()

    fun addIntention(intention: ActiveIntention) {
        intentions.add(intention)
    }

    fun getList():  MutableList<ActiveIntention>{
        return intentions
    }
}