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
import org.knowm.xchange.btcturk.dto.BTCTurkPair;
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

  public List<BTCTurkOpenOrders> getBTCTurkOpenOrders(CurrencyPair pair) throws IOException {

    return btcTurk.getOpenOrders(
        pair.toString().replace("/", ""),
        exchange.getExchangeSpecification().getApiKey(),
        exchange.getNonceFactory(),
        signatureCreator);
  }

  public List<BTCTurkOpenOrders> getBTCTurkOpenOrders() throws IOException {

    List<BTCTurkOpenOrders> openOrdersRaw = new ArrayList<BTCTurkOpenOrders>();

    for (BTCTurkTicker ticker : btcTurk.getTicker()) {
      for (BTCTurkOpenOrders order : getBTCTurkOpenOrders(ticker.getPair())) {
        openOrdersRaw.add(order);
      }
    }

    return openOrdersRaw;
  }

  public List<BTCTurkUserTransactions> getBTCTurkUserTransactions() throws IOException {

    return btcTurk.getUserTransactions(
        0,
        25,
        BTCTurkSort.SORT_ASCENDING.toString(),
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
    BTCTurkOrderTypes tempordertype;
    tempordertype = orderTypes;

    String[] _zero = getLocalizedBigDecimalValue(BigDecimal.ZERO).split("\\.");
    String[] _price = _zero;
    if (orderMethod.equals(BTCTurkOrderMethods.LIMIT)) {
      _price = getLocalizedBigDecimalValue(price).split("\\.");
      _price[0] = _price[0].replace(",", "");
      /* String tmp = _amount[0];
      _amount[0] = _total[0];
      _total[0] = tmp;*/
    }

    String[] _triggerPrice = _zero;
    if (orderMethod.equals(BTCTurkOrderMethods.STOP_LIMIT)
        || orderMethod.equals(BTCTurkOrderMethods.STOP_MARKET))
      _triggerPrice = getLocalizedBigDecimalValue(triggerPrice).split("\\.");

    String[] _total = getLocalizedBigDecimalValue(total_amount).split("\\.");
    String[] _amount = _zero;
    if (orderTypes.equals(BTCTurkOrderTypes.Sell)
        || orderMethod.equals(BTCTurkOrderMethods.LIMIT)) {
      _amount = getLocalizedBigDecimalValue(total_amount).split("\\.");
      _total = _zero;
      if (orderMethod.equals(BTCTurkOrderMethods.LIMIT)) {
        if (orderTypes.equals(BTCTurkOrderTypes.Sell)) tempordertype = BTCTurkOrderTypes.Buy;
        else tempordertype = BTCTurkOrderTypes.Sell;
      }
    }

    BTCTurkOrder order =
        new BTCTurkOrder(
            orderMethod,
            _price[0],
            _price[1],
            _amount[0],
            _amount[1],
            _total[0],
            _total[1],
            _price[1].length(),
            _triggerPrice[0],
            _triggerPrice[1],
            tempordertype,
            new BTCTurkPair(pair));
    System.out.print(order);

    return btcTurk.setOrder(
        order.getPrice(),
        order.getPricePrecision(),
        order.getAmount(),
        order.getAmountPrecision(),
        order.getOrderType().getValue(),
        order.getOrderMethod().getValue(),
        order.getPairSymbol().toString(),
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
