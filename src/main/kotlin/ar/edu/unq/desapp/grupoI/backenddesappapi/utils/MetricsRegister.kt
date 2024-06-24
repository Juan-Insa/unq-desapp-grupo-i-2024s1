package ar.edu.unq.desapp.grupoI.backenddesappapi.utils

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class MetricsRegister(private val meterRegistry: MeterRegistry) {

    val transactionsCounter: Counter = meterRegistry.counter("transactions")
}