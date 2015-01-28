package com.xeiam.xchange.bitstamp.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitstamp.BitstampAuthenticated;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.bitstamp.service.BitstampDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.TradeMetaInfo;

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
    this.signatureCreator = BitstampDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), exchange.getExchangeSpecification()
        .getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public BitstampOrder[] getBitstampOpenOrders() throws IOException {

    return bitstampAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
  }

  public BitstampOrder sellBitstampOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitstampAuthenticated.sell(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
        price);
  }

  public BitstampOrder buyBitStampOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitstampAuthenticated.buy(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
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

  /**
   * @return Map of currency pairs to their corresponding metadata.
   */
  //TODO look at this
  public Map<CurrencyPair, TradeMetaInfo> getTradeMetaDataMap() throws IOException {

    Map<CurrencyPair, TradeMetaInfo> returnObject = new HashMap<CurrencyPair, TradeMetaInfo>();

    BitstampBalance bitstampBalance = bitstampAuthenticated.getBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());

    List<CurrencyPair> currencyPairs = exchange.getMetaData().getCurrencyPairs();
    for (CurrencyPair currencyPair : currencyPairs) {

      // minimum trade $5 // TODO put this in properties file
      returnObject.put(currencyPair, new TradeMetaInfo(bitstampBalance.getFee(), new BigDecimal("5"), 0, null));

    }
    return returnObject;
  }

}
