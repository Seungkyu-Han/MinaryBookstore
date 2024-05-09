package np.minarybook.application

import np.minarybook.model.dto.isbn.ISBNGetRes
import org.springframework.http.ResponseEntity

interface ISBNService {

    fun get(isbn: String): ResponseEntity<ISBNGetRes>
}