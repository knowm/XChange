package org.knowm.xchange.service;

import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import si.mazi.rescu.ClientConfig;

/** Top of the hierarchy abstract class for an "exchange service" */
public abstract class BaseExchangeService<E extends Exchange> {

  /**
   * The base Exchange. Every service has access to the containing exchange class, which hold meta
   * data and the exchange specification
   */
  protected final E exchange;

  /** Constructor */
  protected BaseExchangeService(E exchange) {

    this.exchange = exchange;
  }

  public void verifyOrder(LimitOrder limitOrder) {

    ExchangeMetaData exchangeMetaData = exchange.getExchangeMetaData();
    verifyOrder(limitOrder, exchangeMetaData);
    BigDecimal price = limitOrder.getLimitPrice().stripTrailingZeros();

    if (price.scale()
        > exchangeMetaData.getCurrencyPairs().get(limitOrder.getCurrencyPair()).getPriceScale()) {
      throw new IllegalArgumentException("Unsupported price scale " + price.scale());
    }
  }

  public void verifyOrder(MarketOrder marketOrder) {

    verifyOrder(marketOrder, exchange.getExchangeMetaData());
  }

  /**
   * Get a ClientConfig object which contains exchange-specific timeout values
   * (<i>httpConnTimeout</i> and <i>httpReadTimeout</i>) if they were present in the
   * ExchangeSpecification of this instance. Subclasses are encouraged to use this config object
   * when creating a RestCU proxy.
   *
   * @return a rescu client config object
   */
  public ClientConfig getClientConfig() {

    ClientConfig rescuConfig = new ClientConfig(); // create default rescu config

    // set per exchange connection- and read-timeout (if they have been set in the
    // ExchangeSpecification)
    int customHttpConnTimeout = exchange.getExchangeSpecification().getHttpConnTimeout();
    if (customHttpConnTimeout > 0) {
      rescuConfig.setHttpConnTimeout(customHttpConnTimeout);
    }
    int customHttpReadTimeout = exchange.getExchangeSpecification().getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }
    if (exchange.getExchangeSpecification().getProxyHost() != null) {
      rescuConfig.setProxyHost(exchange.getExchangeSpecification().getProxyHost());
    }
    if (exchange.getExchangeSpecification().getProxyPort() != null) {
      rescuConfig.setProxyPort(exchange.getExchangeSpecification().getProxyPort());
    }
    return rescuConfig;
  }

  protected final void verifyOrder(Order order, ExchangeMetaData exchangeMetaData) {

    CurrencyPairMetaData metaData =
        exchangeMetaData.getCurrencyPairs().get(order.getCurrencyPair());
    if (metaData == null) {
      throw new IllegalArgumentException("Invalid CurrencyPair");
    }

    BigDecimal originalAmount = order.getOriginalAmount();
    if (originalAmount == null) {
      throw new IllegalArgumentException("Missing originalAmount");
    }

    BigDecimal amount = originalAmount.stripTrailingZeros();
    BigDecimal minimumAmount = metaData.getMinimumAmount();
    if (minimumAmount != null) {
      if (amount.scale() > minimumAmount.scale()) {
        throw new IllegalArgumentException("Unsupported amount scale " + amount.scale());
      } else if (amount.compareTo(minimumAmount) < 0) {
        throw new IllegalArgumentException("Order amount less than minimum");
      }
    }
  }
}
