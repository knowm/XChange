/*
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitfloor.service.trade;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitfloor.dto.Product;
import com.xeiam.xchange.bitfloor.dto.trade.BitfloorOrder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test Transaction[] JSON parsing
 */
public class OpenOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = OpenOrdersJSONTest.class.getResourceAsStream("/trade/example-openorders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitfloorOrder[] orders = mapper.readValue(is, BitfloorOrder[].class);

    assertThat("Unexpected order list size", orders.length, is(2));

    // Verify that the example data was unmarshalled correctly
    BitfloorOrder secondOrder = orders[1];
    assertThat("Unexpected Return tid value", secondOrder.getId(), is(equalTo("3d8ae0e0-82cc-4a2e-a16d-9e1dc2c9e028")));
    assertThat("Unexpected Return price value", secondOrder.getPrice(), is(equalTo(new BigDecimal("1111.00000000"))));
    assertThat("Unexpected Return amount value", secondOrder.getSize(), is(equalTo(new BigDecimal("0.10000000"))));
    assertThat("Unexpected Return amount value", secondOrder.getProduct(), is(equalTo(Product.BTCUSD)));
    assertThat("Unexpected Return amount value", secondOrder.getSide(), is(equalTo(BitfloorOrder.Side.sell)));
  }
}
