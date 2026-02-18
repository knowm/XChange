package info.bitrich.xchangestream.bitfinex;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitfinex.config.Config;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.MarginMode;
import org.knowm.xchange.dto.account.OpenPosition.Type;

class BitfinexStreamingTradeServiceTest {

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  BitfinexStreamingService bitfinexStreamingService;

  BitfinexStreamingTradeService bitfinexStreamingTradeService;

  @BeforeAll
  public static void initAdapters() {
    BitfinexAdapters.putCurrencyMapping("BTCF0", "BTC");
    BitfinexAdapters.putCurrencyMapping("USTF0", "USDT");
  }

  @BeforeEach
  public void setUp() {
    bitfinexStreamingService =
        new BitfinexStreamingService(BitfinexStreamingExchange.API_URI, null);
    bitfinexStreamingService.setApiKey("a");
    bitfinexStreamingTradeService = new BitfinexStreamingTradeService(bitfinexStreamingService);
  }

  @Test
  void position_changes() throws IOException {

    JsonNode jsonNode =
        objectMapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("position_update.json"));
    var test = bitfinexStreamingTradeService.getPositionChanges(null).test();
    bitfinexStreamingService.handleMessage(jsonNode);

    var expected =
        OpenPosition.builder()
            .id("185023623")
            .instrument(new FuturesContract(CurrencyPair.BTC_USDT, "PERP"))
            .type(Type.LONG)
            .marginMode(MarginMode.CROSS)
            .size(new BigDecimal("0.000040"))
            .price(new BigDecimal("108470"))
            .liquidationPrice(new BigDecimal("54504.81225"))
            .unRealisedPnl(new BigDecimal("0.028"))
            .build();

    assertThat(test.values()).hasSize(1);
    assertThat(test.values()).first().usingRecursiveComparison().isEqualTo(expected);
  }
}
