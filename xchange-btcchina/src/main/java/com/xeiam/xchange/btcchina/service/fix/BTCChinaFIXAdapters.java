package com.xeiam.xchange.btcchina.service.fix;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;

/**
 * Various adapters for converting from {@link Message} to XChange DTOs.
 */
public final class BTCChinaFIXAdapters {

  private BTCChinaFIXAdapters() {
  }

  public static Ticker adaptTicker(MarketDataSnapshotFullRefresh message) throws FieldNotFound {

    TickerBuilder tickerBuilder = TickerBuilder.newInstance();

    String symbol = message.getSymbol().getValue();
    tickerBuilder.withCurrencyPair(BTCChinaAdapters.adaptCurrencyPair(symbol));

    int noMDEntries = message.getNoMDEntries().getValue();
    for (int i = 1; i <= noMDEntries; i++) {
      Group group = message.getGroup(i, NoMDEntries.FIELD);
      adapt(tickerBuilder, group);
    }

    Ticker ticker = tickerBuilder.build();
    return ticker;
  }

  public static Ticker adaptUpdate(Ticker ticker, MarketDataIncrementalRefresh message) throws FieldNotFound {

    TickerBuilder tickerBuilder =
        TickerBuilder.newInstance().withCurrencyPair(ticker.getCurrencyPair()).withTimestamp(ticker.getTimestamp()).withBid(ticker.getBid()).withAsk(ticker.getAsk()).withLast(ticker.getLast())
            .withHigh(ticker.getHigh()).withLow(ticker.getLow()).withVolume(ticker.getVolume());

    int noMDEntries = message.getNoMDEntries().getValue();
    for (int i = 1; i <= noMDEntries; i++) {
      Group group = message.getGroup(i, NoMDEntries.FIELD);
      adapt(tickerBuilder, group);
    }

    return tickerBuilder.build();
  }

  private static void adapt(TickerBuilder tickerBuilder, Group group) throws FieldNotFound {

    char type = group.getChar(MDEntryType.FIELD);

    BigDecimal px;
    BigDecimal size;
    switch (type) {
    case MDEntryType.BID:
      px = group.getDecimal(MDEntryPx.FIELD);
      tickerBuilder.withBid(px);
      break;
    case MDEntryType.OFFER:
      px = group.getDecimal(MDEntryPx.FIELD);
      tickerBuilder.withAsk(px);
      break;
    case MDEntryType.TRADE:
      px = group.getDecimal(MDEntryPx.FIELD);
      tickerBuilder.withLast(px);
      break;
    case MDEntryType.CLOSING_PRICE:
      // no closing price in XChange's ticker
      break;
    case MDEntryType.TRADING_SESSION_HIGH_PRICE:
      px = group.getDecimal(MDEntryPx.FIELD);
      tickerBuilder.withHigh(px);
      break;
    case MDEntryType.TRADING_SESSION_LOW_PRICE:
      px = group.getDecimal(MDEntryPx.FIELD);
      tickerBuilder.withLow(px);
      break;
    case MDEntryType.TRADING_SESSION_VWAP_PRICE:
      // no vwap price in XChange's ticker
      break;
    case MDEntryType.TRADE_VOLUME:
      size = group.getDecimal(MDEntrySize.FIELD);
      tickerBuilder.withVolume(size);

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

    tickerBuilder.withTimestamp(dateCal.getTime());
  }

}
