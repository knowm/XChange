package org.knowm.xchange.tradeogre;

import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.tradeogre.dto.account.TradeOgreBalance;
import org.knowm.xchange.tradeogre.dto.marketdata.TradeOgreTicker;

public class TradeOgreAdapters {

  public static String adaptCurrencyPair(CurrencyPair currencyPair) {
    return currencyPair.counter.toString() + "-" + currencyPair.base.toString();
  }

  public static CurrencyPair adaptTradeOgreCurrencyPair(String currencyPair) {
    String[] split = currencyPair.split("-");
    return new CurrencyPair(split[1], split[0]);
  }

  public static Balance adaptTradeOgreBalance(String currency, TradeOgreBalance tradeOgreBalance) {
    return new Balance.Builder()
        .currency(new Currency(currency.toUpperCase()))
        .available(tradeOgreBalance.getAvailable())
        .total(tradeOgreBalance.getBalance())
        .build();
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, TradeOgreTicker tradeOgreTicker) {
    return new Ticker.Builder()
        .quoteVolume(new BigDecimal(tradeOgreTicker.getVolume()))
        .high(new BigDecimal(tradeOgreTicker.getHigh()))
        .low(new BigDecimal(tradeOgreTicker.getLow()))
        .last(new BigDecimal(tradeOgreTicker.getPrice()))
        .ask(new BigDecimal(tradeOgreTicker.getAsk()))
        .bid(new BigDecimal(tradeOgreTicker.getBid()))
        .currencyPair(currencyPair)
        .build();
  }
}
