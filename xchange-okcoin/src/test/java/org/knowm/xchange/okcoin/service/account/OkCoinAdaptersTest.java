package org.knowm.xchange.okcoin.service.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;

public class OkCoinAdaptersTest {

  @Test
  public void testAdaptFundingHistory()
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();

    InputStream is =
        OkCoinAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/okcoin/dto/account/example-accountrecords-deposit-data.json");
    OkCoinAccountRecords okCoinAccountDepositRecords =
        mapper.readValue(is, OkCoinAccountRecords.class);

    is =
        OkCoinAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/okcoin/dto/account/example-accountrecords-withdrawal-data.json");
    OkCoinAccountRecords okCoinAccountWithdrawalRecords =
        mapper.readValue(is, OkCoinAccountRecords.class);

    List<FundingRecord> deposits =
        OkCoinAdapters.adaptFundingHistory(okCoinAccountDepositRecords, FundingRecord.Type.DEPOSIT);
    List<FundingRecord> withdrawals =
        OkCoinAdapters.adaptFundingHistory(
            okCoinAccountWithdrawalRecords, FundingRecord.Type.WITHDRAWAL);
    final List<FundingRecord> records = new ArrayList<>();
    records.addAll(deposits);
    records.addAll(withdrawals);

    assertThat(records.size()).isEqualTo(3);
    FundingRecord depositRecord = records.get(1);
    assertThat(depositRecord).isInstanceOf(FundingRecord.class);
    assertThat(depositRecord.getType()).isEqualTo(FundingRecord.Type.DEPOSIT);
    assertThat(depositRecord.getStatus()).isEqualTo(FundingRecord.Status.COMPLETE);
    assertThat(depositRecord.getAmount()).isEqualTo(new BigDecimal("50"));
    assertThat(depositRecord.getFee().doubleValue())
        .isEqualTo(new BigDecimal("0.07").doubleValue());
    assertThat(depositRecord.getAddress()).isEqualTo("1lEWjmlkmlhTqcYj3l33sg980slkjtdqd");

    FundingRecord withdrawalRecord = records.get(2);
    assertThat(withdrawalRecord).isInstanceOf(FundingRecord.class);
    assertThat(withdrawalRecord.getType()).isEqualTo(FundingRecord.Type.WITHDRAWAL);
    assertThat(withdrawalRecord.getStatus()).isEqualTo(FundingRecord.Status.PROCESSING);
    assertThat(withdrawalRecord.getAmount()).isEqualTo(new BigDecimal("33"));
    assertThat(withdrawalRecord.getFee().doubleValue())
        .isEqualTo(new BigDecimal("0.05").doubleValue());
    assertThat(withdrawalRecord.getAddress()).isEqualTo("8OKSDF39aOIUl34lksUIYW3kl3l39d");
  }

  @Test
  public void testAdaptOrderBook() {
    BigDecimal ask1Price = new BigDecimal("8");
    BigDecimal ask1Amount = new BigDecimal("28");
    BigDecimal ask2Price = new BigDecimal("5");
    BigDecimal ask2Amount = new BigDecimal("20");
    BigDecimal ask3Price = new BigDecimal("7");
    BigDecimal ask3Amount = new BigDecimal("24");

    BigDecimal bid1Price = new BigDecimal("4");
    BigDecimal bid1Amount = new BigDecimal("35");
    BigDecimal bid2Price = new BigDecimal("2");
    BigDecimal bid2Amount = new BigDecimal("45");

    BigDecimal[][] asks = {
      {ask1Price, ask1Amount}, {ask2Price, ask2Amount}, {ask3Price, ask3Amount}
    };
    BigDecimal[][] bids = {{bid1Price, bid1Amount}, {bid2Price, bid2Amount}};
    Date date = new Date();
    OkCoinDepth depth = new OkCoinDepth(asks, bids, date);

    OrderBook orderBook = OkCoinAdapters.adaptOrderBook(depth, CurrencyPair.ETH_BTC);

    Assert.assertEquals(orderBook.getAsks().size(), asks.length);
    Assert.assertTrue(
        orderBook
            .getAsks()
            .contains(
                new LimitOrder(
                    Order.OrderType.ASK, ask1Amount, CurrencyPair.ETH_BTC, null, date, ask1Price)));
    Assert.assertTrue(
        orderBook
            .getAsks()
            .contains(
                new LimitOrder(
                    Order.OrderType.ASK, ask2Amount, CurrencyPair.ETH_BTC, null, date, ask2Price)));
    Assert.assertTrue(
        orderBook
            .getAsks()
            .contains(
                new LimitOrder(
                    Order.OrderType.ASK, ask3Amount, CurrencyPair.ETH_BTC, null, date, ask3Price)));
    Assert.assertTrue(
        orderBook
            .getAsks()
            .stream()
            .sorted()
            .collect(Collectors.toList())
            .equals(orderBook.getAsks()));

    Assert.assertEquals(orderBook.getBids().size(), bids.length);
    Assert.assertTrue(
        orderBook
            .getBids()
            .contains(
                new LimitOrder(
                    Order.OrderType.BID, bid1Amount, CurrencyPair.ETH_BTC, null, date, bid1Price)));
    Assert.assertTrue(
        orderBook
            .getBids()
            .contains(
                new LimitOrder(
                    Order.OrderType.BID, bid2Amount, CurrencyPair.ETH_BTC, null, date, bid2Price)));
    Assert.assertTrue(
        orderBook
            .getBids()
            .stream()
            .sorted()
            .collect(Collectors.toList())
            .equals(orderBook.getBids()));
  }
}
