/*
 * The MIT License
 *
 * Copyright 2015-2016 Coinmate.
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
package org.knowm.xchange.coinmate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import org.junit.Test;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTicker;
import org.knowm.xchange.coinmate.dto.trade.CoinmateOrders;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;

/** @author Martin Stachon */
public class CoinmateAdapterTest {

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinmateAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/marketdata/example-ticker.json");

    assertNotNull(is);

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinmateTicker coinmateTicker = mapper.readValue(is, CoinmateTicker.class);

    Ticker ticker = CoinmateAdapters.adaptTicker(coinmateTicker, CurrencyPair.BTC_EUR);

    assertThat(ticker.getLast().toString()).isEqualTo("254.08");
    assertThat(ticker.getBid().toString()).isEqualTo("252.93");
    assertThat(ticker.getAsk().toString()).isEqualTo("254.08");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("42.78294066"));
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(ticker.getTimestamp());
    assertThat(dateString).isEqualTo("2017-01-26 20:12:57");
  }

  @Test
  public void testOrderAdapter_nullPrice() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinmateAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/trade/example-order1.json");

    assertNotNull(is);

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    CoinmateOrders coinmateOrders = mapper.readValue(is, CoinmateOrders.class);

    List<Order> orders = CoinmateAdapters.adaptOrders(coinmateOrders);

    Order order1 = orders.get(0);
    assertThat(order1.getType() == Order.OrderType.ASK);
    assertThat(order1.getId().equals("1"));
    assertThat(order1.getAveragePrice().equals(new BigDecimal("996740")));
    assertThat(order1.getTimestamp().equals(1631188240000L));
    assertNull(order1.getOriginalAmount());
    assertNull(order1.getCumulativeAmount());
    assertThat(order1.getStatus() == Order.OrderStatus.FILLED);
  }

  @Test
  public void testOrderAdapter_notNullPrice() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinmateAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/trade/example-order2.json");

    assertNotNull(is);

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    CoinmateOrders coinmateOrders = mapper.readValue(is, CoinmateOrders.class);

    List<Order> orders = CoinmateAdapters.adaptOrders(coinmateOrders);

    Order order2 = orders.get(0);
    assertThat(order2.getType() == Order.OrderType.ASK);
    assertThat(order2.getId().equals("2"));
    assertThat(order2.getAveragePrice().equals(new BigDecimal("997850")));
    assertThat(order2.getTimestamp().equals(16311882400001L));
    assertThat(order2.getOriginalAmount().compareTo(new BigDecimal("0.0002")) == 0);
    assertThat(order2.getCumulativeAmount().compareTo(new BigDecimal("0.0002")) == 0);
    assertThat(order2.getRemainingAmount().compareTo(BigDecimal.ZERO) == 0);
    assertThat(order2.getStatus() == Order.OrderStatus.FILLED);
  }
}
