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
import org.knowm.xchange.cryptocom.dto.account.CryptoComBalance;
import org.knowm.xchange.dto.account.Balance;

/**
 * The {@code user.balance} WebSocket channel pushes the same shape as the REST {@code
 * private/user-balance} result, so the streaming module reuses {@link CryptoComBalance} directly
 * instead of a separate WS-only DTO.
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

  @Test
  public void testAdaptBalance() throws IOException {
    // given
    String resource = "/info/bitrich/xchangestream/cryptocom/dto/user-balance-update.json";
    JsonNode envelope =
        CryptoComStreamingTestSupport.readEnvelope(
            CryptoComBalanceChannelTest.class, resource, objectMapper);
    CryptoComBalance.PositionBalance position =
        service.extractData(envelope, CryptoComBalance.class).get(0).getPositionBalances().get(1);

    // when
    Balance balance = CryptoComStreamingAdapters.adaptBalance(position);

    // then
    assertThat(balance.getCurrency().getCurrencyCode()).isEqualTo("BTC");
    assertThat(balance.getTotal()).isEqualByComparingTo(new BigDecimal("0.01500000"));
  }
}
