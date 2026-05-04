package com.ibm.inventory_management.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ibm.inventory_management.models.StockEvent;

@Component
public class EventStore {

    private final List<StockEvent> events = new ArrayList<>();

    public void append(StockEvent event) {
        events.add(event);
    }

    public List<StockEvent> getAll() {
        return Collections.unmodifiableList(events);
    }

    public List<StockEvent> getByItemId(String itemId) {
        return events.stream()
                .filter(e -> itemId.equals(e.getItemId()))
                .collect(Collectors.toList());
    }
}
