package org.knowm.xchange.btc38;

import org.knowm.xchange.btc38.dto.marketdata.Btc38Ticker;
import org.knowm.xchange.btc38.dto.marketdata.Btc38Trade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Yingzhe on 12/18/2014.
 */
public class Btc38Adapters {

  public static Ticker adaptTicker(Btc38Ticker btc38Ticker, CurrencyPair currencyPair) {

    BigDecimal last = btc38Ticker.getLast();
    BigDecimal high = btc38Ticker.getHigh();
    BigDecimal low = btc38Ticker.getLow();
    BigDecimal ask = btc38Ticker.getSell();
    BigDecimal bid = btc38Ticker.getBuy();
    BigDecimal volume = btc38Ticker.getVol();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).volume(volume).ask(ask).bid(bid).build();
  }

  public static ExchangeMetaData adaptToExchangeMetaData(Collection<CurrencyPair> pairs) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
    for (CurrencyPair pair : pairs) {
      currencyPairs.put(pair, null);
      currencies.put(pair.base, null);
      currencies.put(pair.counter, null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  /**
   * @param btc38Trades
   * @param currencyPair
   * @return
   */
  public static Trades adaptTrades(Btc38Trade[] btc38Trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>();

    for (Btc38Trade btc38Trade : btc38Trades) {

      Order.OrderType orderType = btc38Trade.getType().equals("buy") ? Order.OrderType.BID : Order.OrderType.ASK;
      Trade trade = new Trade(orderType, btc38Trade.getAmount(), currencyPair, btc38Trade.getPrice(), new Date(btc38Trade.getDate() * 1000),
              btc38Trade.getTid());

      tradeList.add(trade);
    }

    return new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
  }

}
