package org.knowm.xchange.cexio.dto;

import org.knowm.xchange.cexio.dto.trade.CexIOOrder;

import java.math.BigDecimal;

public class PlaceOrderRequest extends CexIORequest {
    public final String tradeableIdentifier;
    public final String currency;
    public final CexIOOrder.Type type;
    public final BigDecimal price;
    public final BigDecimal amount;

    public PlaceOrderRequest(String tradeableIdentifier, String currency, CexIOOrder.Type type, BigDecimal price, BigDecimal amount) {
        this.tradeableIdentifier = tradeableIdentifier;
        this.currency = currency;
        this.type = type;
        this.price = price;
        this.amount = amount;
    }
}
