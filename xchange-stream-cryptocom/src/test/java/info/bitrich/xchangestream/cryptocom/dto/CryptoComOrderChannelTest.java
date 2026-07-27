package info.bitrich.xchangestream.cryptocom.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.cryptocom.CryptoComStreamingService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.CryptoComAdapters;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrder;

/**
 * The {@code user.order} WebSocket channel pushes the same shape as the REST {@code
 * private/get-order-detail} result, so the streaming module reuses {@link CryptoComOrder} and
 * {@link CryptoComAdapters#adaptOrder(CryptoComOrder)} directly instead of a separate WS-only DTO.
 * Adapter behavior itself is covered by {@code CryptoComAdaptersTest} in the REST module.
 */
public class CryptoComOrderChannelTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final CryptoComStreamingService service =
      new CryptoComStreamingService("wss://stream.crypto.com/exchange/v1/market");

  @Test
  public void testUnmarshalOrderUpdate() throws IOException {
    // given
    String resource = "/info/bitrich/xchangestream/cryptocom/dto/user-order-update.json";

    // when
    JsonNode envelope =
        CryptoComStreamingTestSupport.readEnvelope(
            CryptoComOrderChannelTest.class, resource, objectMapper);
    List<CryptoComOrder> updates = service.extractData(envelope, CryptoComOrder.class);

    // then
    assertThat(updates).hasSize(1);
    CryptoComOrder update = updates.get(0);
    assertThat(update.getOrderId()).isEqualTo("18342311");
    assertThat(update.getClientOid()).isEqualTo("my-order-0001");
    assertThat(update.getInstrumentName()).isEqualTo("BTC_USDT");
    assertThat(update.getSide()).isEqualTo("BUY");
    assertThat(update.getStatus()).isEqualTo("ACTIVE");
    assertThat(new BigDecimal(update.getLimitPrice()))
        .isEqualByComparingTo(new BigDecimal("50000.0"));
    assertThat(new BigDecimal(update.getQuantity()))
        .isEqualByComparingTo(new BigDecimal("0.015000"));
    assertThat(new BigDecimal(update.getCumulativeQuantity()))
        .isEqualByComparingTo(new BigDecimal("0.005000"));
    assertThat(update.getUpdateTime()).isEqualTo(1785085695512L);
  }
}
