/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.anx.v2.account.polling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfo;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Test BitStamp Full Depth JSON parsing
 */
public class AccountInfoJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

      // Read in the JSON from the example resources
      InputStream is = AccountInfoJSONTest.class.getResourceAsStream("/v2/account/example-accountinfo-data.json");

      // Use Jackson to parse it
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      ANXAccountInfo anxAccountInfo = mapper.readValue(is, ANXAccountInfo.class);

      // Verify that the example data was unmarshalled correctly
      assertThat(anxAccountInfo.getLogin()).isEqualTo("test@anxpro.com");

      // Get Balance
      assertThat(anxAccountInfo.getWallets().getBTC().getBalance().getValue()).isEqualTo(new BigDecimal("100000.01988000"));
      assertThat(anxAccountInfo.getWallets().getUSD().getBalance().getValue()).isEqualTo(new BigDecimal("100000.00000"));
      assertThat(anxAccountInfo.getWallets().getHKD().getBalance().getValue()).isEqualTo(new BigDecimal("99863.07000"));

      assertThat(anxAccountInfo.getWallets().getLTC().getBalance().getValue()).isEqualTo(new BigDecimal("100000.00000000"));
      assertThat(anxAccountInfo.getWallets().getDOGE().getBalance().getValue()).isEqualTo(new BigDecimal("9999781.09457936"));


      // Get Other Balance
      assertThat(anxAccountInfo.getWallets().getBTC().getMaxWithdraw().getValue()).isEqualTo(new BigDecimal("100.00000000"));
      assertThat(anxAccountInfo.getWallets().getBTC().getDailyWithdrawLimit().getValue()).isEqualTo(new BigDecimal("100.00000000"));
  }
}
