package info.bitrich.xchangestream.lgo.dto;

import info.bitrich.xchangestream.lgo.domain.LgoBatchOrderEvent;
import org.knowm.xchange.dto.Order;

import java.util.List;

public class LgoBatchUpdate {

    private final List<Order> updatedOrders;
    private final List<LgoBatchOrderEvent> events;
    private final long batchId;
    private final String type;

    public LgoBatchUpdate(List<Order> updatedOrders, List<LgoBatchOrderEvent> events, long batchId, String type) {
        this.updatedOrders = updatedOrders;
        this.events = events;
        this.batchId = batchId;
        this.type = type;
    }

    public List<Order> getUpdatedOrders() {
        return updatedOrders;
    }

    public List<LgoBatchOrderEvent> getEvents() {
        return events;
    }

    public long getBatchId() {
        return batchId;
    }

    public String getType() {
        return type;
    }
}
