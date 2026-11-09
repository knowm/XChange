package org.knowm.xchange.bitfinex.v1;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalancesResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositWithdrawalHistoryResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexFeesJSONTest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexTradingFeeResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexWalletJSONTest;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLevel;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexOrderStatusResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexTradeResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.instrument.Instrument;

class BitfinexAdaptersTest {

  private static final String MARKET = "bitfinex";
  private static final String SYMBOL = "BTCUSD";

  @Test
  void shouldAdaptDynamicTradingFees() throws Exception {
    InputStream is =
        BitfinexFeesJSONTest.class.getResourceAsStream(
            "/v1/account/example-account-info-fees.json");
    ObjectMapper mapper = new ObjectMapper();
    BitfinexTradingFeeResponse[] readValues =
        mapper.readValue(is, BitfinexTradingFeeResponse[].class);
    assertThat(readValues.length).isEqualTo(2);
    List<Instrument> currencyPairs =
        new ArrayList<>(
            Arrays.asList(
                CurrencyPair.BTC_LTC,
                CurrencyPair.LTC_AUD,
                CurrencyPair.ETH_BTC,
                CurrencyPair.DGC_BTC,
                CurrencyPair.BTC_USD));
    Map<Instrument, Fee> feesPerPair =
        BitfinexAdapters.adaptDynamicTradingFees(readValues, currencyPairs);
    assertThat(feesPerPair.size()).isEqualTo(currencyPairs.size());

    BigDecimal point001 = BigDecimal.ONE.divide(BigDecimal.ONE.scaleByPowerOfTen(3));
    BigDecimal point002 = point001.multiply(new BigDecimal(2));
    BigDecimal point00025 = new BigDecimal(25).divide(BigDecimal.ONE.scaleByPowerOfTen(5));
    BigDecimal point0001 = BigDecimal.ONE.divide(BigDecimal.ONE.scaleByPowerOfTen(4));

    Fee btcLTCFee = feesPerPair.get(CurrencyPair.BTC_LTC);
    Fee btcExpectedFee = new Fee(point001, point002);
    assertThat(btcLTCFee).isEqualTo(btcExpectedFee);
    Fee btcUSDFee = feesPerPair.get(CurrencyPair.BTC_USD);
    assertThat(btcUSDFee).isEqualTo(btcExpectedFee);
    Fee ltcFee = feesPerPair.get(CurrencyPair.LTC_AUD);
    assertThat(ltcFee).isEqualTo(new Fee(point001, point002));
    Fee ethFee = feesPerPair.get(CurrencyPair.ETH_BTC);
    assertThat(ethFee).isEqualTo(new Fee(point001, point002));
    Fee dgcFee = feesPerPair.get(CurrencyPair.DGC_BTC);
    assertThat(dgcFee).isEqualTo(new Fee(point00025, point0001));
  }

  @Test
  void shouldAdaptBalances() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        BitfinexWalletJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitfinex/v1/dto/account/example-account-info-balance.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitfinexBalancesResponse[] response = mapper.readValue(is, BitfinexBalancesResponse[].class);

    List<Wallet> wallets = BitfinexAdapters.adaptWallets(response);

    Wallet exchangeWallet =
        wallets.stream()
            .filter(wallet -> "exchange".equals(wallet.getId()))
            .findFirst()
            .orElse(null);
    assertThat(exchangeWallet).as("Exchange wallet is missing").isNotNull();
    Wallet tradingWallet =
        wallets.stream()
            .filter(wallet -> "trading".equals(wallet.getId()))
            .findFirst()
            .orElse(null);
    assertThat(tradingWallet).as("Trading wallet is missing").isNotNull();
    Wallet depositWallet =
        wallets.stream()
            .filter(wallet -> "deposit".equals(wallet.getId()))
            .findFirst()
            .orElse(null);
    assertThat(depositWallet).as("Deposit wallet is missing").isNotNull();

    Balance tradingUsdBalance = tradingWallet.getBalance(Currency.USD);
    assertThat(tradingUsdBalance).isNotNull();
    assertThat(tradingUsdBalance.getTotal()).isEqualTo(new BigDecimal("100"));
    assertThat(tradingUsdBalance.getAvailable()).isEqualTo(new BigDecimal("50"));

    Balance tradingBtcBalance = tradingWallet.getBalance(Currency.BTC);
    assertThat(tradingBtcBalance).isNotNull();
    assertThat(tradingBtcBalance.getTotal()).isEqualTo(BigDecimal.ZERO);
    assertThat(tradingBtcBalance.getAvailable()).isEqualTo(BigDecimal.ZERO);

    Balance exchangeUsdBalance = exchangeWallet.getBalance(Currency.USD);
    assertThat(exchangeUsdBalance).isNotNull();
    assertThat(exchangeUsdBalance.getTotal()).isEqualTo(new BigDecimal("5.5"));
    assertThat(exchangeUsdBalance.getAvailable()).isEqualTo(new BigDecimal("5.5"));

    Balance exchangeBtcBalance = exchangeWallet.getBalance(Currency.BTC);
    assertThat(exchangeBtcBalance).isNotNull();
    assertThat(exchangeBtcBalance.getTotal()).isEqualTo(BigDecimal.ZERO);
    assertThat(exchangeBtcBalance.getAvailable()).isEqualTo(BigDecimal.ZERO);

    Balance depositUsdBalance = depositWallet.getBalance(Currency.USD);
    assertThat(depositUsdBalance).isNotNull();
    assertThat(depositUsdBalance.getTotal()).isEqualTo(new BigDecimal("69"));
    assertThat(depositUsdBalance.getAvailable()).isEqualTo(new BigDecimal("42"));

    Balance depositBtcBalance = depositWallet.getBalance(Currency.BTC);
    assertThat(depositBtcBalance).isNotNull();
    assertThat(depositBtcBalance.getTotal()).isEqualTo(new BigDecimal("50"));
    assertThat(depositBtcBalance.getAvailable()).isEqualTo(new BigDecimal("30"));
  }

  @Test
  void adaptOrdersToOrdersContainer() {

    BitfinexLevel[] levels = initLevels();
    BitfinexAdapters.OrdersContainer container =
        BitfinexAdapters.adaptOrders(levels, CurrencyPair.BTC_USD, OrderType.BID);

    BitfinexLevel lastLevel = levels[levels.length - 1];
    assertThat(container.getTimestamp())
        .isEqualTo(lastLevel.getTimestamp().multiply(new BigDecimal(1000L)).longValue());
    assertThat(levels.length).isEqualTo(container.getLimitOrders().size());

    for (int i = 0; i < levels.length; i++) {
      LimitOrder order = container.getLimitOrders().get(i);
      long expectedTimestampMillis =
          levels[i].getTimestamp().multiply(new BigDecimal(1000L)).longValue();

      assertThat(order.getOriginalAmount()).isEqualTo(levels[i].getAmount());
      assertThat(order.getTimestamp().getTime()).isEqualTo(expectedTimestampMillis);
      assertThat(order.getLimitPrice()).isEqualTo(levels[i].getPrice());
    }
  }

  /**
   * Create 60 {@link BitfinexLevel}s. The values increase as the array index does. The timestamps
   * increase by 1 second + 1 minute + 1 hour + 1 day in order to test the correct handling of the
   * given timestamp.
   *
   * @return The generated responses.
   */
  private BitfinexLevel[] initLevels() {

    BitfinexLevel[] responses = new BitfinexLevel[60];

    for (int i = 0; i < responses.length; i++) {
      BigDecimal price = new BigDecimal(350L + i);
      BigDecimal timestamp =
          new BigDecimal("1414669893.823615468")
              .add(new BigDecimal(i * (1 + 60 + 60 * 60 + 60 * 60 * 24)));
      BigDecimal amount = new BigDecimal(1L + i);
      responses[i] = new BitfinexLevel(price, amount, timestamp);
    }

    return responses;
  }

  @Test
  void adaptOrdersToOpenOrders() {

    BitfinexOrderStatusResponse[] responses = initOrderStatusResponses();
    OpenOrders orders = BitfinexAdapters.adaptOrders(responses);
    assertThat(responses.length).isEqualTo(orders.getOpenOrders().size());

    for (int i = 0; i < responses.length; i++) {
      LimitOrder order = orders.getOpenOrders().get(i);
      long expectedTimestampMillis =
          responses[i].getTimestamp().multiply(new BigDecimal(1000L)).longValue();
      Order.OrderType expectedOrderType =
          responses[i].getSide().equalsIgnoreCase("buy")
              ? Order.OrderType.BID
              : Order.OrderType.ASK;

      assertThat(order.getId()).isEqualTo(String.valueOf(responses[i].getId()));
      assertThat(order.getOriginalAmount()).isEqualTo(responses[i].getOriginalAmount());
      assertThat(order.getCurrencyPair()).isEqualTo(BitfinexAdapters.adaptCurrencyPair(SYMBOL));
      assertThat(order.getType()).isEqualTo(expectedOrderType);
      assertThat(order.getTimestamp().getTime()).isEqualTo(expectedTimestampMillis);
      assertThat(order.getLimitPrice()).isEqualTo(responses[i].getPrice());
    }
  }

  /**
   * Create 60 {@link BitfinexOrderStatusResponse}s. The values increase as array index does. The
   * timestamps increase by 1 second + 1 minute + 1 hour + 1 day in order to test the correct
   * handling of the given timestamp.
   *
   * @return The generated responses.
   */
  private BitfinexOrderStatusResponse[] initOrderStatusResponses() {

    BitfinexOrderStatusResponse[] responses = new BitfinexOrderStatusResponse[60];

    for (int i = 0; i < responses.length; i++) {
      BigDecimal price = new BigDecimal(350L + i);
      BigDecimal avgExecutionPrice = price.add(new BigDecimal(0.25 * i));
      String side = i % 2 == 0 ? "buy" : "sell";
      String type = "limit";
      BigDecimal timestamp =
          new BigDecimal("1414658239.41373654")
              .add(new BigDecimal(i * (1 + 60 + 60 * 60 + 60 * 60 * 24)));
      boolean isLive = false;
      boolean isCancelled = false;
      boolean wasForced = false;
      BigDecimal originalAmount = new BigDecimal("70");
      BigDecimal remainingAmount = originalAmount.subtract(new BigDecimal(i * 1));
      BigDecimal executedAmount = originalAmount.subtract(remainingAmount);
      responses[i] =
          new BitfinexOrderStatusResponse(
              i,
              SYMBOL,
              price,
              avgExecutionPrice,
              side,
              type,
              timestamp,
              isLive,
              isCancelled,
              wasForced,
              originalAmount,
              remainingAmount,
              executedAmount);
    }

    return responses;
  }

  @Test
  void adaptTradeHistory() {

    BitfinexTradeResponse[] responses = initTradeResponses();
    Trades trades = BitfinexAdapters.adaptTradeHistory(responses, SYMBOL);
    assertThat(responses.length).isEqualTo(trades.getTrades().size());

    for (int i = 0; i < responses.length; i++) {
      Trade trade = trades.getTrades().get(i);
      long expectedTimestampMillis =
          responses[i].getTimestamp().multiply(new BigDecimal(1000L)).longValue();
      Order.OrderType expectedOrderType =
          responses[i].getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;

      assertThat(trade.getPrice()).isEqualTo(responses[i].getPrice());
      assertThat(trade.getOriginalAmount()).isEqualTo(responses[i].getAmount());
      assertThat(trade.getCurrencyPair()).isEqualTo(BitfinexAdapters.adaptCurrencyPair(SYMBOL));
      assertThat(trade.getTimestamp().getTime()).isEqualTo(expectedTimestampMillis);
      assertThat(trade.getType()).isEqualTo(expectedOrderType);
      assertThat(trade.getId()).isEqualTo(responses[i].getTradeId());
    }
  }

  /**
   * Create 60 {@link BitfinexTradeResponse}s. The values increase as array index does. The
   * timestamps increase by 1 second + 1 minute + 1 hour + 1 day in order to test the correct
   * handling of the given timestamp.
   *
   * @return The generated responses.
   */
  private BitfinexTradeResponse[] initTradeResponses() {

    BitfinexTradeResponse[] responses = new BitfinexTradeResponse[60];
    int tradeId = 2000;
    int orderId = 1000;

    for (int i = 0; i < responses.length; i++) {
      BigDecimal price = new BigDecimal(350L + i);
      BigDecimal amount = new BigDecimal(1L + i);
      BigDecimal timestamp =
          new BigDecimal("1414658239.41373654")
              .add(new BigDecimal(i * (1 + 60 + 60 * 60 + 60 * 60 * 24)));
      String type = i % 2 == 0 ? "buy" : "sell";
      String tradeIdString = String.valueOf(tradeId++);
      String orderIdString = String.valueOf(orderId++);
      BigDecimal feeAmount = new BigDecimal(0L);
      String feeCurrency = "USD";
      responses[i] =
          new BitfinexTradeResponse(
              price,
              amount,
              timestamp,
              MARKET,
              type,
              tradeIdString,
              orderIdString,
              feeAmount,
              feeCurrency);
    }

    return responses;
  }

  @Test
  void adaptFundingHistory() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        BitfinexAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitfinex/v1/dto/account/example-deposit-withdrawal-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitfinexDepositWithdrawalHistoryResponse[] response =
        mapper.readValue(is, BitfinexDepositWithdrawalHistoryResponse[].class);

    List<FundingRecord> fundingRecords = BitfinexAdapters.adaptFundingHistory(response);

    for (FundingRecord record : fundingRecords) {
      if (record.getType().name().equalsIgnoreCase(FundingRecord.Type.DEPOSIT.name())) {
        assertThat(record.getStatus()).isEqualTo(FundingRecord.Status.PROCESSING);
        assertThat(record.getAmount()).isEqualTo(new BigDecimal("0.01"));
        assertThat(record.getAddress()).isEqualTo("jlsd98087sdfkjldsflj432kjlsdf8");
        assertThat(record.getBlockchainTransactionHash()).isNull();
        assertThat(record.getCurrency()).isEqualTo(Currency.BTC);
      } else {
        assertThat(record.getStatus()).isEqualTo(FundingRecord.Status.COMPLETE);
        assertThat(record.getAmount()).isEqualTo(new BigDecimal("0.07"));
        assertThat(record.getAddress()).isEqualTo("3QXYWgRGX2BPYBpUDBssGbeWEa5zq6snBZ");
        assertThat(record.getDescription())
            .isEqualTo("3QXYWgRGX2BPYBpUDBssGbeWEa5zq6snBZ, txid: offchain transfer");
        assertThat(record.getBlockchainTransactionHash()).isNull();
        assertThat(record.getDescription())
            .isEqualTo("3QXYWgRGX2BPYBpUDBssGbeWEa5zq6snBZ, txid: offchain transfer");
        assertThat(record.getCurrency()).isEqualTo(Currency.BTC);
      }
    }
  }

  @Test
  void adaptCurrencyPair() {
    final List<String> currencyPairStrings =
        Arrays.asList("btcusd", "ethusd", "ethbtc", "dusk:usd", "tknusd");
    final List<CurrencyPair> currencyPairs =
        currencyPairStrings.stream()
            .map(BitfinexAdapters::adaptCurrencyPair)
            .collect(Collectors.toList());
    assertThat(currencyPairs)
        .isEqualTo(
            Arrays.asList(
                CurrencyPair.BTC_USD,
                CurrencyPair.ETH_USD,
                CurrencyPair.ETH_BTC,
                new CurrencyPair("DUSK/USD"),
                new CurrencyPair("TKN/USD")));
  }
}
