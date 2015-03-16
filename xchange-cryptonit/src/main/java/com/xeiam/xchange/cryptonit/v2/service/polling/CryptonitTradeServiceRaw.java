package com.xeiam.xchange.cryptonit.v2.service.polling;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.Cryptonit;
import com.xeiam.xchange.cryptonit.v2.CryptonitAuth;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrder;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.trade.CancelOrderRequest;
import com.xeiam.xchange.cryptonit.v2.dto.trade.NewOrderRequest;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Yar.kh on 02/10/14.
 */
public class CryptonitTradeServiceRaw extends CryptonitBasePollingService {
    protected final String apiKey;
    protected final CryptonitAuth cryptonitAuth;

    public CryptonitTradeServiceRaw(Exchange exchange) {
      super(exchange);

      this.cryptonitAuth = RestProxyFactory.createProxy(CryptonitAuth.class, exchange.getExchangeSpecification().getSslUri());
      this.apiKey = exchange.getExchangeSpecification().getApiKey();
    }

    protected CryptonitOrders getPlacedOrders() throws IOException {
        try {
            return cryptonitAuth.getOrders("Bearer " + apiKey, CryptonitOrder.OrderType.placed, true, 1000l);
        } catch (JsonMappingException jme) {
            // returned empty list []
        }
        return null;
    }

    protected CryptonitOrders getFilledOrders() throws IOException {
        try {
            return cryptonitAuth.getOrders("Bearer " + apiKey, CryptonitOrder.OrderType.filled, true,  1000l);
        } catch (JsonMappingException jme) {
            // returned empty list []
        }
        return null;
    }

    protected CryptonitOrders placeOrder(LimitOrder limitOrder) throws IOException {

        String bidCurrency;
        String askCurrency;
        BigDecimal bidAmount;
        BigDecimal askAmount;

        if (limitOrder.getType() == Order.OrderType.BID) {
            bidCurrency = limitOrder.getCurrencyPair().baseSymbol;
            askCurrency = limitOrder.getCurrencyPair().counterSymbol;

            askAmount = limitOrder.getTradableAmount();
            bidAmount = limitOrder.getTradableAmount().multiply(limitOrder.getLimitPrice());

        } else {
            bidCurrency = limitOrder.getCurrencyPair().counterSymbol;
            askCurrency = limitOrder.getCurrencyPair().baseSymbol;

            askAmount = limitOrder.getTradableAmount().multiply(limitOrder.getLimitPrice());
            bidAmount = limitOrder.getTradableAmount();
        }

        Long timestamp = new Date().getTime();

        NewOrderRequest newOrderRequest = new NewOrderRequest(bidCurrency, askCurrency,
                bidAmount, askAmount, timestamp, timestamp);

        try {
            return cryptonitAuth.placeOrder("Bearer " + apiKey, newOrderRequest);
        } catch (JsonMappingException jme) {
            // returned empty list []
        }
        return null;
    }

    protected boolean cancelCryptonitOrder(String id) throws IOException {
        try {
            CancelOrderRequest cancel = new CancelOrderRequest(id, "cancel", System.currentTimeMillis());
            List<Boolean> booleans = cryptonitAuth.cancelOrder("Bearer " + apiKey, cancel);
            return booleans != null && !booleans.isEmpty() && booleans.get(0);
        } catch (JsonMappingException jme) {
            // returned empty list []
        }
        return false;
    }
}
