package com.ibm.inventory_management.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.ibm.inventory_management.models.StockEvent;
import com.ibm.inventory_management.models.StockItem;

@Service
public class StockItemService implements StockItemApi {

    private final EventStore eventStore;

    public StockItemService(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @PostConstruct
    public void init() {
        addStockItem("Item 1", "Sony", 10.5, 100);
        addStockItem("Item 2", "Insignia", 100.5, 150);
        addStockItem("Item 3", "Panasonic", 1000.0, 10);
    }

    @Override
    public List<StockItem> listStockItems() {
        Map<String, StockItem> projection = new LinkedHashMap<>();
        for (StockEvent event : eventStore.getAll()) {
            switch (event.getType()) {
                case "STOCK_ADDED":
                case "STOCK_UPDATED":
                    projection.put(event.getItemId(), event.getPayload());
                    break;
                case "STOCK_DELETED":
                    projection.remove(event.getItemId());
                    break;
                default:
                    break;
            }
        }
        return new ArrayList<>(projection.values());
    }

    @Override
    public void addStockItem(String name, String manufacturer, double price, int stock) {
        String itemId = UUID.randomUUID().toString();
        StockItem item = new StockItem(itemId)
                .withName(name)
                .withManufacturer(manufacturer)
                .withPrice(price)
                .withStock(stock);

        StockEvent event = new StockEvent()
                .withEventId(UUID.randomUUID().toString())
                .withType("STOCK_ADDED")
                .withItemId(itemId)
                .withActor("system")
                .withTimestamp(LocalDateTime.now())
                .withPayload(item);

        eventStore.append(event);
    }

    @Override
    public void updateStockItem(String id, String name, String manufacturer, double price, int stock) {
        // Resolve current state of the item to allow partial updates
        StockItem current = listStockItems().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (current == null) {
            System.out.println("Item not found: " + id);
            return;
        }

        StockItem updated = new StockItem(id)
                .withName(name != null ? name : current.getName())
                .withManufacturer(manufacturer != null ? manufacturer : current.getManufacturer())
                .withPrice(price)
                .withStock(stock);

        StockEvent event = new StockEvent()
                .withEventId(UUID.randomUUID().toString())
                .withType("STOCK_UPDATED")
                .withItemId(id)
                .withActor("system")
                .withTimestamp(LocalDateTime.now())
                .withPayload(updated);

        eventStore.append(event);
    }

    @Override
    public void deleteStockItem(String id) {
        StockEvent event = new StockEvent()
                .withEventId(UUID.randomUUID().toString())
                .withType("STOCK_DELETED")
                .withItemId(id)
                .withActor("system")
                .withTimestamp(LocalDateTime.now())
                .withPayload(null);

        eventStore.append(event);
    }
}
