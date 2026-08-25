package info.bitrich.xchangestream.cryptocom.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.cryptocom.CryptoComStreamingService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.dto.account.CryptoComBalance;

/**
 * The {@code user.balance} WebSocket channel pushes the same shape as the REST {@code
 * private/user-balance} result, so the streaming module reuses {@link CryptoComBalance} directly
 * instead of a separate WS-only DTO. Adapter behavior itself is covered by {@code
 * CryptoComStreamingAdaptersTest}.
 */
public class CryptoComBalanceChannelTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final CryptoComStreamingService service =
      new CryptoComStreamingService("wss://stream.crypto.com/exchange/v1/market");

  @Test
  public void testUnmarshalBalanceUpdate() throws IOException {
    // given
    String resource = "/info/bitrich/xchangestream/cryptocom/dto/user-balance-update.json";

    // when
    JsonNode envelope =
        CryptoComStreamingTestSupport.readEnvelope(
            CryptoComBalanceChannelTest.class, resource, objectMapper);
    List<CryptoComBalance> updates = service.extractData(envelope, CryptoComBalance.class);

    // then
    assertThat(updates).hasSize(1);
    List<CryptoComBalance.PositionBalance> positions = updates.get(0).getPositionBalances();
    assertThat(positions).hasSize(2);

    CryptoComBalance.PositionBalance usdt = positions.get(0);
    assertThat(usdt.getInstrumentName()).isEqualTo("USDT");
    assertThat(new BigDecimal(usdt.getQuantity()))
        .isEqualByComparingTo(new BigDecimal("5000.12345678"));

    CryptoComBalance.PositionBalance btc = positions.get(1);
    assertThat(btc.getInstrumentName()).isEqualTo("BTC");
    assertThat(new BigDecimal(btc.getQuantity()))
        .isEqualByComparingTo(new BigDecimal("0.01500000"));
  }
}
