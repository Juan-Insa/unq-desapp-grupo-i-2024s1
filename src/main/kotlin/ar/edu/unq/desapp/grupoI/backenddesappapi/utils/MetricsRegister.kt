package ar.edu.unq.desapp.grupoI.backenddesappapi.utils

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class MetricsRegister(private val meterRegistry: MeterRegistry) {

    val finishedTransactionsCounter: Counter = meterRegistry.counter("finished_transactions")
    val canceledTransactionsCounter: Counter = meterRegistry.counter("canceled_transactions")
}