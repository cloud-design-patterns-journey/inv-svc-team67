package com.ibm.inventory_management.models;

import java.time.LocalDateTime;

public class StockEvent {
    private String eventId;
    private String type; // STOCK_ADDED, STOCK_UPDATED, STOCK_DELETED
    private String itemId;
    private String actor;
    private LocalDateTime timestamp;
    private StockItem payload;

    public StockEvent() {
        super();
    }

    public StockEvent(String eventId, String type, String itemId, String actor, LocalDateTime timestamp, StockItem payload) {
        this.eventId = eventId;
        this.type = type;
        this.itemId = itemId;
        this.actor = actor;
        this.timestamp = timestamp;
        this.payload = payload;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public StockEvent withEventId(String eventId) {
        this.setEventId(eventId);
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StockEvent withType(String type) {
        this.setType(type);
        return this;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public StockEvent withItemId(String itemId) {
        this.setItemId(itemId);
        return this;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public StockEvent withActor(String actor) {
        this.setActor(actor);
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public StockEvent withTimestamp(LocalDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public StockItem getPayload() {
        return payload;
    }

    public void setPayload(StockItem payload) {
        this.payload = payload;
    }

    public StockEvent withPayload(StockItem payload) {
        this.setPayload(payload);
        return this;
    }
}
