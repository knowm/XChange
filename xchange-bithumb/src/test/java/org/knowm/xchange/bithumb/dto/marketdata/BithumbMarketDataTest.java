package org.knowm.xchange.bithumb.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.BithumbAdaptersTest;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;

public class BithumbMarketDataTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testUnmarshallTicker() throws IOException {

    final InputStream is =
        BithumbMarketDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/marketdata/example-ticker.json");

    final BithumbTicker bithumbTicker = mapper.readValue(is, BithumbTicker.class);

    assertThat(bithumbTicker.getOpeningPrice()).isEqualTo("151300");
    assertThat(bithumbTicker.getClosingPrice()).isEqualTo("168900");
    assertThat(bithumbTicker.getMinPrice()).isEqualTo("148600");
    assertThat(bithumbTicker.getMaxPrice()).isEqualTo("171600");
    assertThat(bithumbTicker.getAveragePrice()).isEqualTo("161373.9643");
    assertThat(bithumbTicker.getUnitsTraded()).isEqualTo("294028.02849871");
    assertThat(bithumbTicker.getVolume1day()).isEqualTo("294028.02849871");
    assertThat(bithumbTicker.getVolume7day()).isEqualTo("1276650.256763659925784183");
    assertThat(bithumbTicker.getBuyPrice()).isEqualTo("168800");
    assertThat(bithumbTicker.getSellPrice()).isEqualTo("168900");
    assertThat(bithumbTicker.get_24HFluctate()).isEqualTo("17600");
    assertThat(bithumbTicker.get_24HFluctateRate()).isEqualTo("11.63");
    assertThat(bithumbTicker.getDate()).isEqualTo(1546440237614L);
  }

  @Test
  public void testUnmarshallTickers() throws IOException {

    // given
    final InputStream is =
        BithumbMarketDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/marketdata/all-ticker-data.json");

    // when
    final BithumbTickersReturn bithumbTickers = mapper.readValue(is, BithumbTickersReturn.class);

    // then
    assertThat(bithumbTickers.getTickers()).hasSize(3);
    assertThat(bithumbTickers.getTickers()).containsKeys("BTC", "ETH", "DASH");

    final BithumbTicker btc = bithumbTickers.getTickers().get("BTC");
    assertThat(btc.getOpeningPrice()).isEqualTo(BigDecimal.valueOf(4185000L));
    assertThat(btc.getClosingPrice()).isEqualTo(BigDecimal.valueOf(4297000L));
    assertThat(btc.getMinPrice()).isEqualTo(BigDecimal.valueOf(4137000L));
    assertThat(btc.getMaxPrice()).isEqualTo(BigDecimal.valueOf(4328000L));
    assertThat(btc.getAveragePrice()).isEqualTo(BigDecimal.valueOf(4252435.9159));
    assertThat(btc.getUnitsTraded()).isEqualTo(BigDecimal.valueOf(3815.4174696));
    assertThat(btc.getVolume1day()).isEqualTo(BigDecimal.valueOf(3815.4174696));
    assertThat(btc.getVolume7day()).isEqualTo(BigDecimal.valueOf(31223.31245306));
    assertThat(btc.getBuyPrice()).isEqualTo(BigDecimal.valueOf(4296000));
    assertThat(btc.getSellPrice()).isEqualTo(BigDecimal.valueOf(4297000));
    assertThat(btc.get_24HFluctate()).isEqualTo(BigDecimal.valueOf(112000));
    assertThat(btc.get_24HFluctateRate()).isEqualTo(BigDecimal.valueOf(2.67));
    assertThat(btc.getDate()).isEqualTo(1546440191110L);
  }

  @Test
  public void testUnmarshallOrderbook() throws IOException {

    final InputStream is =
        BithumbMarketDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/marketdata/example-orderbook.json");

    final BithumbOrderbook bithumbOrderbook = mapper.readValue(is, BithumbOrderbook.class);

    assertThat(bithumbOrderbook.getPaymentCurrency()).isEqualTo("KRW");
    assertThat(bithumbOrderbook.getOrderCurrency()).isEqualTo("ETH");

    final List<BithumbOrderbookEntry> bids = bithumbOrderbook.getBids();
    final List<BithumbOrderbookEntry> asks = bithumbOrderbook.getAsks();

    assertThat(bids.size()).isEqualTo(2);
    assertThat(bids.get(0).getQuantity()).isEqualTo("28.0241");
    assertThat(bids.get(0).getPrice()).isEqualTo("168400");

    assertThat(asks.size()).isEqualTo(2);
    assertThat(asks.get(0).getQuantity()).isEqualTo("49.5577");
    assertThat(asks.get(0).getPrice()).isEqualTo("168500");
  }

  @Test
  public void testUnmarshallOrderbookAll() throws IOException {

    final InputStream is =
        BithumbMarketDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/marketdata/example-orderbook-all.json");

    final BithumbOrderbookAll bithumbOrderbook = mapper.readValue(is, BithumbOrderbookAll.class);

    assertThat(bithumbOrderbook.getPaymentCurrency()).isEqualTo("KRW");
    assertThat(bithumbOrderbook.getTimestamp()).isEqualTo(1547301204217L);
    assertThat(bithumbOrderbook.getAdditionalProperties().size()).isEqualTo(2);
    assertThat(bithumbOrderbook.getAdditionalProperties()).containsKeys("BTC", "ETH");
  }

  @Test
  public void testAdaptTransactionHistory() throws IOException {

    // given
    InputStream is =
        BithumbAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/marketdata/example-transaction-history.json");

    final BithumbResponse<List<BithumbTransactionHistory>> transactionHistory =
        mapper.readValue(
            is, new TypeReference<BithumbResponse<List<BithumbTransactionHistory>>>() {});

    assertThat(transactionHistory.getData().size()).isEqualTo(3);
    // when
    final Trade trade =
        BithumbAdapters.adaptTransactionHistory(
            transactionHistory.getData().get(0), CurrencyPair.BTC_KRW);

    // then
    assertThat(trade.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(trade.getOriginalAmount()).isEqualTo(new BigDecimal("1.0"));
    assertThat(trade.getCurrencyPair()).isEqualTo(new CurrencyPair(Currency.BTC, Currency.KRW));
    assertThat(trade.getPrice()).isEqualTo(BigDecimal.valueOf(6779000));
    assertThat(trade.getTimestamp()).isNotNull();
    assertThat(trade.getId()).isNull();
  }
}
