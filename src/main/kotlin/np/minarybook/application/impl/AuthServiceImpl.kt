package np.minarybook.application.impl

import np.minarybook.application.AuthService
import np.minarybook.config.jwt.JwtTokenProvider
import np.minarybook.model.dto.auth.res.AuthGetRes
import np.minarybook.model.dto.auth.res.AuthKakaoAccessRes
import np.minarybook.model.dto.auth.res.AuthKakaoInfoRes
import np.minarybook.model.dto.auth.res.AuthLoginRes
import np.minarybook.model.entity.User
import np.minarybook.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class AuthServiceImpl(
    @Value("\${kakao.auth.client_id}")
    private val clientId: String,
    @Value("\${kakao.auth.redirect_uri}")
    private val redirectUri: String,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
): AuthService {

    private val accessTokenReqUrl = "https://kauth.kakao.com/oauth/token"
    private val jsonReqUrl = "https://kapi.kakao.com/v2/user/me"

    override fun getLogin(code: String): ResponseEntity<AuthLoginRes> {

        val kakaoAccessToken = getKakaoAccessToken(code) ?: throw NullPointerException()

        val authKakaoInfoRes = getIdByKakao(kakaoAccessToken) ?: throw NullPointerException()

        val optionalUser = userRepository.findById(authKakaoInfoRes.id)

        if (optionalUser.isEmpty)
            return register(authKakaoInfoRes)

        val accessToken = jwtTokenProvider.createAccessToken(authKakaoInfoRes.id)
        val refreshToken = jwtTokenProvider.createRefreshToken(authKakaoInfoRes.id)

        val user = optionalUser.get()
        userRepository.save(user)

        return ResponseEntity.ok(AuthLoginRes(refreshToken = refreshToken, accessToken = accessToken))
    }

    override fun patchLogin(refreshToken: String): ResponseEntity<AuthLoginRes> {
        val token = refreshToken.split(" ")[1]
        val userId = jwtTokenProvider.getId(token)

        val accessToken = jwtTokenProvider.createAccessToken(userId ?: throw NullPointerException())

        return ResponseEntity.ok(AuthLoginRes(refreshToken = token, accessToken = accessToken))
    }

    private fun register(authKakaoInfoRes: AuthKakaoInfoRes): ResponseEntity<AuthLoginRes>{

        val accessToken = jwtTokenProvider.createAccessToken(authKakaoInfoRes.id)
        val refreshToken = jwtTokenProvider.createRefreshToken(authKakaoInfoRes.id)

        val user = User(id = authKakaoInfoRes.id, name = authKakaoInfoRes.properties.nickname, profileImg = authKakaoInfoRes.kakaoAccount.profile.profileImageUrl)
        userRepository.save(user)

        return ResponseEntity.ok(AuthLoginRes(refreshToken = refreshToken, accessToken = accessToken))
    }

    private fun getIdByKakao(token: String): AuthKakaoInfoRes? {

        val restTemplate = RestTemplate()

        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
        httpHeaders.add("Authorization", "Bearer $token")

        return restTemplate.exchange(
            RequestEntity<Any>(httpHeaders, HttpMethod.POST, URI.create(jsonReqUrl)),
            AuthKakaoInfoRes::class.java
        ).body
    }

    override fun get(authentication: Authentication): ResponseEntity<AuthGetRes> {
        val user = userRepository.findById(authentication.name.toLong()).orElseThrow { NullPointerException() }
        return ResponseEntity(AuthGetRes(user), HttpStatus.OK)
    }


    private fun getKakaoAccessToken(code: String):String?{

        val restTemplate = RestTemplate()

        val httpHeaders = HttpHeaders()

        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")

        val httpBody = LinkedMultiValueMap<String, String>()

        httpBody.add("grant_type","authorization_code")
        httpBody.add("client_id", clientId)
        httpBody.add("redirect_uri", redirectUri)
        httpBody.add("code",code)

        return restTemplate.postForObject(accessTokenReqUrl, HttpEntity(httpBody, httpHeaders), AuthKakaoAccessRes::class.java)?.access_token
    }
}