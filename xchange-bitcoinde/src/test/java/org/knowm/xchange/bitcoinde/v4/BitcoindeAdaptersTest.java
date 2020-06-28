package org.knowm.xchange.bitcoinde.v4;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.bitcoinde.v4.dto.*;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
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
}
