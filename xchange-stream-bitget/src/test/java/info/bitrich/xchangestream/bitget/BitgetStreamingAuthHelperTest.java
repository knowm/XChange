package info.bitrich.xchangestream.bitget;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class BitgetStreamingAuthHelperTest {

  @Test
  void sign() {
    Instant timestamp = Instant.ofEpochSecond(1730301330L);
    String apiSecret = "a";

    String actual = BitgetStreamingAuthHelper.sign(timestamp, apiSecret);
    String expected = "t0RPH0gO4ut6hHfJWZwKgtnL7XnVsa4AUenaIIbWCuc=";
    assertThat(actual).isEqualTo(expected);
  }
}
