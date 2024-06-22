package ar.edu.unq.desapp.grupoI.backenddesappapi.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*
import java.util.logging.Logger
import javax.crypto.SecretKey


@Component
class JwtProvider {
    @Value("\${security.jwt.secret-key}")
    private val secret: String? = null

    @Value("\${security.jwt.expiration-time}")
    private val expiration: Int? = null

    fun generateToken(userDetails: UserDetails): String {
        return Jwts.builder()
            .subject(userDetails.username)
            .claim("roles", userDetails.authorities)
            .issuedAt(Date())
            .expiration(Date(Date().time + expiration!!))
            .signWith(getKey(secret))
            .compact()
    }

    fun getClaims(token: String?): Claims {
        return Jwts.parser()
            .verifyWith(getKey(secret))
            .build()
            .parseUnsecuredClaims(token)
            .payload
    }

    fun getSubject(token: String?): String {
        return Jwts.parser()
            .verifyWith(getKey(secret))
            .build()
            .parseUnsecuredClaims(token)
            .payload
            .subject
    }

    fun validate(token: String?): Boolean {
        try {
            Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseUnsecuredClaims(token)
                .payload
                .subject
            return true
        } catch (e: ExpiredJwtException) {
            LOGGER.severe("token expired")
        } catch (e: UnsupportedJwtException) {
            LOGGER.severe("token unsupported")
        } catch (e: MalformedJwtException) {
            LOGGER.severe("token malformed")
        } catch (e: SignatureException) {
            LOGGER.severe("bad signature")
        } catch (e: IllegalArgumentException) {
            LOGGER.severe("illegal args")
        }
        return false
    }

    private fun getKey(secret: String?): SecretKey {
        val secretBytes = Decoders.BASE64URL.decode(secret)
        return Keys.hmacShaKeyFor(secretBytes)
    }

    companion object {
        private val LOGGER: Logger = Logger.getLogger(JwtProvider::class.java.name)
    }
}