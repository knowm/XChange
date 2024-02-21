package info.bitrich.xchangestream.gateio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GateioStreamingAuthHelperTest {

  @Test
  void sign() {
    String time = "1691678147";
    String channel = "spot.balances";
    String event = "subscribe";
    String apiSecret = "a";
    GateioStreamingAuthHelper gateioStreamingAuthHelper = new GateioStreamingAuthHelper(apiSecret);

    String actual = gateioStreamingAuthHelper.sign(channel, event, time);
    String expected = "36481b07c37f27bbfda955c312580e70652d856e7626698f7a5250cf31ffb4052be60f2ec42e6e8816c9fb93d7e26d33f8ef245346b7fd2316ad99dd05ad8176";
    assertThat(actual).isEqualTo(expected);

  }
}