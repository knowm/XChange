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
package com.xeiam.xchange.justcoin.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinPublicTrade;


public class JustcoinTradesTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinTradesTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, JustcoinPublicTrade.class);
    final List<JustcoinPublicTrade> trades = mapper.readValue(is, collectionType);

    // Verify that the example data was unmarshalled correctly
    assertThat(trades).hasSize(2);
    final JustcoinPublicTrade trade = trades.get(0);
    assertThat(trade.getAmount()).isEqualTo("0.16204");
    assertThat(trade.getPrice()).isEqualTo("600.632");
    assertThat(trade.getTid()).isEqualTo("92595");
    assertThat(trade.getDate()).isEqualTo(1403353443);
  }
  
  @Test
  public void testAdapter() throws JsonParseException, JsonMappingException, IOException {

 // Read in the JSON from the example resources
    final InputStream is = JustcoinTradesTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, JustcoinPublicTrade.class);
    final List<JustcoinPublicTrade> trades = mapper.readValue(is, collectionType);

    final Trades adaptedTrades = JustcoinAdapters.adaptPublicTrades(CurrencyPair.BTC_USD, trades);
    
    assertThat(adaptedTrades.getlastID()).isEqualTo(92726);
    
    final List<Trade> adaptedTradeList = adaptedTrades.getTrades();
    assertThat(adaptedTradeList).hasSize(2);
    
    final Trade adaptedTrade = adaptedTradeList.get(0);
    assertThat(adaptedTrade.getTradableAmount()).isEqualTo("0.16204");
    assertThat(adaptedTrade.getPrice()).isEqualTo("600.632");
    assertThat(adaptedTrade.getId()).isEqualTo("92595");
    assertThat(adaptedTrade.getTimestamp().getTime()).isEqualTo(1403353443000L);
    assertThat(adaptedTrade.getId()).isEqualTo("92595");
    assertThat(adaptedTrade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(adaptedTrade.getOrderId()).isNull();
    assertThat(adaptedTrade.getType()).isNull();
  }
}
