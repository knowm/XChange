package org.knowm.xchange.fcoin;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class FCoinManualExample {

    public static void main(String[] args) throws IOException {
        ExchangeSpecification spec = (new FCoinExchange()).getDefaultExchangeSpecification();
        spec.setApiKey("07d1c76724ba4bfb99e0dca152371954");
        spec.setSecretKey("7f1796db239144f89f87d13f36546fa0");
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);
        LimitOrder order = new LimitOrder(Order.OrderType.ASK, new BigDecimal("6"), new CurrencyPair(new Currency("FT"), Currency.BTC), null, new Date(), new BigDecimal("0.00012"));
        exchange.getTradeService().placeLimitOrder(order);
    }

}
