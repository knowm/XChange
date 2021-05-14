package org.knowm.xchange.bleutrade;

import static org.knowm.xchange.bleutrade.BleutradeUtils.toDate;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BleutradeTestData {

  protected static final CurrencyPair BLEU_BTC_CP = new CurrencyPair("BLEU", "BTC");

  protected static Balance[] expectedAccountBalances() {
    return new Balance[] {
      new Balance(
          Currency.AUD,
          new BigDecimal("40.00000000"),
          new BigDecimal("10.00000000"),
          new BigDecimal("30.00000000")),
      new Balance(
          Currency.BTC,
          new BigDecimal("100.00000000"),
          new BigDecimal("40.00000000"),
          new BigDecimal("60.00000000")),
      new Balance(
          Currency.getInstance("BLEU"),
          new BigDecimal("160.00000000"),
          new BigDecimal("70.00000000"),
          new BigDecimal("90.00000000")),
    };
  }

  protected static Balance[] expectedBalances() {
    return new Balance[] {
      new Balance(
          Currency.DOGE, new BigDecimal("0E-8"), new BigDecimal("0E-8"), new BigDecimal("0E-8")),
      new Balance(
          Currency.BTC,
          new BigDecimal("15.49843675"),
          new BigDecimal("13.98901996"),
          new BigDecimal("0E-8")),
    };
  }

  protected static Trade[] expectedTrades() {
    return new Trade[] {
      new Trade.Builder()
          .type(Order.OrderType.BID)
          .originalAmount(new BigDecimal("654971.69417461"))
          .currencyPair(CurrencyPair.BTC_AUD)
          .price(new BigDecimal("0.00000055"))
          .timestamp(new Date(1406657280000L))
          .build(),
      new Trade.Builder()
          .type(Order.OrderType.ASK)
          .originalAmount(new BigDecimal("120.00000000"))
          .currencyPair(CurrencyPair.BTC_AUD)
          .price(new BigDecimal("0.00006600"))
          .timestamp(new Date(1406657555000L))
          .build(),
    };
  }

  protected static LimitOrder[] expectedPlacedOrders() {
    return new LimitOrder[] {
      new LimitOrder(
          Order.OrderType.BID,
          new BigDecimal("10.00000000"),
          CurrencyPair.BTC_AUD,
          "",
          null,
          new BigDecimal("1.1")),
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("20.00000000"),
          CurrencyPair.BTC_AUD,
          "",
          null,
          new BigDecimal("2.2"))
    };
  }

  protected static LimitOrder[] expectedOrders() {
    return new LimitOrder[] {
      new LimitOrder(
          Order.OrderType.BID,
          new BigDecimal("20.00000000"),
          new BigDecimal("15.00000000"),
          CurrencyPair.LTC_BTC,
          "65489",
          toDate("2014-08-03 13:55:20"),
          new BigDecimal("0.01268311")),
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("150491.98700000"),
          (new BigDecimal("150491.98700000")).subtract(new BigDecimal("795.00000000")),
          CurrencyPair.DOGE_BTC,
          "65724",
          toDate("2014-07-29 18:45:17"),
          new BigDecimal("0.00000055")),
    };
  }

  protected static LimitOrder[] expectedBids() {
    return new LimitOrder[] {
      new LimitOrder(
          Order.OrderType.BID,
          new BigDecimal("4.99400000"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("3.00650900")),
      new LimitOrder(
          Order.OrderType.BID,
          new BigDecimal("50.00000000"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("3.50000000"))
    };
  }

  protected static LimitOrder[] expectedAsks() {
    return new LimitOrder[] {
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("12.44147454"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("5.13540000")),
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("100.00000000"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("6.25500000")),
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("30.00000000"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("6.75500001")),
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("13.49989999"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("6.76260099"))
    };
  }

  protected static Ticker expectedTicker() {
    return new Ticker.Builder()
        .currencyPair(BLEU_BTC_CP)
        .last(new BigDecimal("0.00101977"))
        .bid(new BigDecimal("0.00100000"))
        .ask(new BigDecimal("0.00101977"))
        .high(new BigDecimal("0.00105000"))
        .low(new BigDecimal("0.00086000"))
        .vwap(new BigDecimal("0.00103455"))
        .volume(new BigDecimal("2450.97496015"))
        .timestamp(new Date(1406632770000L))
        .build();
  }

  protected static CurrencyPairMetaData[] expectedMetaDataList() {
    return new CurrencyPairMetaData[] {
      new CurrencyPairMetaData(
          new BigDecimal("0.0025"), new BigDecimal("0.10000000"), null, 8, null),
      new CurrencyPairMetaData(
          new BigDecimal("0.0025"), new BigDecimal("0.00000001"), null, 8, null)
    };
  }

  protected static String[] expectedMetaDataStr() {
    return new String[] {
      "CurrencyPairMetaData [tradingFee=0.0025, minimumAmount=0.10000000, maximumAmount=null, baseScale=null, priceScale=8, amountStepSize=null, tradingFeeCurrency=null]",
      "CurrencyPairMetaData [tradingFee=0.0025, minimumAmount=1E-8, maximumAmount=null, baseScale=null, priceScale=8, amountStepSize=null, tradingFeeCurrency=null]"
    };
  }
}
