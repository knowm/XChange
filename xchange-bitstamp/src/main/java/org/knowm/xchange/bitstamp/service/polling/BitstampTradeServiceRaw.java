package org.knowm.xchange.bitstamp.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAuthenticated;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.bitstamp.service.BitstampDigest;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author gnandiga
 */
public class BitstampTradeServiceRaw extends BitstampBasePollingService {

  private final BitstampAuthenticated bitstampAuthenticated;
  private final BitstampDigest signatureCreator;

  /**
   * @param exchange
   */
  public BitstampTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = BitstampDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public BitstampOrder[] getBitstampOpenOrdersUsd() throws IOException {

    return bitstampAuthenticated.getOpenOrdersUsd(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
  }

  public BitstampOrder[] getBitstampOpenOrdersEur() throws IOException {

	    return bitstampAuthenticated.getOpenOrdersEur(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
	  }
  
  public BitstampOrder sellBitstampOrderUsd(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitstampAuthenticated.sellUsd(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
        price);
  }

  public BitstampOrder sellBitstampOrderEur(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitstampAuthenticated.sellEur(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
        price);
  }

  
  public BitstampOrder buyBitStampOrderUsd(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitstampAuthenticated.buyUsd(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
        price);
  }

  public BitstampOrder buyBitStampOrderEur(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitstampAuthenticated.buyEur(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
        price);
  }
  
  public boolean cancelBitstampOrder(int orderId) throws IOException {

    return bitstampAuthenticated.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), orderId);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions) throws IOException {

    return bitstampAuthenticated.getUserTransactions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        numberOfTransactions);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions, Long offset, String sort) throws IOException {

    return bitstampAuthenticated.getUserTransactions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        numberOfTransactions, offset, sort);
  }

}
