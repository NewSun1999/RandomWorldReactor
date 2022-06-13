package com.rw.websocket.app.usecase

import com.rw.random.common.constants.BeingStatus
import com.rw.websocket.app.service.UserService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

interface EatFishUseCase {

    fun runCase(userName: String, fishId: Long): Mono<Boolean>

}

@Component
open class EatFishUseCaseImpl(
    private val userService: UserService,
) : EatFishUseCase {
    override fun runCase(userName: String, fishId: Long): Mono<Boolean> {
        return userService.getUserFish(fishId)
            .filterWhen {
                userService.getUserByUserName(userName)
                    .map { user ->
                        user.id == it.userId
                    }
            }
            .filter { it.fishStatus == BeingStatus.SLEEP.ordinal }
            .flatMap {
                userService.eatFish(fishId, userName)
            }
    }
}
