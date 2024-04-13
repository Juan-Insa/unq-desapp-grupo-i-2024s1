package ar.edu.unq.desapp.grupoI.backenddesappapi

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/register").permitAll() // Permite acceso sin autenticación al endpoint /register
                    .anyRequest().authenticated()                      // Establece que las demás solicitudes requieren autenticación
            }
            .formLogin { login ->
                login
                    .loginPage("/login") // Página de inicio de sesión personalizada
                    .permitAll()         // Permite acceso sin autenticación a la página de inicio de sesión
            }
            .logout { logout ->
                logout
                    .permitAll() // Permite acceso sin autenticación al endpoint de cierre de sesión
            }
            .sessionManagement { session ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Política de creación de sesión si es necesario
                    .invalidSessionUrl("/login")                              // Redirige a /login si la sesión es inválida
                    .sessionFixation().migrateSession()                       // Migración de sesión para evitar ataques de fijación de sesión
                    .maximumSessions(1)                                       // Configura la cantidad máxima de sesiones y su manejo al expirar
                        .expiredUrl("/login")
                        .maxSessionsPreventsLogin(false)
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}