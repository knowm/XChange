package org.knowm.xchange.gateio.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.dto.GateioOrderType;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper.GateioMarketInfo;

public class GateioMarketDataJsonTest {

  @Test
  public void testDeserializeMarketInfo() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GateioMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/gateio/dto/marketdata/example-market-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GateioMarketInfoWrapper marketInfoWrapper = mapper.readValue(is, GateioMarketInfoWrapper.class);

    Map<CurrencyPair, GateioMarketInfo> marketInfoMap = marketInfoWrapper.getMarketInfoMap();
    assertThat(marketInfoMap).hasSize(2);

    CurrencyPair pair = new CurrencyPair("LTC", "CNY");
    GateioMarketInfo marketInfo = marketInfoMap.get(pair);
    assertThat(marketInfo.getCurrencyPair()).isEqualTo(pair);
    assertThat(marketInfo.getDecimalPlaces()).isEqualTo(2);
    assertThat(marketInfo.getMinAmount()).isEqualTo(".5");
    assertThat(marketInfo.getFee()).isEqualTo("0");
  }

  @Test
  public void testDeserializeCurrencyPairs() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GateioMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/gateio/dto/marketdata/example-pairs-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GateioCurrencyPairs currencyPairs = mapper.readValue(is, GateioCurrencyPairs.class);

    Collection<CurrencyPair> pairs = currencyPairs.getPairs();
    assertThat(pairs).hasSize(83);

    assertThat(pairs.contains(new CurrencyPair("TIPS", "CNY"))).isTrue();
  }

  @Test
  public void testDeserializeDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GateioMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/gateio/dto/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GateioDepth depth = mapper.readValue(is, GateioDepth.class);

    assertThat(depth.isResult()).isTrue();

    List<GateioPublicOrder> asks = depth.getAsks();
    assertThat(asks).hasSize(3);

    GateioPublicOrder ask = asks.get(0);
    assertThat(ask.getPrice()).isEqualTo("0.17936");
    assertThat(ask.getAmount()).isEqualTo("687");
  }

  @Test
  public void testDeserializeTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GateioMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/gateio/dto/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GateioTradeHistory tradeHistory = mapper.readValue(is, GateioTradeHistory.class);

    assertThat(tradeHistory.isResult()).isTrue();
    assertThat(tradeHistory.getElapsed()).isEqualTo("0.634ms");

    List<GateioTradeHistory.GateioPublicTrade> trades = tradeHistory.getTrades();
    assertThat(trades).hasSize(2);

    GateioTradeHistory.GateioPublicTrade trade = trades.get(0);
    assertThat(trade.getDate()).isEqualTo(1393908191);
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("3942"));
    assertThat(trade.getAmount()).isEqualTo(new BigDecimal("0.0129"));
    assertThat(trade.getTradeId()).isEqualTo("5600118");
    Assertions.assertThat(trade.getType()).isEqualTo(GateioOrderType.SELL);
  }
}
