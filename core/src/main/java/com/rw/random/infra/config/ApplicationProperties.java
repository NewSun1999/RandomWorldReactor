package com.rw.random.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;

@ConfigurationProperties("random-world")
public class ApplicationProperties {

    private Integer eventChannelSize = 6000;
    private Long workId = 1L;

    private Long loadTestFishCount = 1000L;

    private Long dataCenterId = 1L;

    private Map<String, ObjectInitProperties> objects = new HashMap<>();

    public Map<String, ObjectInitProperties> getObjects() {
        return objects;
    }

    public List<String> messageTypeNeedToSend = Collections.singletonList("BeAtk");

    public void setObjects(Map<String, ObjectInitProperties> objects) {
        this.objects = objects;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(Long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public Long getLoadTestFishCount() {
        return loadTestFishCount;
    }

    public void setLoadTestFishCount(Long loadTestFishCount) {
        this.loadTestFishCount = loadTestFishCount;
    }

    public List<String> getMessageTypeNeedToSend() {
        return messageTypeNeedToSend;
    }

    public void setMessageTypeNeedToSend(List<String> messageTypeNeedToSend) {
        this.messageTypeNeedToSend = messageTypeNeedToSend;
    }

    public Integer getEventChannelSize() {
        return eventChannelSize;
    }

    public void setEventChannelSize(Integer eventChannelSize) {
        this.eventChannelSize = eventChannelSize;
    }

    public static class ObjectInitProperties {
        private String topic;
        private Integer heal;
        private Integer atk;
        private Integer money;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public Integer getHeal() {
            return heal;
        }

        public void setHeal(Integer heal) {
            this.heal = heal;
        }

        public Integer getAtk() {
            return atk;
        }

        public void setAtk(Integer atk) {
            this.atk = atk;
        }

        public Integer getMoney() {
            return money;
        }

        public void setMoney(Integer money) {
            this.money = money;
        }
    }


}
