package org.knowm.xchange.coinex;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.coinex.dto.account.CoinexBalanceInfo;
import org.knowm.xchange.coinex.dto.marketdata.CoinexTickerV1;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.instrument.Instrument;

@UtilityClass
public class CoinexAdapters {

  private final Map<String, CurrencyPair> SYMBOL_TO_CURRENCY_PAIR = new HashMap<>();

  public Balance toBalance(CoinexBalanceInfo balance) {
    return new Balance.Builder()
        .currency(balance.getCurrency())
        .available(balance.getAvailable())
        .frozen(balance.getFrozen())
        .build();
  }

  public Wallet toWallet(List<CoinexBalanceInfo> coinexBalanceInfos) {
    List<Balance> balances = coinexBalanceInfos.stream()
            .map(CoinexAdapters::toBalance)
            .collect(Collectors.toList());

    return Wallet.Builder.from(balances).id("spot").build();
  }

  public String instrumentsToString(Collection<Instrument> instruments) {
    if (instruments == null || instruments.isEmpty()) {
      return null;
    } else {
      return instruments.stream().map(CoinexAdapters::toString).collect(Collectors.joining(","));
    }
  }

  public void putSymbolMapping(String symbol, CurrencyPair currencyPair) {
    SYMBOL_TO_CURRENCY_PAIR.put(symbol, currencyPair);
  }

  public CurrencyPair toCurrencyPair(String symbol) {
    return SYMBOL_TO_CURRENCY_PAIR.get(symbol);
  }

  public String toString(Instrument instrument) {
    if (instrument == null) {
      return null;
    } else {
      return instrument.getBase().getCurrencyCode() + instrument.getCounter().getCurrencyCode();
    }
  }


  public Ticker toTicker(String symbol, CoinexTickerV1 coinexTickerV1, Instant timestamp) {
    return toTicker(toCurrencyPair(symbol), coinexTickerV1, timestamp);
  }


  public Ticker toTicker(Instrument instrument, CoinexTickerV1 coinexTickerV1, Instant timestamp) {
    Builder builder = new Ticker.Builder();

    if (instrument != null) {
      builder.instrument(instrument);
    }

    builder
        .open(coinexTickerV1.getOpen24h())
        .last(coinexTickerV1.getVolume24h())
        .bid(coinexTickerV1.getBestBidPrice())
        .ask(coinexTickerV1.getBestAskPrice())
        .high(coinexTickerV1.getHigh24h())
        .low(coinexTickerV1.getLow24h())
        .volume(coinexTickerV1.getVolume24h())
        .timestamp(Date.from(timestamp))
        .bidSize(coinexTickerV1.getBestBidSize())
        .askSize(coinexTickerV1.getBestAskSize())
        .percentageChange(coinexTickerV1.get24hPercentageChange());

    return builder.build();
  }
}
