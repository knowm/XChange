package org.knowm.xchange.binance.futures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.dto.trade.OrderSide;

import java.math.BigDecimal;
import java.util.Date;

public class BinanceFuturesTrade {
    public final boolean buyer;
    public final BigDecimal commission;
    public final String commissionAsset;
    public final long id;
    public final boolean maker;
    public final long orderId;
    public final BigDecimal price;
    public final BigDecimal qty;
    public final BigDecimal quoteQty;
    public final BigDecimal realizedPnl;
    public final OrderSide side;
    public final PositionSide positionSide;
    public final String symbol;
    public final long time;

    public BinanceFuturesTrade(
            @JsonProperty("buyer") boolean buyer,
            @JsonProperty("commission") BigDecimal commission,
            @JsonProperty("commissionAsset") String commissionAsset,
            @JsonProperty("id") long id,
            @JsonProperty("maker") boolean maker,
            @JsonProperty("orderId") long orderId,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("qty") BigDecimal qty,
            @JsonProperty("quoteQty") BigDecimal quoteQty,
            @JsonProperty("realizedPnl") BigDecimal realizedPnl,
            @JsonProperty("side") OrderSide side,
            @JsonProperty("positionSide") PositionSide positionSide,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("time") long time) {
        this.buyer = buyer;
        this.commission = commission;
        this.commissionAsset = commissionAsset;
        this.id = id;
        this.maker = maker;
        this.orderId = orderId;
        this.price = price;
        this.qty = qty;
        this.quoteQty = quoteQty;
        this.realizedPnl = realizedPnl;
        this.side = side;
        this.positionSide = positionSide;
        this.symbol = symbol;
        this.time = time;
    }

    public Date getTime() {
        return new Date(time);
    }
}
