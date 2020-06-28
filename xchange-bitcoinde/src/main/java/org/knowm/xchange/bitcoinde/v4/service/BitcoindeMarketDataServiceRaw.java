package org.knowm.xchange.bitcoinde.v4.service;

import static org.knowm.xchange.bitcoinde.BitcoindeUtils.*;

import java.io.IOException;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoindeMarketDataServiceRaw extends BitcoindeBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  public BitcoindeMarketDataServiceRaw(BitcoindeExchange exchange) {
    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BitcoindeCompactOrderbookWrapper getBitcoindeCompactOrderBook(CurrencyPair currencyPair)
      throws IOException {
    try {
      return bitcoinde.getCompactOrderBook(
          apiKey, nonceFactory, signatureCreator, createBitcoindePair(currencyPair));
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  public BitcoindeOrderbookWrapper getBitcoindeOrderBook(
      CurrencyPair currencyPair, OrderType type, BitcoindeOrderbookOrdersParams params)
      throws IOException {
    try {
      return bitcoinde.getOrderBook(
          apiKey,
          nonceFactory,
          signatureCreator,
          createBitcoindePair(currencyPair),
          createBitcoindeType(type),
          createBitcoindeBoolean(params.onlyOrdersWithRequirementsFullfilled()),
          createBitcoindeBoolean(params.onlyFromFullyIdentifiedUsers()),
          createBitcoindeBoolean(params.onlyExpressOrders()));
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  public BitcoindeTradesWrapper getBitcoindeTrades(CurrencyPair currencyPair, Integer since)
      throws IOException {
    try {
      return bitcoinde.getTrades(
          apiKey, nonceFactory, signatureCreator, createBitcoindePair(currencyPair), since);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }
}
