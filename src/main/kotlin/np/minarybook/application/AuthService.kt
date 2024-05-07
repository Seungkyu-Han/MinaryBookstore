package np.minarybook.application

import np.minarybook.model.dto.auth.res.AuthLoginRes
import org.springframework.http.ResponseEntity

interface AuthService {
    fun getLogin(code: String): ResponseEntity<AuthLoginRes>
}