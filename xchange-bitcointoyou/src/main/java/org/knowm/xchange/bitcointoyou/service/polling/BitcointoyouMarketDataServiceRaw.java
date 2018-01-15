package org.knowm.xchange.bitcointoyou.service.polling;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcointoyou.BitcointoyouException;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouMarketData;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouOrderBook;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouPublicTrade;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * MarketDataService raw implementation for Bitcointoyou Exchange.
 *
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
class BitcointoyouMarketDataServiceRaw extends BitcointoyouBasePollingService {

  /**
   * Constructor
   *
   * @param exchange the Bitcointoyou Exchange
   */
  BitcointoyouMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  BitcointoyouTicker getBitcointoyouTicker(CurrencyPair currencyPair) throws IOException {

    Map<String, BitcointoyouMarketData> marketData;
    try {
      marketData = bitcointoyou.getTicker();
    } catch (BitcointoyouException e) {
      throw new ExchangeException(e.getError());
    }

    BitcointoyouMarketData data = null;
    if (marketData != null && !marketData.isEmpty()) {
      data = marketData.entrySet().iterator().next().getValue();
    }

    if (data == null)
      throw new ExchangeException(currencyPair + " not available");

    return new BitcointoyouTicker(data, currencyPair);

  }
  
  BitcointoyouOrderBook getBitcointoyouOrderBook() throws IOException {

    try {
      return bitcointoyou.getOrderBook();
    } catch (BitcointoyouException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  /**
   * List all public trades made at Bitcointoyou Exchange.
   *
   * @param currencyPair the trade currency pair
   * @return an array of {@link BitcointoyouPublicTrade}
   * @throws IOException
   */
  BitcointoyouPublicTrade[] getBitcointoyouPublicTrades(CurrencyPair currencyPair) throws IOException {

    try {
      return getBitcointoyouPublicTrades(currencyPair, null, null);
    } catch (BitcointoyouException e) {
      throw new ExchangeException(e.getError());
    }
  }

  /**
   * List all public trades made at Bitcointoyou Exchange.
   *
   * @param currencyPair the trade currency pair
   * @param tradeTimestamp the trade timestamp
   * @param minTradeId the minimum trade ID
   * @return an array of {@link BitcointoyouPublicTrade}
   * @throws IOException
   */
  BitcointoyouPublicTrade[] getBitcointoyouPublicTrades(CurrencyPair currencyPair, Long tradeTimestamp, Long minTradeId) throws IOException {

    String currency = currencyPair.base.toString();

    try {
      return bitcointoyou.getTrades(currency, tradeTimestamp, minTradeId);
    } catch (BitcointoyouException e) {
      throw new ExchangeException(e.getError());
    }
  }

}
