package org.knowm.xchange.blockchain.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.blockchain.serializer.BlockchainCurrencyPairSerializer;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

import java.math.BigDecimal;
import java.util.Date;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;
import static org.knowm.xchange.blockchain.BlockchainConstants.BUY;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockchainOrder {

    @JsonSerialize(using = BlockchainCurrencyPairSerializer.class)
    @JsonDeserialize(using = CurrencyPairDeserializer.class)
    private final CurrencyPair symbol;
    private final Long exOrdId;
    private final String clOrdId;
    private final String ordType;
    private final String ordStatus;
    private final BigDecimal orderQty;
    private final String side;
    private final String text;
    private final BigDecimal price;
    private final BigDecimal lastShares;
    private final BigDecimal lastPx;
    private final BigDecimal leavesQty;
    private final BigDecimal cumQty;
    private final BigDecimal avgPx;
    private final Date timestamp;

    @JsonIgnore
    public boolean isMarketOrder() {
        return MARKET.equals(ordType);
    }

    @JsonIgnore
    public boolean isLimitOrder() {
        return LIMIT.equals(ordType);
    }

    @JsonIgnore
    public boolean isBuyer() {
        return BUY.equals(this.side.toUpperCase());
    }

    @JsonIgnore
    public Order.Builder getOrderBuilder(){
        final Order.OrderType orderType = this.isBuyer() ? Order.OrderType.BID : Order.OrderType.ASK;
        final CurrencyPair symbol = this.getSymbol();
        Order.Builder builder;

        if (this.isMarketOrder()) {
            builder = new MarketOrder.Builder(orderType, symbol);
        } else if (this.isLimitOrder()){
            builder = new LimitOrder.Builder(orderType, symbol).limitPrice(this.getPrice());
        } else {
            builder = new StopOrder.Builder(orderType, symbol).stopPrice(this.getPrice());
        }

        return builder;
    }

}
