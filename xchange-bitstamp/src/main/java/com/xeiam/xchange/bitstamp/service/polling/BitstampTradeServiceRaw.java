package com.xeiam.xchange.bitstamp.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampAuthenticated;
import com.xeiam.xchange.bitstamp.BitstampUtils;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.bitstamp.service.BitstampDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.TradeMetaData;

/**
 * @author gnandiga
 */
public class BitstampTradeServiceRaw extends BitstampBasePollingService {

  private final BitstampAuthenticated bitstampAuthenticated;
  private final BitstampDigest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The
   *          {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitstampTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class, exchangeSpecification.getSslUri());
    this.signatureCreator = BitstampDigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getUserName(), exchangeSpecification.getApiKey());
  }

  public BitstampOrder[] getBitstampOpenOrders() throws IOException {

    return bitstampAuthenticated.getOpenOrders(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce());
  }

  public BitstampOrder sellBitstampOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitstampAuthenticated.sell(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), tradableAmount, price);
  }

  public BitstampOrder buyBitStampOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitstampAuthenticated.buy(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), tradableAmount, price);
  }

  public boolean cancelBitstampOrder(int orderId) throws IOException {

    return bitstampAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), orderId);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions) throws IOException {

    return bitstampAuthenticated.getUserTransactions(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), numberOfTransactions);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions, Long offset, String sort) throws IOException {

    return bitstampAuthenticated.getUserTransactions(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), numberOfTransactions, offset, sort);
  }

  /**
   *
   * @return Map of currency pairs to their corresponding metadata.
   */
  public Map<CurrencyPair, TradeMetaData> getTradeMetaDataMap() throws IOException {

    Map<CurrencyPair, TradeMetaData> returnObject = new HashMap<CurrencyPair, TradeMetaData>();

    BitstampBalance bitstampBalance = bitstampAuthenticated.getBalance(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce());

    List<CurrencyPair> currencyPairs = getExchangeSymbols();
    for (CurrencyPair currencyPair : currencyPairs) {

      // minimum trade $5 // TODO put this in properties file
      returnObject.put(currencyPair, new TradeMetaData(bitstampBalance.getFee(), new BigDecimal("5"), 0));

    }
    return returnObject;
  }

}
