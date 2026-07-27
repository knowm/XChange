package info.bitrich.xchangestream.cryptocom.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.cryptocom.CryptoComStreamingAdapters;
import info.bitrich.xchangestream.cryptocom.CryptoComStreamingService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.UserTrade;

public class CryptoComUserTradeUpdateTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final CryptoComStreamingService service =
      new CryptoComStreamingService("wss://stream.crypto.com/exchange/v1/market");

  @Test
  public void testUnmarshalUserTradeUpdate() throws IOException {
    // given
    String resource = "/info/bitrich/xchangestream/cryptocom/dto/user-trade-update.json";

    // when
    JsonNode envelope =
        CryptoComStreamingTestSupport.readEnvelope(
            CryptoComUserTradeUpdateTest.class, resource, objectMapper);
    List<CryptoComUserTradeUpdate> updates =
        service.extractData(envelope, CryptoComUserTradeUpdate.class);

    // then
    assertThat(updates).hasSize(1);
    CryptoComUserTradeUpdate update = updates.get(0);
    assertThat(update.getTradeId()).isEqualTo("38246881");
    assertThat(update.getOrderId()).isEqualTo("18342311");
    assertThat(update.getInstrumentName()).isEqualTo("BTC_USDT");
    assertThat(update.getSide()).isEqualTo("BUY");
    assertThat(new BigDecimal(update.getPrice())).isEqualByComparingTo(new BigDecimal("50000.0"));
    assertThat(new BigDecimal(update.getQuantity()))
        .isEqualByComparingTo(new BigDecimal("0.005000"));
    assertThat(new BigDecimal(update.getFee())).isEqualByComparingTo(new BigDecimal("0.000005"));
    assertThat(update.getFeeCurrency()).isEqualTo("BTC");
    assertThat(update.getCreateTime()).isEqualTo(1785085695512L);
  }

  @Test
  public void testAdaptUserTrade() throws IOException {
    // given
    String resource = "/info/bitrich/xchangestream/cryptocom/dto/user-trade-update.json";
    JsonNode envelope =
        CryptoComStreamingTestSupport.readEnvelope(
            CryptoComUserTradeUpdateTest.class, resource, objectMapper);
    CryptoComUserTradeUpdate update =
        service.extractData(envelope, CryptoComUserTradeUpdate.class).get(0);

    // when
    UserTrade trade = CryptoComStreamingAdapters.adaptUserTrade(update);

    // then
    assertThat(trade.getId()).isEqualTo("38246881");
    assertThat(trade.getOrderId()).isEqualTo("18342311");
    assertThat(trade.getInstrument().toString()).isEqualTo("BTC/USDT");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);
    assertThat(trade.getPrice()).isEqualByComparingTo(new BigDecimal("50000.0"));
    assertThat(trade.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("0.005000"));
    assertThat(trade.getFeeAmount()).isEqualByComparingTo(new BigDecimal("0.000005"));
    assertThat(trade.getFeeCurrency().getCurrencyCode()).isEqualTo("BTC");
  }
}
