package org.knowm.xchange.bitso.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bitso.BitsoJacksonObjectMapperFactory;
import org.knowm.xchange.bitso.dto.marketdata.BitsoAvailableBooks;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTrades;

/** Tests DTOs against real API responses from Bitso API v3 */
public class BitsoRealApiResponseTest {

  private final ObjectMapper objectMapper = BitsoJacksonObjectMapperFactory.getInstance();

  @Test
  public void testRealAvailableBooksResponse() throws Exception {
    // Load real API response from Bitso
    BitsoAvailableBooks availableBooks =
        loadJsonResource("available_books_response.json", BitsoAvailableBooks.class);

    assertThat(availableBooks).isNotNull();
    assertThat(availableBooks.getSuccess()).isTrue();
    assertThat(availableBooks.getPayload()).isNotNull();
    assertThat(availableBooks.getPayload()).isNotEmpty();

    // Verify we have some expected trading pairs
    boolean hasBtcMxn =
        availableBooks.getPayload().stream().anyMatch(book -> "btc_mxn".equals(book.getBook()));
    assertThat(hasBtcMxn).isTrue();

    // Verify book structure is correct
    BitsoAvailableBooks.BitsoBook firstBook = availableBooks.getPayload().get(0);
    assertThat(firstBook.getBook()).isNotBlank();
    assertThat(firstBook.getMinimumAmount()).isNotNull();
    assertThat(firstBook.getMaximumAmount()).isNotNull();
    assertThat(firstBook.getMinimumPrice()).isNotNull();
    assertThat(firstBook.getMaximumPrice()).isNotNull();
  }

  @Test
  public void testRealTickerResponse() throws Exception {
    // Load real API response from Bitso
    BitsoTicker ticker = loadJsonResource("ticker_btc_mxn_response.json", BitsoTicker.class);

    assertThat(ticker).isNotNull();
    assertThat(ticker.getSuccess()).isTrue();
    assertThat(ticker.getPayload()).isNotNull();

    BitsoTicker.BitsoTickerData payload = ticker.getPayload();
    assertThat(payload.getBook()).isEqualTo("btc_mxn");
    assertThat(payload.getLast()).isNotNull();
    assertThat(payload.getHigh()).isNotNull();
    assertThat(payload.getLow()).isNotNull();
    assertThat(payload.getVolume()).isNotNull();
    assertThat(payload.getVwap()).isNotNull();
    assertThat(payload.getAsk()).isNotNull();
    assertThat(payload.getBid()).isNotNull();
    assertThat(payload.getCreatedAt()).isNotNull();
    assertThat(payload.getChange24()).isNotNull();

    // Test legacy compatibility methods
    assertThat(ticker.getLast()).isNotNull();
    assertThat(ticker.getHigh()).isNotNull();
    assertThat(ticker.getLow()).isNotNull();
    assertThat(ticker.getVwap()).isNotNull();
    assertThat(ticker.getVolume()).isNotNull();
    assertThat(ticker.getAsk()).isNotNull();
    assertThat(ticker.getBid()).isNotNull();

    // Verify values are positive (sanity check)
    assertThat(ticker.getLast()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getVolume()).isGreaterThan(BigDecimal.ZERO);
  }

  @Test
  public void testRealOrderBookResponse() throws Exception {
    // Load real API response from Bitso
    BitsoOrderBook orderBook =
        loadJsonResource("order_book_btc_mxn_response.json", BitsoOrderBook.class);

    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getSuccess()).isTrue();
    assertThat(orderBook.getPayload()).isNotNull();

    BitsoOrderBook.BitsoOrderBookData payload = orderBook.getPayload();
    assertThat(payload.getAsks()).isNotNull();
    assertThat(payload.getBids()).isNotNull();
    assertThat(payload.getAsks()).isNotEmpty();
    assertThat(payload.getBids()).isNotEmpty();
    assertThat(payload.getSequence()).isNotBlank();

    // Test first bid/ask entry structure
    BitsoOrderBook.BitsoOrderBookEntry firstBid = payload.getBids().get(0);
    assertThat(firstBid.getBook()).isEqualTo("btc_mxn");
    assertThat(firstBid.getPrice()).isNotNull();
    assertThat(firstBid.getAmount()).isNotNull();

    BitsoOrderBook.BitsoOrderBookEntry firstAsk = payload.getAsks().get(0);
    assertThat(firstAsk.getBook()).isEqualTo("btc_mxn");
    assertThat(firstAsk.getPrice()).isNotNull();
    assertThat(firstAsk.getAmount()).isNotNull();

    // Test legacy compatibility methods
    assertThat(orderBook.getAsks()).isNotEmpty();
    assertThat(orderBook.getBids()).isNotEmpty();

    // Verify order structure (price, amount pairs) from legacy methods
    assertThat(orderBook.getAsks().get(0)).hasSize(2); // [price, amount]
    assertThat(orderBook.getBids().get(0)).hasSize(2); // [price, amount]

    // Verify asks are higher than bids (sanity check) using legacy methods
    BigDecimal highestBid = orderBook.getBids().get(0).get(0);
    BigDecimal lowestAsk = orderBook.getAsks().get(0).get(0);
    assertThat(lowestAsk).isGreaterThan(highestBid);
  }

  @Test
  public void testRealTradesResponse() throws Exception {
    // Load real API response from Bitso
    BitsoTrades trades = loadJsonResource("trades_btc_mxn_response.json", BitsoTrades.class);

    assertThat(trades).isNotNull();
    assertThat(trades.getSuccess()).isTrue();
    assertThat(trades.getPayload()).isNotNull();
    assertThat(trades.getPayload()).isNotEmpty();

    // Verify trade structure
    BitsoTrades.BitsoTrade firstTrade = trades.getPayload().get(0);
    assertThat(firstTrade.getBook()).isEqualTo("btc_mxn");
    assertThat(firstTrade.getCreatedAt()).isNotBlank();
    assertThat(firstTrade.getAmount()).isNotNull();
    assertThat(firstTrade.getPrice()).isNotNull();
    assertThat(firstTrade.getTid()).isNotNull();
    assertThat(firstTrade.getMakerSide()).isIn("buy", "sell");

    // Verify all trades have required fields
    for (BitsoTrades.BitsoTrade trade : trades.getPayload()) {
      assertThat(trade.getBook()).isNotBlank();
      assertThat(trade.getCreatedAt()).isNotBlank();
      assertThat(trade.getAmount()).isNotNull();
      assertThat(trade.getPrice()).isNotNull();
      assertThat(trade.getTid()).isNotNull();
      assertThat(trade.getMakerSide()).isIn("buy", "sell");

      // Verify numeric values are valid
      assertThat(trade.getAmount()).isGreaterThan(BigDecimal.ZERO);
      assertThat(trade.getPrice()).isGreaterThan(BigDecimal.ZERO);
    }
  }

  /** Helper method to load JSON resources from test/resources */
  private <T> T loadJsonResource(String resourceName, Class<T> clazz) throws IOException {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
      assertThat(is).isNotNull();
      return objectMapper.readValue(is, clazz);
    }
  }
}
