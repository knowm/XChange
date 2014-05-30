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
 * THE SOFTWARE IS PROVIdED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.cryptsy.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptsy.CryptsyUtils;
import com.xeiam.xchange.cryptsy.dto.CryptsyOrder;
import com.xeiam.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;

public class CryptsyMarketDataJsonTests {

  @Test
  public void testDeserializeOrderBook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyMarketDataJsonTests.class.getResourceAsStream("/marketdata/Sample_MarketOrders_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyOrderBook cryptsyOrderBook = mapper.readValue(is, CryptsyOrderBookReturn.class).getReturnValue();

    assertEquals(cryptsyOrderBook.buyOrders().size(), 100);
    assertEquals(cryptsyOrderBook.buyOrders().get(0).getBuyPrice(), new BigDecimal("0.00006065"));
    assertEquals(cryptsyOrderBook.buyOrders().get(0).getQuantity(), new BigDecimal("10000.00000000"));
    assertEquals(cryptsyOrderBook.buyOrders().get(0).getTotal(), new BigDecimal("0.60650000"));

    assertEquals(cryptsyOrderBook.sellOrders().size(), 100);
    assertEquals(cryptsyOrderBook.sellOrders().get(0).getSellPrice(), new BigDecimal("0.00006089"));
    assertEquals(cryptsyOrderBook.sellOrders().get(0).getQuantity(), new BigDecimal("216.05575836"));
    assertEquals(cryptsyOrderBook.sellOrders().get(0).getTotal(), new BigDecimal("0.01315564"));
  }

  @Test
  public void testDeserializeTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyMarketDataJsonTests.class.getResourceAsStream("/marketdata/Sample_MarketTrades_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CryptsyOrder> cryptsyTrades = mapper.readValue(is, CryptsyMarketTradesReturn.class).getReturnValue();

    CryptsyOrder trade = cryptsyTrades.get(0);

    assertEquals(trade.getPrice(), new BigDecimal("0.00006103"));
    assertEquals(trade.getQuantity(), new BigDecimal("124.81824536"));
    assertEquals(trade.getTotal(), new BigDecimal("0.00761766"));
    assertEquals(trade.getTradeId(), 45360522);
    assertEquals(trade.getType(), CryptsyOrderType.Sell);

    trade = cryptsyTrades.get(999);

    assertEquals(trade.getPrice(), new BigDecimal("0.00006180"));
    assertEquals(trade.getQuantity(), new BigDecimal("2.82427886"));
    assertEquals(trade.getTotal(), new BigDecimal("0.00017454"));
    assertEquals(trade.getTradeId(), 45233881);
    assertEquals(trade.getType(), CryptsyOrderType.Buy);
  }

  @Test
  public void testDeserializeMarkets() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyMarketDataJsonTests.class.getResourceAsStream("/marketdata/Sample_GetMarket_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CryptsyMarketData> cryptsyMarketData = mapper.readValue(is, CryptsyGetMarketsReturn.class).getReturnValue();

    CryptsyMarketData marketData = cryptsyMarketData.get(0);

    assertEquals(marketData.get24hBTCVolume(), new BigDecimal("2.98366934"));
    assertEquals(marketData.get24hUSDVolume(), new BigDecimal("1332.74541977"));
    assertEquals(marketData.get24hVolume(), new BigDecimal("0.13619144"));
    assertEquals(marketData.getHigh(), new BigDecimal("23.53895040"));
    assertEquals(marketData.getLast(), new BigDecimal("21.90790653"));
    assertEquals(marketData.getLow(), new BigDecimal("21.30000000"));
    assertEquals(marketData.getMarketId(), 141);
    assertEquals(marketData.getLabel(), "42/BTC");
    assertEquals(marketData.getPrimaryCurrencyCode(), "42");
    assertEquals(marketData.getSecondaryCurrencyCode(), "BTC");
  }

  @Test
  public void testDeserializeMarketsPublic() throws IOException, ParseException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyMarketDataJsonTests.class.getResourceAsStream("/marketdata/Sample_MarketData_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<String, CryptsyPublicMarketData> cryptsyMarketData = mapper.readValue(is, CryptsyPublicMarketDataReturn.class).getReturnValue();

    CryptsyPublicMarketData marketData = cryptsyMarketData.get("DOGE");

    assertEquals(marketData.getVolume(), new BigDecimal("13154836.13271418"));
    assertEquals(marketData.getLastTradePrice(), new BigDecimal("0.00003485"));
    assertThat(marketData.getLastTradeTime()).isEqualTo(CryptsyUtils.convertDateTime("2014-05-29 21:45:41"));
    assertEquals(marketData.getMarketId(), 135);
    assertEquals(marketData.getLabel(), "DOGE/LTC");
    assertEquals(marketData.getPrimaryCurrencyCode(), "DOGE");
    assertEquals(marketData.getSecondaryCurrencyCode(), "LTC");

    List<CryptsyPublicTrade> cryptsyTrades = marketData.getRecentTrades();
    assertThat(cryptsyTrades).hasSize(2);

    CryptsyPublicTrade trade = cryptsyTrades.get(0);

    assertEquals(trade.getPrice(), new BigDecimal("0.00003495"));
    assertEquals(trade.getQuantity(), new BigDecimal("2961.55892792"));
    assertEquals(trade.getTotal(), new BigDecimal("0.10350648"));
    assertEquals(trade.getTradeId(), 47692497);
    assertThat(trade.getTime()).isEqualTo(CryptsyUtils.convertDateTime("2014-05-29 21:49:34"));

    List<CryptsyPublicOrder> buyOrders = marketData.getBuyOrders();

    assertThat(buyOrders).hasSize(2);

    CryptsyPublicOrder order = buyOrders.get(0);
    assertEquals(order.getPrice(), new BigDecimal("0.00003485"));
    assertEquals(order.getQuantity(), new BigDecimal("47246.26655413"));
    assertEquals(order.getTotal(), new BigDecimal("1.64653239"));
  }

  @Test
  public void testDeserializeOrderBookPublic() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyMarketDataJsonTests.class.getResourceAsStream("/marketdata/Sample_Orderbook_Public_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<String, CryptsyPublicOrderbook> cryptsyOrderBookMap = mapper.readValue(is, CryptsyPublicOrderbookReturn.class).getReturnValue();

    CryptsyPublicOrderbook cryptsyOrderBook = cryptsyOrderBookMap.get("DOGE");

    assertEquals(cryptsyOrderBook.getMarketId(), 135);
    assertEquals(cryptsyOrderBook.getLabel(), "DOGE/LTC");
    assertEquals(cryptsyOrderBook.getPriCurrCode(), "DOGE");
    assertEquals(cryptsyOrderBook.getSecCurrCode(), "LTC");

    List<CryptsyPublicOrder> sellOrders = cryptsyOrderBook.getSellOrders();
    assertThat(sellOrders).hasSize(3);

    CryptsyPublicOrder order = sellOrders.get(0);
    assertEquals(order.getPrice(), new BigDecimal("0.00003495"));
    assertEquals(order.getQuantity(), new BigDecimal("334369.75217020"));
    assertEquals(order.getTotal(), new BigDecimal("11.68622284"));

  }
}
