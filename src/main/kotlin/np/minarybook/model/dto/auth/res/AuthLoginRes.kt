package np.minarybook.model.dto.auth.res

data class AuthLoginRes(
    val accessToken: String,
    val refreshToken: String
)
