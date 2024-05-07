package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.AuthService
import np.minarybook.model.dto.auth.res.AuthLoginRes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Tag(name = "인증")
class AuthController(private val authService: AuthService) {

    @GetMapping("/login")
    @Operation(hidden = true)
    fun getLogin(@RequestParam code: String): ResponseEntity<AuthLoginRes>{
        return authService.getLogin(code)
    }
}