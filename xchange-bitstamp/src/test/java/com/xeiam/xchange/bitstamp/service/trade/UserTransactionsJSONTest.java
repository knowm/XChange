/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitstamp.service.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction.TransactionType;

/**
 * Test UserTransactions[] JSON parsing
 */
public class UserTransactionsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = UserTransactionsJSONTest.class.getResourceAsStream("/trade/example-user-transactions.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampUserTransaction[] transactions = mapper.readValue(is, BitstampUserTransaction[].class);

    assertThat(transactions.length).isEqualTo(3);

    // Verify that the example data was unmarshalled correctly
    assertThat(transactions[0].getUsd()).isEqualTo(new BigDecimal("-11.37"));
    assertThat(transactions[0].getBtc()).isEqualTo(new BigDecimal("0.08650000"));
    assertThat(transactions[0].getPrice()).isEqualTo(new BigDecimal("131.50"));
    assertThat(transactions[0].getId()).isEqualTo(1296712L);
    assertThat(transactions[0].getOrderId()).isEqualTo(6877187L);
    assertThat(transactions[0].getDatetime()).isEqualTo("2013-09-02 13:17:49");
    assertThat(transactions[0].getType()).isEqualTo(TransactionType.trade);
  }
}
