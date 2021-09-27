package info.bitrich.xchangestream.coinbasepro.dto;

import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;

import java.math.BigDecimal;

public class CoinbaseProOrderBuilder {
    private String id;
    private BigDecimal price;
    private BigDecimal size;
    private String productId;
    private String clientOid;
    private String side;
    private String createdAt;
    private String doneAt;
    private BigDecimal filledSize;
    private BigDecimal fillFees;
    private String status;
    private boolean settled;
    private String type;
    private String doneReason;
    private BigDecimal executedValue;
    private String stop;
    private BigDecimal stopPrice;

    public static CoinbaseProOrder from(CoinbaseProWebSocketTransaction t) {
        switch(t.getType()) {
            case "received":
                return new CoinbaseProOrder(
                        t.getOrderId(),
                        t.getPrice(),
                        t.getSize(),
                        t.getProductId(),
                        t.getClientOid(),
                        t.getSide(),
                        t.getTime(),
                        null,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        "pending",
                        false,
                        t.getOrderType(),
                        null,
                        BigDecimal.ZERO,
                        null,
                        BigDecimal.ZERO);
            case "activate":
                return new CoinbaseProOrder(
                        t.getOrderId(),
                        t.getLimitPrice(),
                        t.getSize(),
                        t.getProductId(),
                        t.getClientOid(),
                        t.getSide(),
                        t.getTime(),
                        null,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        "active",
                        false,
                        "limit",
                        null,
                        BigDecimal.ZERO,
                        t.getStopType(),
                        t.getStopPrice());
            case "open":
                new CoinbaseProOrder(
                        t.getOrderId(),
                        t.getPrice(),
                        t.getRemainingSize(),
                        t.getProductId(),
                        t.getClientOid(),
                        t.getSide(),
                        t.getTime(),
                        null,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        "open",
                        false,
                        t.getOrderType(),
                        null,
                        BigDecimal.ZERO,
                        null,
                        BigDecimal.ZERO);
        }
        return null;
    }

    public static CoinbaseProOrderBuilder from(CoinbaseProOrder order, CoinbaseProWebSocketTransaction t) {
        switch(t.getType()) {
            case "open":
                return CoinbaseProOrderBuilder.from(order)
                        .status("open");
            case "match": {
                return CoinbaseProOrderBuilder.from(order)
                        .fillFees(order.getFillFees().add(t.getFee()))
                        .filledSize(order.getFilledSize().add(t.getSize()))
                        .executedValue(order.getExecutedValue().add(t.getSize().multiply(t.getPrice())));
            }
            case "done":
                return CoinbaseProOrderBuilder.from(order)
                        .status("done")
                        .doneAt(t.getTime())
                        .doneReason(t.getReason());

        }
        return CoinbaseProOrderBuilder.from(order);
    }

    public static CoinbaseProOrderBuilder from(CoinbaseProOrder order) {
        return new CoinbaseProOrderBuilder()
                .id(order.getId())
                .price(order.getPrice())
                .size(order.getSize())
                .productId(order.getProductId())
                .clientOid(order.getClientOid())
                .side(order.getSide())
                .createdAt(order.getCreatedAt())
                .doneAt(order.getDoneAt())
                .filledSize(order.getFilledSize())
                .fillFees(order.getFillFees())
                .status(order.getStatus())
                .settled(order.isSettled())
                .type(order.getType())
                .doneReason(order.getDoneReason())
                .executedValue(order.getExecutedValue())
                .stop(order.getStop())
                .stopPrice(order.getStopPrice());
    }

    public CoinbaseProOrderBuilder id(String id) {
        this.id = id;
        return this;
    }

    public CoinbaseProOrderBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CoinbaseProOrderBuilder size(BigDecimal size) {
        this.size = size;
        return this;
    }

    public CoinbaseProOrderBuilder productId(String productId) {
        this.productId = productId;
        return this;
    }

    public CoinbaseProOrderBuilder clientOid(String clientOid) {
        this.clientOid = clientOid;
        return this;
    }

    public CoinbaseProOrderBuilder side(String side) {
        this.side = side;
        return this;
    }

    public CoinbaseProOrderBuilder createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CoinbaseProOrderBuilder doneAt(String doneAt) {
        this.doneAt = doneAt;
        return this;
    }

    public CoinbaseProOrderBuilder filledSize(BigDecimal filledSize) {
        this.filledSize = filledSize;
        return this;
    }

    public CoinbaseProOrderBuilder fillFees(BigDecimal fillFees) {
        this.fillFees = fillFees;
        return this;
    }

    public CoinbaseProOrderBuilder status(String status) {
        this.status = status;
        return this;
    }

    public CoinbaseProOrderBuilder settled(boolean settled) {
        this.settled = settled;
        return this;
    }

    public CoinbaseProOrderBuilder type(String type) {
        this.type = type;
        return this;
    }

    public CoinbaseProOrderBuilder doneReason(String doneReason) {
        this.doneReason = doneReason;
        return this;
    }

    public CoinbaseProOrderBuilder executedValue(BigDecimal executedValue) {
        this.executedValue = executedValue;
        return this;
    }

    public CoinbaseProOrderBuilder stop(String stop) {
        this.stop = stop;
        return this;
    }

    public CoinbaseProOrderBuilder stopPrice(BigDecimal stopPrice) {
        this.stopPrice = stopPrice;
        return this;
    }

    public CoinbaseProOrder build() {
        return new CoinbaseProOrder(
                id,
                price,
                size,
                productId,
                clientOid,
                side,
                createdAt,
                doneAt,
                filledSize,
                fillFees,
                status,
                settled,
                type,
                doneReason,
                executedValue,
                stop,
                stopPrice);
    }
}
