/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.coinmate.dto.marketdata.OrderBookJSONTest;

/** @author Martin Stachon */
public class BalanceJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        OrderBookJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/account/example-balance.json");

    ObjectMapper mapper = new ObjectMapper();
    CoinmateBalance coinmateBalance = mapper.readValue(is, CoinmateBalance.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(coinmateBalance.getData().get("EUR").getCurrency()).isEqualTo("EUR");
    assertThat(coinmateBalance.getData().get("EUR").getBalance())
        .isEqualTo(new BigDecimal("20925.48295"));
    assertThat(coinmateBalance.getData().get("EUR").getReserved())
        .isEqualTo(new BigDecimal("9.009"));
    assertThat(coinmateBalance.getData().get("EUR").getAvailable())
        .isEqualTo(new BigDecimal("20916.47395"));

    assertThat(coinmateBalance.getData().get("BTC").getCurrency()).isEqualTo("BTC");
    assertThat(coinmateBalance.getData().get("BTC").getBalance())
        .isEqualTo(new BigDecimal("9934.56163999"));
    assertThat(coinmateBalance.getData().get("BTC").getReserved())
        .isEqualTo(new BigDecimal("8.008"));
    assertThat(coinmateBalance.getData().get("BTC").getAvailable())
        .isEqualTo(new BigDecimal("9926.55363999"));
  }
}
