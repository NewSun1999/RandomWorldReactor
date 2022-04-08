package com.rw.websocket.domain.entity

import com.fasterxml.jackson.annotation.JsonProperty

class UserWithProperty(
    @JsonProperty(USER_ID_FIELD)
    val userId: Long,
    @JsonProperty(USER_NAME_FIELD)
    val userName: String,
    @JsonProperty(ACCESS_TOKEN_FIELD)
    val accessToken: String,
    @JsonProperty(EXP_FIELD)
    var exp: Long,
    @JsonProperty(MONEY_FIELD)
    var money: Long,
    @JsonProperty(FISH_MAX_COUNT_FIELD)
    var fishMaxCount: Long,
) {

    companion object {
        const val USER_ID_FIELD = "user_id"
        const val USER_NAME_FIELD = "user_name"
        const val ACCESS_TOKEN_FIELD = "access_token"
        const val EXP_FIELD = "exp"
        const val MONEY_FIELD = "money"
        const val FISH_MAX_COUNT_FIELD = "fish_max_count"
    }
}