package np.minarybook.application

import np.minarybook.model.dto.auth.res.AuthGetRes
import np.minarybook.model.dto.auth.res.AuthLoginRes
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface AuthService {
    fun getLogin(code: String): ResponseEntity<AuthLoginRes>
    fun patchLogin(refreshToken: String): ResponseEntity<AuthLoginRes>
    fun get(authentication: Authentication): ResponseEntity<AuthGetRes>
}