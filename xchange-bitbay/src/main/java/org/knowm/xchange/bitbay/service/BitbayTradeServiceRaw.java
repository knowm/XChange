package org.knowm.xchange.bitbay.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.dto.trade.BitbayCancelResponse;
import org.knowm.xchange.bitbay.dto.trade.BitbayOrder;
import org.knowm.xchange.bitbay.dto.trade.BitbayTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author Z. Dolezal */
public class BitbayTradeServiceRaw extends BitbayBaseService {

  BitbayTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BitbayOrder> getBitbayOpenOrders() throws IOException, ExchangeException {
    return bitbayAuthenticated.orders(apiKey, sign, exchange.getNonceFactory());
  }

  public List<Map> getBitbayTransactions(CurrencyPair currencyPair)
      throws IOException, ExchangeException {
    return bitbayAuthenticated.transactions(
        apiKey,
        sign,
        exchange.getNonceFactory(),
        currencyPair == null
            ? null
            : currencyPair.base.toString() + "-" + currencyPair.counter.toString());
  }

  public BitbayTradeResponse placeBitbayOrder(LimitOrder order) throws IOException {
    String currency = order.getCurrencyPair().base.toString();
    String paymentCurrency = order.getCurrencyPair().counter.toString();
    String type = order.getType() == Order.OrderType.ASK ? "ask" : "bid";

    BitbayTradeResponse response =
        bitbayAuthenticated.trade(
            apiKey,
            sign,
            exchange.getNonceFactory(),
            type,
            currency,
            order.getOriginalAmount(),
            paymentCurrency,
            order.getLimitPrice());

    checkError(response);

    return response;
  }

  public BitbayCancelResponse cancelBitbayOrder(long id) throws IOException {
    BitbayCancelResponse response =
        bitbayAuthenticated.cancel(apiKey, sign, exchange.getNonceFactory(), id);

    checkError(response);

    return response;
  }
}
