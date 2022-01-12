package me.jungseob.apps.mytodoapp.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MyNotFoundException::class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    fun handleMyNotFoundException(e: MyNotFoundException): ErrorResponse {
        return ErrorResponse(e.message!!)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): ErrorResponse {
        return ErrorResponse("Unexpected exception")
    }

    data class ErrorResponse(
        val message: String
    )
}
