package org.knowm.xchange.coindcx.dto;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.util.Date;

public class CoindcxMarketOrder extends MarketOrder {

    private CoindcxOrderStatusResponse response = null;

    public CoindcxMarketOrder(OrderType type, BigDecimal originalAmount, Instrument instrument, String id, Date timestamp, BigDecimal averagePrice, BigDecimal cumulativeAmount, BigDecimal fee, OrderStatus status, String userReference) {
        super(type, originalAmount, instrument, id, timestamp, averagePrice, cumulativeAmount, fee, status, userReference);
    }

    public CoindcxMarketOrder(OrderType type, BigDecimal originalAmount, Instrument instrument, String id, Date timestamp, BigDecimal averagePrice, BigDecimal cumulativeAmount, BigDecimal fee, OrderStatus status) {
        super(type, originalAmount, instrument, id, timestamp, averagePrice, cumulativeAmount, fee, status);
    }

    public CoindcxMarketOrder(OrderType type, BigDecimal originalAmount, Instrument instrument, String id, Date timestamp) {
        super(type, originalAmount, instrument, id, timestamp);
    }

    public CoindcxMarketOrder(OrderType type, BigDecimal originalAmount, Instrument instrument, Date timestamp) {
        super(type, originalAmount, instrument, timestamp);
    }

    public CoindcxMarketOrder(OrderType type, BigDecimal originalAmount, Instrument instrument) {
        super(type, originalAmount, instrument);
    }

    public CoindcxOrderStatusResponse getResponse() {
        return response;
    }

    public void setResponse(CoindcxOrderStatusResponse value) {
        response = value;
    }

    public static class Builder extends MarketOrder.Builder {

        public Builder(OrderType orderType, CurrencyPair currencyPair) {
            super(orderType, currencyPair);
        }

        public CoindcxMarketOrder build() {
            final CoindcxMarketOrder order =
                    new CoindcxMarketOrder(
                            orderType, originalAmount, (CurrencyPair) instrument, id, timestamp);
            order.setOrderFlags(flags);
            return order;
        }
    }

}
