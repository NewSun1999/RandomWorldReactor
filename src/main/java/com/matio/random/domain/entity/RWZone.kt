package com.matio.random.domain.entity

import com.matio.random.infra.constants.RWConstants
import com.matio.random.infra.subscription.SubscriptionRegistry
import java.util.concurrent.CopyOnWriteArraySet
import java.util.stream.Stream
import kotlin.math.log
import kotlin.reflect.KClass

abstract class RWZone(
    val zoneId: Long,
    val zoneName: String,
    val neighbor: List<RWZone> = listOf(),
    var money: Long = 100000,
    val moneyIncrSpeed: Long = 3000,
    val maxObjSize: Long = 100
) {

    protected val objSet = CopyOnWriteArraySet<RWObject>()

    abstract fun enterZone(obj: RWObject): Boolean

    abstract fun handlerMsg(event: RWEvent)

    abstract fun moreMoney()

    fun getZoneTopic(): String {
        return RWConstants.TOPIC_PREFIX + zoneId
    }

    fun getAllObjByType(clazz: KClass<out RWObject>): Stream<RWObject> {
        return objSet.stream()
            .filter {
                it::class == clazz
            }
    }
}

open class SimpleZone(
    zoneId: Long,
    zoneName: String,
    money: Long,
    moneyIncrSpeed: Long,
    private val subscription: SubscriptionRegistry
) : RWZone(zoneId, zoneName, money = money, moneyIncrSpeed = moneyIncrSpeed) {

    override fun enterZone(obj: RWObject): Boolean {
        if (objSet.size >= maxObjSize) {
            return false
        }
        obj.topic = this.getZoneTopic()
        if (subscription.subscribe(obj, getZoneTopic())) {
            objSet.add(obj)
        }
        return true
    }

    override fun handlerMsg(event: RWEvent) {
        TODO("Not yet implemented")
    }

    override fun moreMoney() {
        money += moneyIncrSpeed
    }

}