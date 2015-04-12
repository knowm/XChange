package com.xeiam.xchange.service;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.meta.ExchangeMetaData;
import com.xeiam.xchange.dto.meta.MarketMetaData;
import com.xeiam.xchange.dto.meta.SimpleMetaData;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

/**
 * Top of the hierarchy abstract class for an "exchange service"
 */
public abstract class BaseExchangeService {

  /**
   * The base Exchange. Every service has access to the containing exchange class, which hold meta data and the exchange specification
   */
  protected final Exchange exchange;

  /**
   * Constructor
   */
  protected BaseExchangeService(Exchange exchange) {

    this.exchange = exchange;
  }

  public void verifyOrder(LimitOrder limitOrder) {
    verifyOrder(limitOrder, exchange.getMetaData());
  }

  public void verifyOrder(MarketOrder marketOrder) {
    verifyOrder(marketOrder, exchange.getMetaData());
  }

  final protected void verifyOrder(Order order, SimpleMetaData metaData) {
    if (!metaData.getCurrencyPairs().contains(order.getCurrencyPair())) {
      throw new IllegalArgumentException("Invalid CurrencyPair");
    }
  }

  final protected void verifyOrder(Order order, ExchangeMetaData exchangeMetaData) {
    MarketMetaData metaData = exchangeMetaData.getMarketMetaDataMap().get(order.getCurrencyPair());
    if (metaData == null) {
      throw new IllegalArgumentException("Invalid CurrencyPair");
    }

    BigDecimal tradableAmount = order.getTradableAmount();
    if (tradableAmount == null)
      throw new IllegalArgumentException("Missing tradableAmount");

    BigDecimal amount = tradableAmount.stripTrailingZeros();
    BigDecimal minimumAmount = metaData.getMinimumAmount();
    if (amount.scale() > minimumAmount.scale()) {
      throw new IllegalArgumentException("Unsupported amount scale " + amount.scale());
    } else if (amount.compareTo(minimumAmount) < 0) {
      throw new IllegalArgumentException("Order amount less than minimum");
    }
  }

  // these two methods are for SimpleMetaData/ExchangeMetaData transition. Until all exchanges return ExchangeMetaData
  // we provide support for both, where SimpleMetaData is the default.

  protected void verifyOrder2(LimitOrder order) {
    ExchangeMetaData exchangeMetaData = (ExchangeMetaData) exchange.getMetaData();
    verifyOrder(order, exchangeMetaData);

    BigDecimal price = order.getLimitPrice().stripTrailingZeros();

    if (price.scale() > exchangeMetaData.getMarketMetaDataMap().get(order.getCurrencyPair()).getPriceScale()) {
      throw new IllegalArgumentException("Unsupported price scale " + price.scale());
    }
  }

  protected void verifyOrder2(MarketOrder order) {
    verifyOrder(order, (ExchangeMetaData) exchange.getMetaData());
  }

}
