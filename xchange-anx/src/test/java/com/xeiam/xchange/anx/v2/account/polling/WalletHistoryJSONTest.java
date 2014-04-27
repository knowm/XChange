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

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWalletHistoryWrapper;

//import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Test ANXWalletHistory JSON parsing
 */
public class WalletHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = WalletHistoryJSONTest.class.getResourceAsStream("/v2/account/example-wallethistory-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ANXWalletHistoryWrapper anxWalletHistoryWrapper = mapper.readValue(is, ANXWalletHistoryWrapper.class);

    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory() != null);
    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory().getRecords() == 104);
    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory().getCurrentPage() == 1);
    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory().getMaxPage() == 3);
    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory().getMaxResults() == 50);
    Assert.assertTrue(anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries().length == 50);

    Assert.assertEquals("BTC bought: [tid:264f7b7f-f70c-4fc6-ba27-2bcf33b6cd51] 10.00000000 BTC at 280.65500 HKD", anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0]
        .getInfo());

    Assert.assertEquals(104, anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getIndex());

    Assert.assertEquals("1394594770000", anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getDate());

    Assert.assertEquals("fee", anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getType());

    Assert.assertEquals("BTC", anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getValue().getCurrency());

    // Assert.assertEquals(new BigDecimal(0),
    // anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getValue().getValue());

    Assert.assertEquals(new BigDecimal("103168.75400000"), anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getBalance().getValue());

    Assert.assertEquals("cc496636-4849-4acf-a390-e4091a5009c3", anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getTrade().getOid());

    Assert.assertEquals("264f7b7f-f70c-4fc6-ba27-2bcf33b6cd51", anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getTrade().getTid());

    Assert.assertEquals(new BigDecimal("10.00000000"), anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getTrade().getAmount().getValue());

    Assert.assertEquals("market", anxWalletHistoryWrapper.getANXWalletHistory().getANXWalletHistoryEntries()[0].getTrade().getProperties());

  }
}