package com.ibm.inventory_management.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ibm.inventory_management.models.StockEvent;
import com.ibm.inventory_management.models.StockItem;
import com.ibm.inventory_management.services.EventStore;
import com.ibm.inventory_management.services.StockItemApi;

@RestController
public class StockItemController {

    private final StockItemApi service;
    private final EventStore eventStore;

    public StockItemController(StockItemApi service, EventStore eventStore) {
        this.service = service;
        this.eventStore = eventStore;
    }

    @GetMapping(path = "/stock-items", produces = "application/json")
    public List<StockItem> listStockItems() {
        return this.service.listStockItems();
    }

    @PostMapping(path = "/stock-item")
    public void addStockItem(@RequestParam String name, @RequestParam String manufacturer, @RequestParam float price,
            @RequestParam int stock) {
        this.service.addStockItem(name, manufacturer, price, stock);
    }

    @PutMapping(path = "/stock-item/{id}")
    public void updateStockItem(@PathVariable("id") String id, @RequestParam String name,
            @RequestParam String manufacturer, @RequestParam float price, @RequestParam int stock) {
        this.service.updateStockItem(id, name, manufacturer, price, stock);
    }

    @DeleteMapping(path = "/stock-item/{id}")
    public void deleteStockItem(@PathVariable("id") String id) {
        this.service.deleteStockItem(id);
    }

    @GetMapping(path = "/stock-items/audit", produces = "application/json")
    public List<StockEvent> getAuditLog() {
        return this.eventStore.getAll();
    }

    @GetMapping(path = "/stock-items/{id}/history", produces = "application/json")
    public List<StockEvent> getItemHistory(@PathVariable("id") String id) {
        return this.eventStore.getByItemId(id);
    }
}
