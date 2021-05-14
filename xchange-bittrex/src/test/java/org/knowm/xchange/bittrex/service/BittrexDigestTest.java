package org.knowm.xchange.bittrex.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BittrexDigestTest {

  @Test
  public void urlDecodeEncodedQueryParam() {

    String unencoded =
        "https://api.bittrex.com/v3/orders/closed?marketSymbol=BTC-USD&pageSize=100&startDate=2020-11-04T09:09:21Z";
    String encoded =
        "https://api.bittrex.com/v3/orders/closed?marketSymbol=BTC-USD&pageSize=100&startDate=2020-11-04T09%3A09%3A21Z";

//    assertThat(BittrexDigest.urlDecode(encoded)).isEqualTo(unencoded);
  }
}
