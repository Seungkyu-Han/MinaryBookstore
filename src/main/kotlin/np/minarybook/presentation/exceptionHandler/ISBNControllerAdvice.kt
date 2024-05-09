package np.minarybook.presentation.exceptionHandler

import np.minarybook.presentation.ISBNController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [ISBNController::class])
class ISBNControllerAdvice {

    @ExceptionHandler(IndexOutOfBoundsException::class)
    fun indexOutOfBoundsExceptionHandler(indexOutOfBoundsException: IndexOutOfBoundsException): ResponseEntity<String>{
        return ResponseEntity("해당 도서가 없습니다.", HttpStatus.NOT_FOUND)
    }
}