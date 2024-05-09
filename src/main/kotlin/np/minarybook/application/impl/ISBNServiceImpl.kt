package np.minarybook.application.impl

import np.minarybook.application.ISBNService
import np.minarybook.model.dto.isbn.ISBNGetRes
import np.minarybook.model.dto.isbn.ISBNSeojiRes
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class ISBNServiceImpl(
    @Value("\${ISBN.key}")
    private val key: String
): ISBNService {

    private val requestUrl: String = "https://www.nl.go.kr/seoji/SearchApi.do"

    override fun get(isbn: String): ResponseEntity<ISBNGetRes> {

        val restTemplate = RestTemplate()

        val requestUrlWithParam = UriComponentsBuilder.fromHttpUrl(requestUrl)
            .queryParam("cert_key", key)
            .queryParam("result_style", "json")
            .queryParam("page_no", 1)
            .queryParam("page_size", 1)
            .queryParam("isbn", isbn)
            .build(true).toString()

        val response = restTemplate.getForObject(requestUrlWithParam, ISBNSeojiRes::class.java)

        val isbnBookRes = response?.docs?.get(0)

        return ResponseEntity.ok(
            ISBNGetRes(
                title = isbnBookRes?.TITLE,
                price = isbnBookRes?.PRE_PRICE,
                author = isbnBookRes?.AUTHOR,
                img = isbnBookRes?.TITLE_URL,
                publicationDate = isbnBookRes?.REAL_PUBLISH_DATE,
                publisher = isbnBookRes?.PUBLISHER,
                isbn = isbnBookRes?.EA_ISBN
            )
        )
    }
}