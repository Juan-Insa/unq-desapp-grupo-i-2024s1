/*package ar.edu.unq.desapp.grupoI.backenddesappapi

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/register").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { login ->
                login
                    .loginPage("/login")
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .permitAll()
            }
            .sessionManagement { session ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/login")
                    .sessionFixation().migrateSession()
                    .maximumSessions(1)
                        .expiredUrl("/login")
                        .maxSessionsPreventsLogin(false)
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}*/