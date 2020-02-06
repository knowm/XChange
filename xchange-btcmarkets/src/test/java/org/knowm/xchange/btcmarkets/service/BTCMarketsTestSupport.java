package org.knowm.xchange.btcmarkets.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsDtoTestSupport;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransfer;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransferHistoryResponse;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.powermock.reflect.Whitebox;

/** Test utilities for btnmarkets tests. */
public class BTCMarketsTestSupport extends BTCMarketsDtoTestSupport {

  protected static final String SPECIFICATION_USERNAME = "admin";
  protected static final String SPECIFICATION_API_KEY =
      Base64.getEncoder().encodeToString("publicKey".getBytes());
  protected static final String SPECIFICATION_SECRET_KEY =
      Base64.getEncoder().encodeToString("secretKey".getBytes());

  protected static final Balance EXPECTED_BALANCE =
      new Balance(Currency.BTC, new BigDecimal("3.0E-7"), new BigDecimal("2.0E-7"));
  protected static final Ticker EXPECTED_TICKER =
      new Ticker.Builder()
          .bid(new BigDecimal("137.00"))
          .ask(new BigDecimal("140.00"))
          .last(new BigDecimal("140.00"))
          .currencyPair(CurrencyPair.BTC_AUD)
          .timestamp(new Date(1378878117000L))
          .build();
  protected static final BTCMarketsTicker EXPECTED_BTC_MARKETS_TICKER =
      new BTCMarketsTicker(
          new BigDecimal("137.00"),
          new BigDecimal("140.00"),
          new BigDecimal("140.00"),
          "AUD",
          "BTC",
          new Date(1378878117000L));

  protected static LimitOrder[] expectedAsks() {
    return new LimitOrder[] {
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("1.004594"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("329.31")),
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("22.265709"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("329.38")),
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("10.0"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("329.41"))
    };
  }

  protected static LimitOrder[] expectedBids() {
    return new LimitOrder[] {
      new LimitOrder(
          Order.OrderType.BID,
          new BigDecimal("1.3593495"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("328.98")),
      new LimitOrder(
          Order.OrderType.BID,
          new BigDecimal("0.21273699"),
          CurrencyPair.BTC_AUD,
          null,
          null,
          new BigDecimal("327.6"))
    };
  }

  protected static LimitOrder[] expectedOrders() {
    return new LimitOrder[] {
      new LimitOrder(
          Order.OrderType.ASK,
          new BigDecimal("10.00000000"),
          CurrencyPair.BTC_AUD,
          "1",
          null,
          new BigDecimal("20.00000000")),
      new LimitOrder(
          Order.OrderType.BID,
          new BigDecimal("30.00000000"),
          CurrencyPair.BTC_AUD,
          "2",
          null,
          new BigDecimal("40.00000000"))
    };
  }

  protected static UserTrade[] expectedUserTrades() {
    return new UserTrade[] {
      new UserTrade.Builder()
          .type(Order.OrderType.ASK)
          .originalAmount(new BigDecimal("20.00000000"))
          .currencyPair(CurrencyPair.BTC_AUD)
          .price(new BigDecimal("10.00000000"))
          .timestamp(new Date(111111111L))
          .id("1")
          .feeAmount(BigDecimal.ONE)
          .feeCurrency(Currency.AUD)
          .build(),
      new UserTrade.Builder()
          .type(Order.OrderType.ASK)
          .originalAmount(new BigDecimal("40.00000000"))
          .currencyPair(CurrencyPair.BTC_AUD)
          .price(new BigDecimal("30.00000000"))
          .timestamp(new Date(222222222L))
          .id("2")
          .feeAmount(BigDecimal.valueOf(2))
          .feeCurrency(Currency.AUD)
          .build(),
      new UserTrade.Builder()
          .type(Order.OrderType.BID)
          .originalAmount(new BigDecimal("60.00000000"))
          .currencyPair(CurrencyPair.BTC_AUD)
          .price(new BigDecimal("50.00000000"))
          .timestamp(new Date(333333333L))
          .id("3")
          .feeAmount(BigDecimal.valueOf(3))
          .feeCurrency(Currency.AUD)
          .build(),
      new UserTrade.Builder()
          .type(Order.OrderType.BID)
          .originalAmount(new BigDecimal("80.00000000"))
          .currencyPair(CurrencyPair.BTC_AUD)
          .price(new BigDecimal("70.00000000"))
          .timestamp(new Date(444444444L))
          .id("4")
          .feeAmount(BigDecimal.valueOf(4))
          .feeCurrency(Currency.AUD)
          .build(),
      new UserTrade.Builder()
          .type(Order.OrderType.BID)
          .originalAmount(BigDecimal.ZERO)
          .currencyPair(CurrencyPair.BTC_AUD)
          .price(new BigDecimal("90.00000000"))
          .timestamp(new Date(555555555L))
          .feeAmount(BigDecimal.valueOf(5))
          .feeCurrency(Currency.AUD)
          .build()
    };
  }

  protected static List<BTCMarketsUserTrade> expectedBtcMarketsUserTrades() {
    return Collections.unmodifiableList(
        Arrays.asList(
            createBTCMarketsUserTrade(
                1L,
                "trade 1",
                new BigDecimal("10.00000000"),
                new BigDecimal("20.00000000"),
                new BigDecimal("1"),
                new Date(111111111L),
                BTCMarketsOrder.Side.Ask),
            createBTCMarketsUserTrade(
                2L,
                "trade 2",
                new BigDecimal("30.00000000"),
                new BigDecimal("40.00000000"),
                new BigDecimal("2"),
                new Date(222222222L),
                BTCMarketsOrder.Side.Ask),
            createBTCMarketsUserTrade(
                3L,
                "trade 3",
                new BigDecimal("50.00000000"),
                new BigDecimal("60.00000000"),
                new BigDecimal("3"),
                new Date(333333333L),
                BTCMarketsOrder.Side.Bid),
            createBTCMarketsUserTrade(
                4L,
                "trade 4",
                new BigDecimal("70.00000000"),
                new BigDecimal("80.00000000"),
                new BigDecimal("4"),
                new Date(444444444L),
                BTCMarketsOrder.Side.Bid),
            createBTCMarketsUserTrade(
                5L,
                "trade 5",
                new BigDecimal("90.00000000"),
                BigDecimal.ZERO,
                new BigDecimal("5"),
                new Date(555555555L),
                BTCMarketsOrder.Side.Bid)));
  }

  protected static List<BTCMarketsUserTrade> expectedParsedBtcMarketsUserTrades() {
    return Collections.unmodifiableList(
        Arrays.asList(
            createBTCMarketsUserTrade(
                45118157L,
                null,
                new BigDecimal("330.00000000"),
                new BigDecimal("0.00100000"),
                new BigDecimal("0.00280499"),
                new Date(1442994673684L),
                BTCMarketsOrder.Side.Bid),
            createBTCMarketsUserTrade(
                45118095L,
                null,
                new BigDecimal("328.33000000"),
                new BigDecimal("0.00100000"),
                new BigDecimal("0.00279080"),
                new Date(1442994664114L),
                BTCMarketsOrder.Side.Ask),
            createBTCMarketsUserTrade(
                45117892L,
                null,
                new BigDecimal("328.65000000"),
                new BigDecimal("0.00100000"),
                new BigDecimal("0.00279352"),
                new Date(1442994245419L),
                BTCMarketsOrder.Side.Ask)));
  }

  protected static BTCMarketsOrder[] expectedBtcMarketsOrders() {
    return new BTCMarketsOrder[] {
      createBTCMarketsOrder(
          1L,
          new BigDecimal("10.00000000"),
          new BigDecimal("20.00000000"),
          "AUD",
          "BTC",
          BTCMarketsOrder.Side.Ask,
          BTCMarketsOrder.Type.Market,
          "11111",
          null,
          null,
          null,
          null,
          null),
      createBTCMarketsOrder(
          2L,
          new BigDecimal("30.00000000"),
          new BigDecimal("40.00000000"),
          "AUD",
          "BTC",
          BTCMarketsOrder.Side.Bid,
          BTCMarketsOrder.Type.Limit,
          "22222",
          null,
          null,
          null,
          null,
          null)
    };
  }

  protected static BTCMarketsOrder[] expectedParsedBtcMarketsOrders() {
    return new BTCMarketsOrder[] {
      createBTCMarketsOrder(
          1003245675L,
          new BigDecimal("0.10000000"),
          new BigDecimal("130.00000000"),
          "AUD",
          "BTC",
          BTCMarketsOrder.Side.Bid,
          BTCMarketsOrder.Type.Limit,
          null,
          new Date(1378862733366L),
          "Placed",
          null,
          new BigDecimal("0.10000000"),
          new ArrayList<BTCMarketsUserTrade>()),
      createBTCMarketsOrder(
          4345675L,
          new BigDecimal("0.10000000"),
          new BigDecimal("130.00000000"),
          "AUD",
          "BTC",
          BTCMarketsOrder.Side.Ask,
          BTCMarketsOrder.Type.Limit,
          null,
          new Date(1378636912705L),
          "Fully Matched",
          null,
          new BigDecimal("0E-8"),
          Arrays.asList(
              createBTCMarketsUserTrade(
                  5345677L,
                  null,
                  new BigDecimal("130.00000000"),
                  new BigDecimal("0.10000000"),
                  new BigDecimal("0.00100000"),
                  new Date(1378636913151L),
                  null))),
    };
  }

  protected static BTCMarketsBalance[] expectedBtcMarketsBalances() {
    return new BTCMarketsBalance[] {
      createBTCMarketsBalance(new BigDecimal("10.00000000"), null, "AUD"),
      createBTCMarketsBalance(null, new BigDecimal("10.00000000"), "BTC"),
      createBTCMarketsBalance(null, null, "LTC")
    };
  }

  protected static BTCMarketsUserTrade createBTCMarketsUserTrade(
      Long id,
      String description,
      BigDecimal price,
      BigDecimal volume,
      BigDecimal fee,
      Date creationTime,
      BTCMarketsOrder.Side side) {

    BTCMarketsUserTrade marketsUserTrade = new BTCMarketsUserTrade();
    Whitebox.setInternalState(marketsUserTrade, "id", id);
    Whitebox.setInternalState(marketsUserTrade, "description", description);
    Whitebox.setInternalState(marketsUserTrade, "price", price);
    Whitebox.setInternalState(marketsUserTrade, "volume", volume);
    Whitebox.setInternalState(marketsUserTrade, "fee", fee);
    Whitebox.setInternalState(marketsUserTrade, "creationTime", creationTime);
    Whitebox.setInternalState(marketsUserTrade, "side", side);

    return marketsUserTrade;
  }

  protected static BTCMarketsOrder createBTCMarketsOrder(
      Long id,
      BigDecimal volume,
      BigDecimal price,
      String currency,
      String instrument,
      BTCMarketsOrder.Side orderSide,
      BTCMarketsOrder.Type ordertype,
      String clientRequestId,
      Date creationTime,
      String status,
      String errorMessage,
      BigDecimal openVolume,
      List<BTCMarketsUserTrade> trades) {
    BTCMarketsOrder order =
        new BTCMarketsOrder(
            volume, price, currency, instrument, orderSide, ordertype, clientRequestId);

    Whitebox.setInternalState(order, "id", id);
    Whitebox.setInternalState(order, "creationTime", creationTime);
    Whitebox.setInternalState(order, "status", status);
    Whitebox.setInternalState(order, "errorMessage", errorMessage);
    Whitebox.setInternalState(order, "openVolume", openVolume);
    Whitebox.setInternalState(order, "trades", trades);

    return order;
  }

  protected static BTCMarketsBalance createBTCMarketsBalance(
      BigDecimal pendingFunds, BigDecimal balance, String currency) {

    BTCMarketsBalance marketsBalance = new BTCMarketsBalance();
    Whitebox.setInternalState(marketsBalance, "pendingFunds", pendingFunds);
    Whitebox.setInternalState(marketsBalance, "balance", balance);
    Whitebox.setInternalState(marketsBalance, "currency", currency);

    return marketsBalance;
  }

  protected static BTCMarketsFundtransferHistoryResponse
      expectedParsedBTCMarketsFundtransferHistoryResponse() {
    return new BTCMarketsFundtransferHistoryResponse(
        true,
        null,
        null,
        Arrays.asList(
            new BTCMarketsFundtransfer(
                "Complete",
                new Date(1530533761866L),
                BigDecimal.ZERO.setScale(8),
                "Ethereum Deposit, S 15",
                null,
                new Date(1530533761866L),
                7485764826L,
                new BTCMarketsFundtransfer.CryptoPaymentDetail(
                    "0x1234abcdef1234abcdef1234abcdef1234abcdef1234abcdef1234abcdef", null),
                "ETH",
                BigDecimal.valueOf(15.04872041),
                "DEPOSIT")),
        new BTCMarketsFundtransferHistoryResponse.Paging(
            "/fundtransfer/history?since=1957653133&indexForward=true",
            "/fundtransfer/history?since=373346299&indexForward=false"));
  }
}
