package org.knowm.xchange.bitfinex.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexPublicTrade;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BitfinexAdapters {

  public static final Logger log = LoggerFactory.getLogger(BitfinexAdapters.class);

  private BitfinexAdapters() {}

  /**
   * Converts a {@link Currency} to Bitfinex Symbol
   * @param currency
   * @return 
   */
  public static String adaptCurrency(Currency currency) {
    if (currency.equals(Currency.DASH)) {
      return "DSH";
    
    } else if (currency.equals(Currency.QTUM)) {
      return "QTM";
    
    } else {
      return currency.toString();
    }
  }
  
  public static String adaptBitfinexCurrency(String bitfinexSymbol) {
    String currency = bitfinexSymbol.toUpperCase();
    if (currency.equals("DSH")) {
      currency = "DASH";
    }
    if (currency.equals("QTM")) {
      currency = "QTUM";
    }
    return currency;
  }

  public static CurrencyPair adaptCurrencyPair(String bitfinexSymbol) {
    String tradableIdentifier = adaptBitfinexCurrency(bitfinexSymbol.substring(0, 3));
    String transactionCurrency = adaptBitfinexCurrency(bitfinexSymbol.substring(3));
    return new CurrencyPair(tradableIdentifier, transactionCurrency);
  }
  
  public static String adaptCurrencyPairsToTickersParam(Collection<CurrencyPair> currencyPairs) {
    return currencyPairs
        .stream()
        .map(currencyPair -> "t" + adaptCurrency(currencyPair.base) + adaptCurrency(currencyPair.counter))
        .collect(Collectors.joining(","));
  }

  public static Ticker adaptTicker(BitfinexTicker bitfinexTicker) {

    BigDecimal last = bitfinexTicker.getLastPrice();
    BigDecimal bid = bitfinexTicker.getBid();
    BigDecimal bidSize = bitfinexTicker.getBidSize();
    BigDecimal ask = bitfinexTicker.getAsk();
    BigDecimal askSize = bitfinexTicker.getAskSize();
    BigDecimal high = bitfinexTicker.getHigh();
    BigDecimal low = bitfinexTicker.getLow();
    BigDecimal volume = bitfinexTicker.getVolume();

    CurrencyPair currencyPair = adaptCurrencyPair(bitfinexTicker.getSymbol().substring(1));

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .bidSize(bidSize)
        .askSize(askSize)
        .build();
  }

  public static Trade adaptPublicTrade(BitfinexPublicTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = trade.getType();
    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date = DateUtils.fromMillisUtc(trade.getTimestamp());
    final String tradeId = String.valueOf(trade.getTradeId());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptPublicTrades(BitfinexPublicTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>(trades.length);
    long lastTradeId = 0;
    for (BitfinexPublicTrade trade : trades) {
      long tradeId = trade.getTradeId();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(adaptPublicTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }
}
