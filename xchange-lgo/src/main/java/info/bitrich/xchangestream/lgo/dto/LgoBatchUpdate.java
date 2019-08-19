package info.bitrich.xchangestream.lgo.dto;

import info.bitrich.xchangestream.lgo.domain.LgoOrderEvent;
import org.knowm.xchange.dto.Order;

import java.util.List;

public class LgoBatchUpdate {

    private final List<Order> updatedOrders;
    private final List<LgoOrderEvent> events;
    private final long batchId;
    private final String type;

    public LgoBatchUpdate(List<Order> updatedOrders, List<LgoOrderEvent> events, long batchId, String type) {
        this.updatedOrders = updatedOrders;
        this.events = events;
        this.batchId = batchId;
        this.type = type;
    }

    public List<Order> getUpdatedOrders() {
        return updatedOrders;
    }

    public List<LgoOrderEvent> getEvents() {
        return events;
    }

    public long getBatchId() {
        return batchId;
    }

    public String getType() {
        return type;
    }
}
