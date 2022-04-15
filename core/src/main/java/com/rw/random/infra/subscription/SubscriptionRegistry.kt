package com.rw.random.infra.subscription

import cn.hutool.core.collection.ConcurrentHashSet
import com.rw.random.domain.entity.RWEvent
import com.rw.random.domain.entity.RWObject
import com.rw.random.domain.entity.RWZone
import com.rw.random.domain.entity.obj.Being
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer
import java.util.stream.Stream
import kotlin.reflect.KClass

@Component
open class SubscriptionRegistry {

    private val log: Logger = LoggerFactory.getLogger(javaClass)
    private val zoneSubscriptions = ConcurrentHashMap<String, ConcurrentHashSet<Long>>()
    private val objectHandler = ConcurrentHashMap<Long, Consumer<RWEvent>>()
    private val objectTopic = ConcurrentHashMap<Long, String>()
    private val topicZone = ConcurrentHashMap<String, RWZone>()

    fun subscribe(obj: RWObject, topic: String): Boolean {
        if (zoneSubscriptions.containsKey(topic)) {
            zoneSubscriptions[topic]!!.add(obj.id)
            objectTopic[obj.id] = topic
            objectHandler[obj.id] = obj.handler
            return true
        }
        return false
    }

    fun unsubscribe(obj: RWObject, topic: String) {
        if (zoneSubscriptions.containsKey(topic)) {
            zoneSubscriptions[topic]!!.remove(obj.id)
            objectTopic.remove(obj.id)
            objectHandler.remove(obj.id)
        }
    }

    fun registerZone(zone: RWZone) {
        log.info("Zone created ${zone.zoneName}")
        topicZone[zone.getZoneTopic()] = zone
        zoneSubscriptions[zone.getZoneTopic()] = ConcurrentHashSet()
    }

    fun findZoneByTopic(topic: String): RWZone? {
        return topicZone[topic]
    }

    fun findAllObjByTopic(topic: String): Flux<Long> {
        return Flux.fromIterable(zoneSubscriptions[topic] ?: setOf())
    }

    fun findConsumerByObjId(objectId: Long): Optional<Consumer<RWEvent>> {
        return Optional.ofNullable(objectHandler[objectId])
    }

    fun findObjByTopic(topic: String, clazz: KClass<out RWObject>): Stream<Being> {
        if (!topicZone.containsKey(topic)) {
            return Stream.empty()
        }
        return topicZone[topic]!!.getAllObjByType(clazz).map { it as Being }
    }

}
