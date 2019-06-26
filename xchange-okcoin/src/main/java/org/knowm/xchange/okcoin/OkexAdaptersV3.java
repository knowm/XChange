package org.knowm.xchange.okcoin;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.okcoin.v3.dto.account.OkexFundingAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexSpotAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.marketdata.OkexTokenPairInformation;
import org.knowm.xchange.okcoin.v3.dto.trade.OkexOpenOrder;
import org.knowm.xchange.okcoin.v3.dto.trade.Side;

public class OkexAdaptersV3 {

  public static Balance convert(OkexFundingAccountRecord rec) {
    return new Balance.Builder()
        .currency(Currency.getInstance(rec.getCurrency()))
        .available(rec.getAvailable())
        .frozen(rec.getHold())
        .total(rec.getBalance())
        .build();
  }

  public static Balance convert(OkexSpotAccountRecord rec) {
    return new Balance.Builder()
        .currency(Currency.getInstance(rec.getCurrency()))
        .available(rec.getAvailable())
        .frozen(rec.getHold())
        .total(rec.getBalance())
        .build();
  }

  public static String toInstrument(CurrencyPair pair) {
    return pair == null ? null : pair.base.getCurrencyCode() + "-" + pair.counter.getCurrencyCode();
  }

  public static CurrencyPair toPair(String instrument) {
    return instrument == null ? null : new CurrencyPair(instrument.replace('-', '/'));
  }

  public static Ticker convert(OkexTokenPairInformation i) {
    return new Ticker.Builder()
        .currencyPair(toPair(i.getInstrumentId()))
        .last(i.getLast())
        .bid(i.getBid())
        .ask(i.getAsk())
        .volume(i.getBaseVolume24h())
        .quoteVolume(i.getQuoteVolume24h())
        .timestamp(i.getTimestamp())
        .build();
  }

  public static LimitOrder convert(OkexOpenOrder o) {
    return new LimitOrder.Builder(
            o.getSide() == Side.sell ? OrderType.ASK : OrderType.BID, toPair(o.getInstrumentId()))
        .id(o.getOrderId())
        .limitPrice(o.getPrice())
        .originalAmount(o.getSize())
        .timestamp(o.getCreatedAt())
        .build();
  }
}
