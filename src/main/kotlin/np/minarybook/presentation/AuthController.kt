package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.AuthService
import np.minarybook.model.dto.auth.res.AuthGetRes
import np.minarybook.model.dto.auth.res.AuthLoginRes
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@Tag(name = "인증")
class AuthController(private val authService: AuthService) {

    @GetMapping("/login")
    @Operation(hidden = true)
    fun getLogin(@RequestParam code: String): ResponseEntity<AuthLoginRes>{
        return authService.getLogin(code)
    }

    @PatchMapping("/login")
    @Operation(summary = "AccessToken 갱신 API", description = "RefreshToken 사용해서 AccessToken 갱신")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content(schema = Schema(implementation = AuthLoginRes::class))))
    )
    fun patchLogin(@Parameter(hidden = true) @RequestHeader("Authorization") refreshToken: String):ResponseEntity<AuthLoginRes>{
        return authService.patchLogin(refreshToken)
    }

    @GetMapping
    @Operation(summary = "유저 정보 조회", description = "카카오에서 유저 정보를 조회")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content(schema = Schema(implementation = AuthGetRes::class))))
    )
    fun get(@Parameter(hidden = true) authentication: Authentication): ResponseEntity<AuthGetRes>{
        return authService.get(authentication)
    }
}