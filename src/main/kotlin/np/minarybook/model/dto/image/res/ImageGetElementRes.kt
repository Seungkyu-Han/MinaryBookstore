package np.minarybook.model.dto.image.res

import np.minarybook.model.entity.Image

data class ImageGetElementRes(
    val id: Int,
    val url: String
){
    constructor(image: Image): this(
        id = image.id ?: 0,
        url = image.url
    )
}
