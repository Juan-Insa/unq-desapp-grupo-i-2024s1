package ar.edu.unq.desapp.grupoI.backenddesappapi.utils

import org.ehcache.event.CacheEvent
import org.ehcache.event.CacheEventListener
import org.slf4j.LoggerFactory

class CacheEventLogger : CacheEventListener<Any, Any> {

    private val log = LoggerFactory.getLogger(CacheEventLogger::class.java)

    override fun onEvent(event: CacheEvent<out Any, out Any>) {
        log.info("Cache event = {}, Key = {},  Old value = {}, New value = {}",
            event.type, event.key, event.oldValue, event.newValue)
    }
}