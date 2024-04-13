package ar.edu.unq.desapp.grupoI.backenddesappapi.service

import org.springframework.security.core.userdetails.UserDetails

interface UserDetailsService {
    fun loadUserByName(name: String): UserDetails
}