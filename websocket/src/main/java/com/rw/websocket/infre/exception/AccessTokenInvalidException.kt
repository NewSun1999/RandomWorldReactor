package com.rw.websocket.infre.exception

import org.springframework.http.HttpStatus

class AccessTokenInvalidException(s: String) : IllegalArgumentException(s) {
    val status = HttpStatus.UNAUTHORIZED
}
