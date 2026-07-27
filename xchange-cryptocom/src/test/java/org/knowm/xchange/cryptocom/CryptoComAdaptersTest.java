package org.knowm.xchange.cryptocom;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.dto.account.CryptoComBalance;
import org.knowm.xchange.cryptocom.dto.account.CryptoComDepositRecord;
import org.knowm.xchange.cryptocom.dto.account.CryptoComWithdrawalRecord;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComInstrument;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComOrderBookData;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComPublicTrade;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComTicker;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrder;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;

public class CryptoComAdaptersTest {

  @Test
  public void testToInstrumentName() {
    assertThat(CryptoComAdapters.toInstrumentName(CurrencyPair.BTC_USDT)).isEqualTo("BTC_USDT");
    assertThat(CryptoComAdapters.toInstrumentName(null)).isNull();
  }

  @Test
  public void testToCurrencyPair() {
    assertThat(CryptoComAdapters.toCurrencyPair("BTC_USDT")).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(CryptoComAdapters.toCurrencyPair(null)).isNull();
    assertThat(CryptoComAdapters.toCurrencyPair("BTCUSDT")).isNull();
    assertThat(CryptoComAdapters.toCurrencyPair("BTC_USDT_PERP")).isNull();
  }

  @Test
  public void testToBigDecimal() {
    assertThat(CryptoComAdapters.toBigDecimal("1.5")).isEqualByComparingTo(new BigDecimal("1.5"));
    assertThat(CryptoComAdapters.toBigDecimal(null)).isNull();
  }

  @Test
  public void testToEpochMillis() {
    assertThat(CryptoComAdapters.toEpochMillis(new Date(1000L))).isEqualTo(1000L);
    assertThat(CryptoComAdapters.toEpochMillis(null)).isNull();
  }

  @Test
  public void testAdaptExchangeMetaData() {
    CryptoComInstrument instrument = new CryptoComInstrument();
    instrument.setSymbol("BTC_USDT");
    instrument.setInstType("CCY_PAIR");
    instrument.setBaseCurrency("BTC");
    instrument.setQuoteCurrency("USDT");
    instrument.setQuoteDecimals(2);
    instrument.setQuantityDecimals(5);
    instrument.setPriceTickSize("0.01");
    instrument.setQtyTickSize("0.00001");
    instrument.setTradable(true);

    ExchangeMetaData metaData =
        CryptoComAdapters.adaptExchangeMetaData(Collections.singletonList(instrument));

    Instrument pair = CurrencyPair.BTC_USDT;
    assertThat(metaData.getInstruments()).containsKey(pair);
    assertThat(metaData.getInstruments().get(pair).getPriceScale()).isEqualTo(2);
    assertThat(metaData.getInstruments().get(pair).getVolumeScale()).isEqualTo(5);
    assertThat(metaData.getInstruments().get(pair).getAmountStepSize())
        .isEqualByComparingTo(new BigDecimal("0.00001"));
    assertThat(metaData.getInstruments().get(pair).isMarketOrderEnabled()).isTrue();
    assertThat(metaData.getCurrencies()).containsKeys(Currency.BTC, Currency.USDT);
  }

  @Test
  public void testAdaptTicker() {
    CryptoComTicker ticker = new CryptoComTicker();
    ticker.setInstrumentName("BTC_USDT");
    ticker.setLatestTradePrice("50000.0");
    ticker.setBestBidPrice("49999.0");
    ticker.setBestBidSize("1.0");
    ticker.setBestAskPrice("50001.0");
    ticker.setBestAskSize("2.0");
    ticker.setHigh24h("51000.0");
    ticker.setLow24h("49000.0");
    ticker.setVolume24h("100.0");
    ticker.setVolume24hUsd("5000000.0");
    ticker.setPriceChange24h("0.01");
    ticker.setTimestamp(1000L);

    Ticker adapted = CryptoComAdapters.adaptTicker(ticker);

    assertThat(adapted.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(adapted.getLast()).isEqualByComparingTo(new BigDecimal("50000.0"));
    assertThat(adapted.getBid()).isEqualByComparingTo(new BigDecimal("49999.0"));
    assertThat(adapted.getAsk()).isEqualByComparingTo(new BigDecimal("50001.0"));
    assertThat(adapted.getHigh()).isEqualByComparingTo(new BigDecimal("51000.0"));
    assertThat(adapted.getLow()).isEqualByComparingTo(new BigDecimal("49000.0"));
    assertThat(adapted.getVolume()).isEqualByComparingTo(new BigDecimal("100.0"));
    assertThat(adapted.getTimestamp()).isEqualTo(new Date(1000L));

    assertThat(CryptoComAdapters.adaptTicker(null)).isNull();
  }

  @Test
  public void testAdaptTickers() {
    CryptoComTicker ticker = new CryptoComTicker();
    ticker.setInstrumentName("BTC_USDT");
    List<Ticker> adapted = CryptoComAdapters.adaptTickers(Arrays.asList(ticker));
    assertThat(adapted).hasSize(1);
    assertThat(CryptoComAdapters.adaptTickers(null)).isEmpty();
  }

  @Test
  public void testAdaptOrderBook() {
    CryptoComOrderBookData data = new CryptoComOrderBookData();
    data.setBids(Arrays.asList(Arrays.asList("100", "1", "1")));
    data.setAsks(Arrays.asList(Arrays.asList("101", "2", "1")));
    data.setTimestamp(1000L);

    OrderBook orderBook = CryptoComAdapters.adaptOrderBook(data, CurrencyPair.BTC_USDT);

    assertThat(orderBook.getBids()).hasSize(1);
    assertThat(orderBook.getBids().get(0).getLimitPrice()).isEqualByComparingTo("100");
    assertThat(orderBook.getBids().get(0).getOriginalAmount()).isEqualByComparingTo("1");
    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(orderBook.getAsks()).hasSize(1);
    assertThat(orderBook.getAsks().get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(orderBook.getTimeStamp()).isEqualTo(new Date(1000L));

    assertThat(CryptoComAdapters.adaptOrderBook(null, CurrencyPair.BTC_USDT)).isNull();
  }

  @Test
  public void testAdaptTrade() {
    CryptoComPublicTrade trade = new CryptoComPublicTrade();
    trade.setPrice("100");
    trade.setQuantity("1");
    trade.setTimestamp(1000L);
    trade.setTradeId("1");
    trade.setSide("sell");

    Trade adapted = CryptoComAdapters.adaptTrade(trade, CurrencyPair.BTC_USDT);

    assertThat(adapted.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(adapted.getPrice()).isEqualByComparingTo("100");
    assertThat(adapted.getOriginalAmount()).isEqualByComparingTo("1");
    assertThat(adapted.getId()).isEqualTo("1");
    assertThat(adapted.getType()).isEqualTo(OrderType.ASK);
  }

  @Test
  public void testAdaptTrades() {
    CryptoComPublicTrade trade = new CryptoComPublicTrade();
    trade.setSide("buy");
    Trades trades = CryptoComAdapters.adaptTrades(Arrays.asList(trade), CurrencyPair.BTC_USDT);
    assertThat(trades.getTrades()).hasSize(1);

    Trades empty = CryptoComAdapters.adaptTrades(null, CurrencyPair.BTC_USDT);
    assertThat(empty.getTrades()).isEmpty();
  }

  @Test
  public void testAdaptOrderType() {
    assertThat(CryptoComAdapters.adaptOrderType("BUY")).isEqualTo(OrderType.BID);
    assertThat(CryptoComAdapters.adaptOrderType("SELL")).isEqualTo(OrderType.ASK);
    assertThat(CryptoComAdapters.adaptOrderType("buy")).isEqualTo(OrderType.BID);
  }

  @Test
  public void testAdaptOrderStatus() {
    assertThat(CryptoComAdapters.adaptOrderStatus("ACTIVE", BigDecimal.ZERO))
        .isEqualTo(OrderStatus.NEW);
    assertThat(CryptoComAdapters.adaptOrderStatus("ACTIVE", new BigDecimal("0.5")))
        .isEqualTo(OrderStatus.PARTIALLY_FILLED);
    assertThat(CryptoComAdapters.adaptOrderStatus("FILLED", null)).isEqualTo(OrderStatus.FILLED);
    assertThat(CryptoComAdapters.adaptOrderStatus("CANCELED", null))
        .isEqualTo(OrderStatus.CANCELED);
    assertThat(CryptoComAdapters.adaptOrderStatus("REJECTED", null))
        .isEqualTo(OrderStatus.REJECTED);
    assertThat(CryptoComAdapters.adaptOrderStatus("EXPIRED", null)).isEqualTo(OrderStatus.EXPIRED);
    assertThat(CryptoComAdapters.adaptOrderStatus("SOMETHING_ELSE", null))
        .isEqualTo(OrderStatus.UNKNOWN);
    assertThat(CryptoComAdapters.adaptOrderStatus((String) null, null))
        .isEqualTo(OrderStatus.UNKNOWN);
  }

  @Test
  public void testAdaptOrder() {
    CryptoComOrder order = new CryptoComOrder();
    order.setOrderId("1");
    order.setClientOid("client-1");
    order.setInstrumentName("BTC_USDT");
    order.setSide("BUY");
    order.setStatus("ACTIVE");
    order.setLimitPrice("100");
    order.setQuantity("2");
    order.setAvgPrice("0");
    order.setCumulativeQuantity("0");
    order.setCreateTime(1000L);

    LimitOrder adapted = CryptoComAdapters.adaptOrder(order);

    assertThat(adapted.getId()).isEqualTo("1");
    assertThat(adapted.getUserReference()).isEqualTo("client-1");
    assertThat(adapted.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(adapted.getType()).isEqualTo(OrderType.BID);
    assertThat(adapted.getLimitPrice()).isEqualByComparingTo("100");
    assertThat(adapted.getOriginalAmount()).isEqualByComparingTo("2");
    assertThat(adapted.getStatus()).isEqualTo(OrderStatus.NEW);
    assertThat(adapted.getTimestamp()).isEqualTo(new Date(1000L));

    assertThat(CryptoComAdapters.adaptOrder(null)).isNull();
  }

  @Test
  public void testAdaptOpenOrders() {
    CryptoComOrder order = new CryptoComOrder();
    order.setInstrumentName("BTC_USDT");
    order.setSide("BUY");

    OpenOrders openOrders = CryptoComAdapters.adaptOpenOrders(Arrays.asList(order));
    assertThat(openOrders.getOpenOrders()).hasSize(1);

    assertThat(CryptoComAdapters.adaptOpenOrders(null).getOpenOrders()).isEmpty();
  }

  @Test
  public void testAdaptUserTrade() {
    CryptoComUserTrade trade = new CryptoComUserTrade();
    trade.setTradeId("1");
    trade.setOrderId("2");
    trade.setInstrumentName("BTC_USDT");
    trade.setSide("SELL");
    trade.setTradedPrice("100");
    trade.setTradedQuantity("1");
    trade.setFees("0.01");
    trade.setFeeInstrumentName("USDT");
    trade.setCreateTime(1000L);

    UserTrade adapted = CryptoComAdapters.adaptUserTrade(trade);

    assertThat(adapted.getId()).isEqualTo("1");
    assertThat(adapted.getOrderId()).isEqualTo("2");
    assertThat(adapted.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(adapted.getType()).isEqualTo(OrderType.ASK);
    assertThat(adapted.getPrice()).isEqualByComparingTo("100");
    assertThat(adapted.getOriginalAmount()).isEqualByComparingTo("1");
    assertThat(adapted.getFeeAmount()).isEqualByComparingTo("0.01");
    assertThat(adapted.getFeeCurrency()).isEqualTo(Currency.USDT);

    assertThat(CryptoComAdapters.adaptUserTrade(null)).isNull();
  }

  @Test
  public void testAdaptUserTrades() {
    CryptoComUserTrade trade = new CryptoComUserTrade();
    trade.setInstrumentName("BTC_USDT");
    trade.setSide("BUY");

    UserTrades adapted = CryptoComAdapters.adaptUserTrades(Arrays.asList(trade));
    assertThat(adapted.getUserTrades()).hasSize(1);

    assertThat(CryptoComAdapters.adaptUserTrades(null).getUserTrades()).isEmpty();
  }

  @Test
  public void testAdaptAccountInfo() {
    CryptoComBalance.PositionBalance position = new CryptoComBalance.PositionBalance();
    position.setInstrumentName("USDT");
    position.setQuantity("100");
    position.setReservedQty("10");

    CryptoComBalance balance = new CryptoComBalance();
    balance.setPositionBalances(Arrays.asList(position));

    AccountInfo accountInfo = CryptoComAdapters.adaptAccountInfo(Arrays.asList(balance));

    Balance usdtBalance = accountInfo.getWallet().getBalance(Currency.USDT);
    assertThat(usdtBalance.getTotal()).isEqualByComparingTo("100");
    assertThat(usdtBalance.getAvailable()).isEqualByComparingTo("90");
    assertThat(usdtBalance.getFrozen()).isEqualByComparingTo("10");
  }

  @Test
  public void testAdaptDepositRecord() {
    CryptoComDepositRecord record = new CryptoComDepositRecord();
    record.setId("1");
    record.setCurrency("BTC");
    record.setNetwork("BTC");
    record.setAmount("1.0");
    record.setFee("0.0001");
    record.setAddress("addr");
    record.setStatus("1");
    record.setCreateTime(1000L);

    FundingRecord adapted = CryptoComAdapters.adaptDepositRecord(record);

    assertThat(adapted.getType()).isEqualTo(FundingRecord.Type.DEPOSIT);
    assertThat(adapted.getCurrency()).isEqualTo(Currency.BTC);
    assertThat(adapted.getAmount()).isEqualByComparingTo("1.0");
    assertThat(adapted.getInternalId()).isEqualTo("1");
    assertThat(adapted.getStatus()).isEqualTo(FundingRecord.Status.COMPLETE);
  }

  @Test
  public void testAdaptWithdrawalRecord() {
    CryptoComWithdrawalRecord record = new CryptoComWithdrawalRecord();
    record.setId("2");
    record.setCurrency("BTC");
    record.setNetwork("BTC");
    record.setAmount("1.0");
    record.setFee("0.0001");
    record.setAddress("addr");
    record.setStatus("2");
    record.setCreateTime(1000L);
    record.setTxid("tx-1");

    FundingRecord adapted = CryptoComAdapters.adaptWithdrawalRecord(record);

    assertThat(adapted.getType()).isEqualTo(FundingRecord.Type.WITHDRAWAL);
    assertThat(adapted.getBlockchainTransactionHash()).isEqualTo("tx-1");
    assertThat(adapted.getStatus()).isEqualTo(FundingRecord.Status.FAILED);
  }

  @Test
  public void testAdaptDepositAndWithdrawalRecords() {
    CryptoComDepositRecord deposit = new CryptoComDepositRecord();
    deposit.setCurrency("BTC");
    assertThat(CryptoComAdapters.adaptDepositRecords(Arrays.asList(deposit))).hasSize(1);
    assertThat(CryptoComAdapters.adaptDepositRecords(null)).isEmpty();

    CryptoComWithdrawalRecord withdrawal = new CryptoComWithdrawalRecord();
    withdrawal.setCurrency("BTC");
    assertThat(CryptoComAdapters.adaptWithdrawalRecords(Arrays.asList(withdrawal))).hasSize(1);
    assertThat(CryptoComAdapters.adaptWithdrawalRecords(null)).isEmpty();
  }
}
