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
package com.xeiam.xchange.justcoin.service.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinTrade;

/**
 * @author jamespedwards42
 */
public class JustcoinTradesTest {

  private String id;
  private String tradableIdentifier;
  private String transactionCurrency;
  private BigDecimal averagePrice;
  private BigDecimal amount;
  private Date orderCreatedAt;
  private JustcoinTrade justcoinTrade;

  @Before
  public void before() {

    // initialize expected values
    id = "1591866";
    tradableIdentifier = Currencies.BTC;
    transactionCurrency = Currencies.LTC;
    averagePrice = BigDecimal.valueOf(31.400);
    amount = BigDecimal.valueOf(0.50024);
    try {
      orderCreatedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse("2014-01-07T06:44:14.576Z");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    justcoinTrade =
        new JustcoinTrade(id, JustcoinUtils.getApiMarket(tradableIdentifier, transactionCurrency), "bid", null, amount, BigDecimal.ZERO, BigDecimal.valueOf(0.50024), BigDecimal.ZERO, orderCreatedAt,
            averagePrice);
  }

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinOrdersTest.class.getResourceAsStream("/trade/example-order-history-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final JustcoinTrade[] justcoinTrades = mapper.readValue(is, new JustcoinTrade[0].getClass());
    final JustcoinTrade deserializedTrade = justcoinTrades[0];
    assertThat(justcoinTrades.length).isEqualTo(3);
    assertThat(deserializedTrade).isEqualTo(justcoinTrade);
  }

  @Test
  public void testAdapter() {

    final Trade trade = JustcoinAdapters.adaptTrade(justcoinTrade);

    assertThat(trade.getId()).isEqualTo(id);
    assertThat(trade.getPrice()).isEqualTo(averagePrice);
    assertThat(trade.getTimestamp()).isEqualTo(orderCreatedAt);
    assertThat(trade.getTradableAmount()).isEqualTo(amount);
    assertThat(trade.getCurrencyPair().baseSymbol).isEqualTo(tradableIdentifier);
    assertThat(trade.getCurrencyPair().counterSymbol).isEqualTo(transactionCurrency);
    assertThat(trade.getType()).isEqualTo(OrderType.BID);
  }
}
