package np.minarybook.application.impl

import np.minarybook.application.ISBNService
import np.minarybook.model.dto.isbn.ISBNGetRes
import np.minarybook.model.dto.isbn.ISBNSeojiRes
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class ISBNServiceImpl(
    @Value("\${kakao.auth.client_id}")
    private val key: String
): ISBNService {

    private val requestUrl: String = "https://dapi.kakao.com/v3/search/book"

    override fun get(isbn: String): ResponseEntity<ISBNGetRes> {

        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.set("Authorization", "KakaoAK $key")
        headers.contentType = MediaType.APPLICATION_JSON

        val requestUrlWithParam = UriComponentsBuilder.fromHttpUrl(requestUrl)
            .queryParam("target", "isbn")
            .queryParam("query", isbn)
            .build(true).toString()

        val requestEntity = HttpEntity<String>(headers)

        val response = restTemplate.exchange(
            requestUrlWithParam,
            HttpMethod.GET,
            requestEntity,
            ISBNSeojiRes::class.java
        )

        val isbnBookRes = response.body?.documents?.get(0)

        return ResponseEntity.ok(
            ISBNGetRes(
                title = isbnBookRes?.title,
                price = isbnBookRes?.price,
                author = isbnBookRes?.authors,
                img = isbnBookRes?.thumbnail,
                publicationDate = isbnBookRes?.datetime,
                publisher = isbnBookRes?.publisher,
                isbn = isbnBookRes?.isbn
            )
        )
    }
}