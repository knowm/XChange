package org.knowm.xchange.bitcoinde.v4;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeAccountLedgerType;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderFlagsOrderQuantities;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderFlagsOrderRequirements;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderFlagsTradingPartnerInformation;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindePaymentOption;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeTrustLevel;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedgerWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOrdersWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

/** @author matthewdowney */
public class BitcoindeAdaptersTest {

  @Test
  public void testCompactOrderBookAdapter() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/compact_orderbook.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeCompactOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeCompactOrderbookWrapper.class);

    // Create a generic OrderBook object from a Bitcoinde specific OrderBook
    final OrderBook orderBook =
        BitcoindeAdapters.adaptCompactOrderBook(bitcoindeOrderBook, CurrencyPair.BTC_EUR);
    final LimitOrder firstBid = orderBook.getBids().get(0);

    // verify all fields are filled correctly
    assertThat(firstBid.getLimitPrice()).isEqualByComparingTo("2406.11");
    assertThat(firstBid.getType()).isEqualTo(OrderType.BID);
    assertThat(firstBid.getOriginalAmount()).isEqualByComparingTo("1.745");
    assertThat(firstBid.getInstrument()).isEqualTo(CurrencyPair.BTC_EUR);
  }

  @Test
  public void testOrderBookAdapter() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/orderbook.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeOrderbookWrapper.class);

    // Create a generic OrderBook object from a Bitcoinde specific OrderBook
    final OrderBook orderBook =
        BitcoindeAdapters.adaptOrderBook(
            bitcoindeOrderBook, bitcoindeOrderBook, CurrencyPair.BCH_EUR);
    final LimitOrder firstBid = orderBook.getBids().get(0);
    final BitcoindeOrderFlagsTradingPartnerInformation tpi =
        new BitcoindeOrderFlagsTradingPartnerInformation("bla", true, BitcoindeTrustLevel.GOLD);
    final BitcoindeOrderFlagsOrderRequirements or =
        new BitcoindeOrderFlagsOrderRequirements(BitcoindeTrustLevel.GOLD);
    final BitcoindeOrderFlagsOrderQuantities oqty =
        new BitcoindeOrderFlagsOrderQuantities(
            new BigDecimal("0.1"),
            new BigDecimal("0.5"),
            new BigDecimal("23.06"),
            new BigDecimal("115.28"));

    // verify all fields are filled correctly
    assertThat(firstBid.getLimitPrice()).isEqualByComparingTo("230.55");
    assertThat(firstBid.getType()).isEqualTo(OrderType.BID);
    assertThat(firstBid.getOriginalAmount()).isEqualByComparingTo("0.5");
    assertThat(firstBid.getInstrument()).isEqualTo(CurrencyPair.BCH_EUR);
    assertThat(firstBid.hasFlag(tpi)).isEqualTo(true);
    assertThat(firstBid.hasFlag(or)).isEqualTo(true);
    assertThat(firstBid.hasFlag(oqty)).isEqualTo(true);
    testTradingPartnerInformation(firstBid, tpi);
    testOrderRequirements(firstBid, or);
    testOrderQuantities(firstBid, oqty);
  }

  private void testTradingPartnerInformation(
      LimitOrder order, BitcoindeOrderFlagsTradingPartnerInformation tpi) {
    order.getOrderFlags().stream()
        .filter(flag -> flag instanceof BitcoindeOrderFlagsTradingPartnerInformation)
        .forEach(
            flag -> {
              assertThat(((BitcoindeOrderFlagsTradingPartnerInformation) flag).getUserName())
                  .isEqualTo(tpi.getUserName());
              assertThat(((BitcoindeOrderFlagsTradingPartnerInformation) flag).isKycFull())
                  .isEqualTo(tpi.isKycFull());
              assertThat(((BitcoindeOrderFlagsTradingPartnerInformation) flag).getTrustLevel())
                  .isEqualByComparingTo(tpi.getTrustLevel());
            });
  }

  private void testOrderRequirements(LimitOrder order, BitcoindeOrderFlagsOrderRequirements or) {
    order.getOrderFlags().stream()
        .filter(flag -> flag instanceof BitcoindeOrderFlagsOrderRequirements)
        .forEach(
            flag -> {
              assertThat(((BitcoindeOrderFlagsOrderRequirements) flag).getMinTrustLevel())
                  .isEqualByComparingTo(or.getMinTrustLevel());
            });
  }

  private void testOrderQuantities(LimitOrder order, BitcoindeOrderFlagsOrderQuantities qty) {
    order.getOrderFlags().stream()
        .filter(flag -> flag instanceof BitcoindeOrderFlagsOrderQuantities)
        .forEach(
            flag -> {
              assertThat(((BitcoindeOrderFlagsOrderQuantities) flag).getMinAmountToTrade())
                  .isEqualByComparingTo(qty.getMinAmountToTrade());
              assertThat(((BitcoindeOrderFlagsOrderQuantities) flag).getMaxAmountToTrade())
                  .isEqualByComparingTo(qty.getMaxAmountToTrade());
              assertThat(((BitcoindeOrderFlagsOrderQuantities) flag).getMinVolumeToPay())
                  .isEqualByComparingTo(qty.getMinVolumeToPay());
              assertThat(((BitcoindeOrderFlagsOrderQuantities) flag).getMaxVolumeToPay())
                  .isEqualByComparingTo(qty.getMaxVolumeToPay());
            });
  }

  @Test
  public void testTradesAdapter() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/trades.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeTradesWrapper bitcoindeTradesWrapper =
        mapper.readValue(is, BitcoindeTradesWrapper.class);

    // Use our adapter to get a generic Trades object from a
    // BitcoindeTrade[] object
    final Trades trades =
        BitcoindeAdapters.adaptTrades(bitcoindeTradesWrapper, CurrencyPair.BTC_EUR);
    final Trade firstTrade = trades.getTrades().get(0);

    // Make sure the adapter got all the data
    assertThat(trades.getTrades().size()).isEqualTo(92);
    assertThat(trades.getlastID()).isEqualTo(2844384);

    // Verify that all fields are filled
    assertThat(firstTrade.getId()).isEqualTo("2844111");
    assertThat(firstTrade.getPrice()).isEqualTo(new BigDecimal("2395"));
    assertThat(firstTrade.getOriginalAmount()).isEqualTo(new BigDecimal("0.08064516"));
    assertThat(firstTrade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(firstTrade.getTimestamp()).isEqualTo(new Date(1500717160L * 1000));
  }

  @Test
  public void testAccountInfoAdapter() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/account.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeAccountWrapper bitcoindeTradesWrapper =
        mapper.readValue(is, BitcoindeAccountWrapper.class);

    // Use our adapter to get a generic AccountInfo object from a BitcoindeData object
    final AccountInfo accountInfo = BitcoindeAdapters.adaptAccountInfo(bitcoindeTradesWrapper);
    assertThat(accountInfo.getWallets()).containsOnlyKeys("BTC", "BCH", "BTG", "BSV", "ETH");

    final Wallet btcWallet = accountInfo.getWallet("BTC");
    assertThat(btcWallet.getBalances()).containsOnlyKeys(Currency.BTC, Currency.EUR);
    assertThat(btcWallet.getBalances().get(Currency.BTC).getAvailable())
        .isEqualByComparingTo("0.009");
    assertThat(btcWallet.getBalances().get(Currency.BTC).getFrozen()).isEqualByComparingTo("0");
    assertThat(btcWallet.getBalances().get(Currency.BTC).getTotal()).isEqualByComparingTo("0.009");
    assertThat(btcWallet.getBalances().get(Currency.EUR).getAvailable())
        .isEqualByComparingTo("2000");
    assertThat(btcWallet.getBalances().get(Currency.EUR).getFrozen()).isEqualByComparingTo("0");
    assertThat(btcWallet.getBalances().get(Currency.EUR).getTotal()).isEqualByComparingTo("2000");

    final Wallet bchWallet = accountInfo.getWallet("BCH");
    assertThat(bchWallet.getBalances()).containsOnlyKeys(Currency.BCH, Currency.EUR);
    assertThat(bchWallet.getBalances().get(Currency.BCH).getAvailable())
        .isEqualByComparingTo("0.008");
    assertThat(bchWallet.getBalances().get(Currency.BCH).getFrozen()).isEqualByComparingTo("0");
    assertThat(bchWallet.getBalances().get(Currency.BCH).getTotal()).isEqualByComparingTo("0.008");
    assertThat(bchWallet.getBalances().get(Currency.EUR).getAvailable()).isEqualByComparingTo("0");
    assertThat(bchWallet.getBalances().get(Currency.EUR).getFrozen()).isEqualByComparingTo("0");
    assertThat(bchWallet.getBalances().get(Currency.EUR).getTotal()).isEqualByComparingTo("0");

    final Wallet btgWallet = accountInfo.getWallet("BTG");
    assertThat(btgWallet.getBalances()).containsOnlyKeys(Currency.BTG, Currency.EUR);
    assertThat(btgWallet.getBalances().get(Currency.BTG).getAvailable())
        .isEqualByComparingTo("0.007");
    assertThat(btgWallet.getBalances().get(Currency.BTG).getFrozen()).isEqualByComparingTo("0");
    assertThat(btgWallet.getBalances().get(Currency.BTG).getTotal()).isEqualByComparingTo("0.007");
    assertThat(btgWallet.getBalances().get(Currency.EUR).getAvailable()).isEqualByComparingTo("0");
    assertThat(btgWallet.getBalances().get(Currency.EUR).getFrozen()).isEqualByComparingTo("0");
    assertThat(btgWallet.getBalances().get(Currency.EUR).getTotal()).isEqualByComparingTo("0");

    final Wallet bsvWallet = accountInfo.getWallet("BSV");
    assertThat(bsvWallet.getBalances()).containsOnlyKeys(Currency.getInstance("BSV"));
    assertThat(bsvWallet.getBalances().get(Currency.getInstance("BSV")).getAvailable())
        .isEqualByComparingTo("0.006");
    assertThat(bsvWallet.getBalances().get(Currency.getInstance("BSV")).getFrozen())
        .isEqualByComparingTo("0");
    assertThat(bsvWallet.getBalances().get(Currency.getInstance("BSV")).getTotal())
        .isEqualByComparingTo("0.006");

    final Wallet ethWallet = accountInfo.getWallet("ETH");
    assertThat(ethWallet.getBalances()).containsOnlyKeys(Currency.ETH, Currency.EUR);
    assertThat(ethWallet.getBalances().get(Currency.ETH).getAvailable())
        .isEqualByComparingTo("0.06463044");
    assertThat(ethWallet.getBalances().get(Currency.ETH).getFrozen()).isEqualByComparingTo("0");
    assertThat(ethWallet.getBalances().get(Currency.ETH).getTotal())
        .isEqualByComparingTo("0.06463044");
    assertThat(ethWallet.getBalances().get(Currency.EUR).getAvailable()).isEqualByComparingTo("0");
    assertThat(ethWallet.getBalances().get(Currency.EUR).getFrozen()).isEqualByComparingTo("0");
    assertThat(ethWallet.getBalances().get(Currency.EUR).getTotal()).isEqualByComparingTo("0");
  }

  @Test
  public void testFundingHistoryAdapter() throws IOException {
    final InputStream is =
        BitcoindeAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/account_ledger.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    BitcoindeAccountLedgerWrapper accountLedgerWrapper =
        mapper.readValue(is, BitcoindeAccountLedgerWrapper.class);

    final List<FundingRecord> fundingRecords =
        BitcoindeAdapters.adaptFundingHistory(
            Currency.BTC, accountLedgerWrapper.getAccountLedgers(), false);

    // Make sure trade values are correct
    assertThat(fundingRecords).isNotEmpty();
    assertThat(fundingRecords.size()).isEqualTo(2);

    assertThat(fundingRecords.get(0).getAddress()).isNull();
    assertThat(fundingRecords.get(0).getAddressTag()).isNull();
    assertThat(fundingRecords.get(0).getDate()).isEqualTo("2015-08-12T13:05:02+02:00");
    assertThat(fundingRecords.get(0).getCurrency()).isEqualByComparingTo(Currency.BTC);
    assertThat(fundingRecords.get(0).getAmount()).isEqualByComparingTo("0.10000000");
    assertThat(fundingRecords.get(0).getInternalId()).isNull();
    assertThat(fundingRecords.get(0).getBlockchainTransactionHash())
        .isEqualTo("dqwdqwdwqwq4dqw4d5qd45qd45qwd4qw5df45g4r5g4trh4r5j5j4tz5j4tbc");
    assertThat(fundingRecords.get(0).getType()).isEqualByComparingTo(FundingRecord.Type.WITHDRAWAL);
    assertThat(fundingRecords.get(0).getBalance()).isEqualByComparingTo("4.71619794");
    assertThat(fundingRecords.get(0).getStatus())
        .isEqualByComparingTo(FundingRecord.Status.COMPLETE);
    assertThat(fundingRecords.get(0).getFee()).isNull();
    assertThat(fundingRecords.get(0).getDescription())
        .isEqualTo(BitcoindeAccountLedgerType.PAYOUT.getValue());

    assertThat(fundingRecords.get(1).getAddress()).isNull();
    assertThat(fundingRecords.get(1).getAddressTag()).isNull();
    assertThat(fundingRecords.get(1).getDate()).isEqualTo("2015-08-12T12:30:01+02:00");
    assertThat(fundingRecords.get(1).getCurrency()).isEqualByComparingTo(Currency.BTC);
    assertThat(fundingRecords.get(1).getAmount()).isEqualByComparingTo("1.91894200");
    assertThat(fundingRecords.get(1).getInternalId()).isNull();
    assertThat(fundingRecords.get(1).getBlockchainTransactionHash())
        .isEqualTo("bdgwflwguwgr884t34g4g555h4zr5j4fh5j48rg4s5bx2nt4jr5jr45j4r5j4");
    assertThat(fundingRecords.get(1).getType()).isEqualByComparingTo(FundingRecord.Type.WITHDRAWAL);
    assertThat(fundingRecords.get(1).getBalance()).isEqualByComparingTo("4.81619794");
    assertThat(fundingRecords.get(1).getStatus())
        .isEqualByComparingTo(FundingRecord.Status.COMPLETE);
    assertThat(fundingRecords.get(1).getFee()).isNull();
    assertThat(fundingRecords.get(1).getDescription())
        .isEqualTo(BitcoindeAccountLedgerType.PAYOUT.getValue());
  }

  @Test
  public void testOpenOrdersAdapter() throws IOException {
    final InputStream is =
        BitcoindeAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/my_orders.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    BitcoindeMyOrdersWrapper bitcoindeOpenOrdersWrapper =
        mapper.readValue(is, BitcoindeMyOrdersWrapper.class);

    final OpenOrders openOrders = BitcoindeAdapters.adaptOpenOrders(bitcoindeOpenOrdersWrapper);

    // Make sure trade values are correct
    assertThat(openOrders.getOpenOrders()).isNotNull();
    assertThat(openOrders.getOpenOrders().size()).isEqualTo(1);

    LimitOrder order = openOrders.getOpenOrders().get(0);
    assertThat(order.getId()).isEqualTo("VNSP86");
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(order.getTimestamp()).isEqualTo("2018-01-25T17:35:19+01:00");
    assertThat(order.getOriginalAmount()).isEqualByComparingTo("0.01");
    assertThat(order.getLimitPrice()).isEqualByComparingTo("6000");
    assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
    assertThat(order.getOrderFlags())
        .containsOnly(
            new BitcoindeOrderFlagsOrderQuantities(
                new BigDecimal("0.01"),
                new BigDecimal("0.01"),
                new BigDecimal("60"),
                new BigDecimal("60")),
            new BitcoindeOrderFlagsOrderRequirements(
                BitcoindeTrustLevel.SILVER,
                false,
                new String[] {
                  "AT", "BE", "BG", "CH", "CY", "CZ", "DE", "DK", "EE", "ES", "FI", "FR", "GB",
                  "GR", "HR", "HU", "IE", "IS", "IT", "LI", "LT", "LU", "LV", "MQ", "MT", "NL",
                  "NO", "PL", "PT", "RO", "SE", "SI", "SK"
                },
                BitcoindePaymentOption.EXPRESS_SEPA));

    assertThat(order.getAveragePrice()).isNull();
    assertThat(order.getCumulativeAmount()).isNull();
    assertThat(order.getFee()).isNull();
  }

  @Test
  public void testTradeHistoryAdapter() throws IOException {
    final InputStream is =
        BitcoindeAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/my_trades.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    BitcoindeMyTradesWrapper myTradesWrapper = mapper.readValue(is, BitcoindeMyTradesWrapper.class);

    final UserTrades userTrades = BitcoindeAdapters.adaptTradeHistory(myTradesWrapper);

    // Make sure trade values are correct
    assertThat(userTrades.getUserTrades()).isNotEmpty();
    assertThat(userTrades.getUserTrades().size()).isEqualTo(1);

    UserTrade trade = userTrades.getUserTrades().get(0);
    assertThat(trade.getType()).isEqualByComparingTo(OrderType.ASK);
    assertThat(trade.getOriginalAmount()).isEqualByComparingTo("0.5");
    assertThat(trade.getCurrencyPair()).isEqualByComparingTo(CurrencyPair.BTC_EUR);
    assertThat(trade.getPrice()).isEqualByComparingTo("250.55");
    assertThat(trade.getTimestamp()).isEqualTo("2015-01-10T15:00:00+02:00");
    assertThat(trade.getId()).isEqualTo("2EDYNS");
    assertThat(trade.getOrderId()).isNull();
    assertThat(trade.getFeeAmount()).isEqualByComparingTo("0.6");
    assertThat(trade.getFeeCurrency()).isEqualByComparingTo(Currency.EUR);
    assertThat(trade.getOrderUserReference()).isNull();
  }
}
