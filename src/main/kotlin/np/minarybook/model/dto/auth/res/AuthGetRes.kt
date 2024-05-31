package np.minarybook.model.dto.auth.res

import np.minarybook.model.entity.User

data class AuthGetRes(
    val name: String?,
    val img: String?
){
    constructor(user: User): this(
        name = user.name, img = user.profileImg
    )
}