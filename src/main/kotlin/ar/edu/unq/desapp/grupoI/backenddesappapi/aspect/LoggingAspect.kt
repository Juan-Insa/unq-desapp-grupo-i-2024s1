package ar.edu.unq.desapp.grupoI.backenddesappapi.aspect

import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.request.IntentionRequest
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.request.TransactionRequest
import ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers.request.UserRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Around("execution(* ar.edu.unq.desapp.grupoI.backenddesappapi.webservice.controllers..*(..))")
    fun logWebServiceCalls(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()

        val methodName = joinPoint.signature.toShortString()
        val params = joinPoint.args.joinToString()

        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.name

        val result = joinPoint.proceed()

        val elapsedTime = System.currentTimeMillis() - startTime
        val timestamp = java.time.Instant.ofEpochMilli(startTime).toString()

        logger.info("timestamp=$timestamp, user=$user, method=$methodName, params=$params, elapsedTime=${elapsedTime}ms")

        return result
    }

    private fun getUserIdFromArgs(args: Array<Any>): Long? {
        for (arg in args) {
            when (arg) {
                is IntentionRequest   -> return arg.userId
                is TransactionRequest -> return arg.userId
                is UserRequest        -> return arg.user.id
                is Long               -> return arg
            }
        }
        return null
    }
}