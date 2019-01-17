package org.knowm.xchange.btcturk.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderMethods;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.btcturk.dto.BTCTurkSort;
import org.knowm.xchange.btcturk.dto.account.BTCTurkUserTransactions;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkExchangeResult;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkOpenOrders;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkOrder;
import org.knowm.xchange.currency.CurrencyPair;

/** @author mertguner */
public class BTCTurkTradeServiceRaw extends BTCTurkBaseService {

  public BTCTurkTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BTCTurkOpenOrders> getOpenOrders(CurrencyPair pair) throws IOException {

    return btcTurk.getOpenOrders(
        pair.toString().replace("/", ""),
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory(),
        signatureCreator);
  }

  public List<BTCTurkOpenOrders> getOpenOrdersRaw() throws IOException {

    List<BTCTurkOpenOrders> openOrdersRaw = new ArrayList<BTCTurkOpenOrders>();

    for (BTCTurkTicker ticker : btcTurk.getTicker()) {
      for (BTCTurkOpenOrders order : getOpenOrders(ticker.getPair())) {
        openOrdersRaw.add(order);
      }
    }

    return openOrdersRaw;
  }

  public List<BTCTurkUserTransactions> getUserTransactions() throws IOException {

    return btcTurk.getUserTransactions(
        0,
        50,
        BTCTurkSort.SORT_ASCENDING,
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory(),
        signatureCreator);
  }

  public boolean cancelOrder(String id) throws IOException {
    return btcTurk
        .setCancelOrder(
            id,
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(),
            signatureCreator)
        .getResult();
  }

  public BTCTurkExchangeResult placeMarketOrder(
      BigDecimal total, CurrencyPair pair, BTCTurkOrderTypes orderTypes) throws IOException {
    return postExchange(
        total, BigDecimal.ZERO, BigDecimal.ZERO, pair, BTCTurkOrderMethods.MARKET, orderTypes);
  }

  public BTCTurkExchangeResult placeLimitOrder(
      BigDecimal amount, BigDecimal price, CurrencyPair pair, BTCTurkOrderTypes orderTypes)
      throws IOException {
    return postExchange(
        amount, price, BigDecimal.ZERO, pair, BTCTurkOrderMethods.LIMIT, orderTypes);
  }

  public BTCTurkExchangeResult placeStopLimitOrder(
      BigDecimal amount,
      BigDecimal price,
      BigDecimal triggerPrice,
      CurrencyPair pair,
      BTCTurkOrderTypes orderTypes)
      throws IOException {
    return postExchange(
        amount, price, triggerPrice, pair, BTCTurkOrderMethods.STOP_LIMIT, orderTypes);
  }

  protected String getLocalizedBigDecimalValue(BigDecimal input) {
    final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
    numberFormat.setGroupingUsed(true);
    numberFormat.setMaximumFractionDigits(8);
    numberFormat.setMinimumFractionDigits(2);
    return numberFormat.format(input);
  }

  private BTCTurkExchangeResult postExchange(
      BigDecimal total_amount,
      BigDecimal price,
      BigDecimal triggerPrice,
      CurrencyPair pair,
      BTCTurkOrderMethods orderMethod,
      BTCTurkOrderTypes orderTypes)
      throws IOException {

    BTCTurkOrder order = new BTCTurkOrder();

    order.setOrderMethod(orderMethod);
    order.setOrderType(orderTypes);
    order.setPairSymbol(pair);

    String[] _zero = getLocalizedBigDecimalValue(BigDecimal.ZERO).split("\\.");
    String[] _price = _zero;
    if (orderMethod.equals(BTCTurkOrderMethods.LIMIT))
      _price = getLocalizedBigDecimalValue(price).split("\\.");
    order.setPrice(_price[0]);
    order.setPricePrecision(_price[1]);

    String[] _triggerPrice = _zero;
    if (orderMethod.equals(BTCTurkOrderMethods.STOP_LIMIT)
        || orderMethod.equals(BTCTurkOrderMethods.STOP_MARKET))
      _triggerPrice = getLocalizedBigDecimalValue(triggerPrice).split("\\.");
    order.setTriggerPrice(_triggerPrice[0]);
    order.setTriggerPricePrecision(_triggerPrice[1]);

    String[] _total = getLocalizedBigDecimalValue(total_amount).split("\\.");
    String[] _amount = _zero;
    if (orderTypes.equals(BTCTurkOrderTypes.SELL)) {
      _amount = getLocalizedBigDecimalValue(total_amount).split("\\.");
      _total = _zero;
    }
    order.setTotal(_total[0]);
    order.setTotalPrecision(_total[1]);

    order.setAmount(_amount[0]);
    order.setAmountPrecision(_amount[1]);

    order.setDenominatorPrecision(2);

    return btcTurk.setOrder(
        order.getPrice(),
        order.getPricePrecision(),
        order.getAmount(),
        order.getAmountPrecision(),
        order.getOrderType().getValue(),
        order.getOrderMethod().getValue(),
        order.getPairSymbol().toString().replace("/", ""),
        order.getDenominatorPrecision(),
        order.getTotal(),
        order.getTotalPrecision(),
        order.getTriggerPrice(),
        order.getTriggerPricePrecision(),
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory(),
        signatureCreator);
  }
}
