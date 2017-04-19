package org.knowm.xchange.btcchina.service.fix;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.knowm.xchange.btcchina.BTCChinaAdapters;
import org.knowm.xchange.btcchina.service.fix.field.Amount;
import org.knowm.xchange.btcchina.service.fix.fix44.AccountInfoResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;

import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.Message;
import quickfix.field.MDEntryDate;
import quickfix.field.MDEntryPx;
import quickfix.field.MDEntrySize;
import quickfix.field.MDEntryTime;
import quickfix.field.MDEntryType;
import quickfix.field.NoMDEntries;
import quickfix.fix44.MarketDataIncrementalRefresh;
import quickfix.fix44.MarketDataSnapshotFullRefresh;

/**
 * Various adapters for converting from {@link Message} to XChange DTOs.
 */
public final class BTCChinaFIXAdapters {

  private BTCChinaFIXAdapters() {
  }

  public static Ticker adaptTicker(MarketDataSnapshotFullRefresh message) throws FieldNotFound {

    Ticker.Builder tickerBuilder = new Ticker.Builder();

    String symbol = message.getSymbol().getValue();
    tickerBuilder.currencyPair(BTCChinaAdapters.adaptCurrencyPair(symbol));

    int noMDEntries = message.getNoMDEntries().getValue();
    for (int i = 1; i <= noMDEntries; i++) {
      Group group = message.getGroup(i, NoMDEntries.FIELD);
      adapt(tickerBuilder, group);
    }

    Ticker ticker = tickerBuilder.build();
    return ticker;
  }

  public static Ticker adaptUpdate(Ticker ticker, MarketDataIncrementalRefresh message) throws FieldNotFound {

    Ticker.Builder tickerBuilder = new Ticker.Builder().currencyPair(ticker.getCurrencyPair()).timestamp(ticker.getTimestamp()).bid(ticker.getBid())
        .ask(ticker.getAsk()).last(ticker.getLast()).high(ticker.getHigh()).low(ticker.getLow()).volume(ticker.getVolume());

    int noMDEntries = message.getNoMDEntries().getValue();
    for (int i = 1; i <= noMDEntries; i++) {
      Group group = message.getGroup(i, NoMDEntries.FIELD);
      adapt(tickerBuilder, group);
    }

    return tickerBuilder.build();
  }

  private static void adapt(Ticker.Builder tickerBuilder, Group group) throws FieldNotFound {

    char type = group.getChar(MDEntryType.FIELD);

    BigDecimal px;
    BigDecimal size;
    switch (type) {
      case MDEntryType.BID:
        px = group.getDecimal(MDEntryPx.FIELD);
        tickerBuilder.bid(px);
        break;
      case MDEntryType.OFFER:
        px = group.getDecimal(MDEntryPx.FIELD);
        tickerBuilder.ask(px);
        break;
      case MDEntryType.TRADE:
        px = group.getDecimal(MDEntryPx.FIELD);
        tickerBuilder.last(px);
        break;
      case MDEntryType.CLOSING_PRICE:
        // no closing price in XChange's ticker
        break;
      case MDEntryType.TRADING_SESSION_HIGH_PRICE:
        px = group.getDecimal(MDEntryPx.FIELD);
        tickerBuilder.high(px);
        break;
      case MDEntryType.TRADING_SESSION_LOW_PRICE:
        px = group.getDecimal(MDEntryPx.FIELD);
        tickerBuilder.low(px);
        break;
      case MDEntryType.TRADING_SESSION_VWAP_PRICE:
        // no vwap price in XChange's ticker
        break;
      case MDEntryType.TRADE_VOLUME:
        size = group.getDecimal(MDEntrySize.FIELD);
        tickerBuilder.volume(size);

        break;
    }

    Date date = group.getField(new MDEntryDate()).getValue();
    Date time = group.getField(new MDEntryTime()).getValue();
    TimeZone utc = TimeZone.getTimeZone("UTC");
    Calendar dateCal = Calendar.getInstance(utc);
    dateCal.setTime(date);
    Calendar timeCal = Calendar.getInstance(utc);
    timeCal.setTime(time);
    dateCal.set(HOUR_OF_DAY, timeCal.get(HOUR_OF_DAY));
    dateCal.set(MINUTE, timeCal.get(MINUTE));
    dateCal.set(SECOND, timeCal.get(SECOND));
    dateCal.set(MILLISECOND, timeCal.get(MILLISECOND));

    tickerBuilder.timestamp(dateCal.getTime());
  }

  public static Wallet adaptWallet(AccountInfoResponse message) throws FieldNotFound {

    List<Group> groups = message.getGroups(org.knowm.xchange.btcchina.service.fix.field.Balance.FIELD);
    List<Balance> balances = new ArrayList<>(groups.size());
    for (Group group : groups) {
      Balance balance = new Balance(Currency.getInstance(group.getField(new quickfix.field.Currency()).getValue()),
          group.getField(new Amount()).getValue());
      balances.add(balance);
    }
    return new Wallet(balances);
  }

}
