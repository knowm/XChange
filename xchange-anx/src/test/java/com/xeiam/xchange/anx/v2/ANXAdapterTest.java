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
package com.xeiam.xchange.anx.v2;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfo;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWallet;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXDepth;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXDepthUpdate;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTicker;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTrade;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrder;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBookUpdate;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the ANXAdapter class
 */
public class ANXAdapterTest {

    @Test
    public void testAccountInfoAdapter() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = ANXAdapterTest.class.getResourceAsStream("/v2/account/example-accountinfo-data.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ANXAccountInfo anxAccountInfo = mapper.readValue(is, ANXAccountInfo.class);

        AccountInfo accountInfo = ANXAdapters.adaptAccountInfo(anxAccountInfo);
        assertThat(accountInfo.getUsername()).isEqualTo("test@anxpro.com");
    }

    @Test
    public void testOrderAdapterWithOpenOrders() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = ANXAdapterTest.class.getResourceAsStream("/v2/trade/example-openorders-data.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ANXOpenOrder[] anxOpenOrders = mapper.readValue(is, ANXOpenOrder[].class);

        List<LimitOrder> openorders = ANXAdapters.adaptOrders(anxOpenOrders);
//        assertThat(openorders.size()).isEqualTo(38);
        Assert.assertEquals(2, openorders.size());

        // verify all fields filled
        System.out.println(openorders.get(0).getLimitPrice().toString());
        Assert.assertEquals(new BigDecimal("412.34567"), openorders.get(0).getLimitPrice());
        Assert.assertEquals(OrderType.ASK, openorders.get(0).getType());
        Assert.assertEquals(new BigDecimal("412.34567"), openorders.get(0).getLimitPrice());
        Assert.assertEquals(new BigDecimal("10.00000000"), openorders.get(0).getTradableAmount());

        Assert.assertEquals("BTC", openorders.get(0).getCurrencyPair().baseCurrency);
        Assert.assertEquals("HKD", openorders.get(0).getCurrencyPair().counterCurrency);

        Assert.assertEquals(new Date(1393411075000L), openorders.get(0).getTimestamp());
    }

    @Test
    public void testOrderAdapterWithDepth() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = ANXAdapterTest.class.getResourceAsStream("/v2/marketdata/example-fulldepth-data.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ANXDepth anxDepth = mapper.readValue(is, ANXDepth.class);

        List<LimitOrder> asks = ANXAdapters.adaptOrders(anxDepth.getAsks(), "BTC", "USD", "ask", "id_567");
        Assert.assertEquals(3, asks.size());

        // Verify all fields filled
        assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);


        Assert.assertEquals(new BigDecimal("16.00000000"), asks.get(0).getTradableAmount());
        Assert.assertEquals(new BigDecimal("3260.40000"), asks.get(0).getLimitPrice());

        Assert.assertEquals("BTC", asks.get(0).getCurrencyPair().baseCurrency);
        Assert.assertEquals("USD", asks.get(0).getCurrencyPair().counterCurrency);
    }

    /*
    @Test
    public void testTradeAdapter() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = ANXAdapterTest.class.getResourceAsStream("/v2/marketdata/polling/example-trades-data.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ANXTrade[] anxTrades = mapper.readValue(is, ANXTrade[].class);

        Trades trades = ANXAdapters.adaptTrades(anxTrades);
        assertThat(trades.getTrades().size()).isEqualTo(30);

        // Verify all fields filled
        assertThat(trades.getTrades().get(0).getPrice().getAmount().doubleValue()).isEqualTo(193.99989);
        assertThat(trades.getTrades().get(0).getPrice().getAmount().floatValue()).isEqualTo(193.99989f);
        assertThat(trades.getTrades().get(0).getType()).isEqualTo(OrderType.BID);
        assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue()).isEqualTo(0.01985186);
        assertThat(trades.getTrades().get(0).getTradableIdentifier()).isEqualTo("BTC");
        assertThat(trades.getTrades().get(0).getTransactionCurrency()).isEqualTo("USD");
        assertThat(trades.getTrades().get(0).getId()).isEqualTo(1365499103363494L);
        // Unix 1334177326 = Wed, 11 Apr 2012 20:48:46 GMT
        assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2013-04-09 09:18:23 GMT");
        assertThat(trades.getTrades().get(0).getTimestamp().getTime()).isEqualTo(1365499103363L);
    }

    */

    @Test
    public void testWalletAdapter() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = ANXAdapterTest.class.getResourceAsStream("/v2/account/example-accountinfo-data.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ANXAccountInfo anxAccountInfo = mapper.readValue(is, ANXAccountInfo.class);


        // in Wallet(s), only wallets from ANXAccountInfo.getWallets that contained data are NOT null.
        List<Wallet> wallets = ANXAdapters.adaptWallets(anxAccountInfo.getWallets());
        Assert.assertEquals(22, wallets.size());

        Assert.assertTrue(wallets.contains(new Wallet(Currencies.CAD, new BigDecimal("100000.00000"))));
        Assert.assertTrue(wallets.contains(new Wallet(Currencies.BTC, new BigDecimal("100000.01988000"))));
        Assert.assertTrue(wallets.contains(new Wallet(Currencies.DOGE, new BigDecimal("9999781.09457936"))));
    }

    @Test
    public void testTickerAdapter() throws IOException {

        // Read in the JSON from the example resources
        InputStream is = ANXAdapterTest.class.getResourceAsStream("/v2/marketdata/example-ticker-data.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ANXTicker anxTicker = mapper.readValue(is, ANXTicker.class);

        Ticker ticker = ANXAdapters.adaptTicker(anxTicker);

        Assert.assertEquals(new BigDecimal("725.38123"), ticker.getLast());
        Assert.assertEquals(new BigDecimal("725.38123"), ticker.getHigh());
        Assert.assertEquals(new BigDecimal("380.00000"), ticker.getLow());
        Assert.assertEquals(new BigDecimal("7.00000000"), ticker.getVolume());
        Assert.assertEquals(new BigDecimal("725.38123"), ticker.getLast());
        Assert.assertEquals(new BigDecimal("38.85148"), ticker.getBid());
        Assert.assertEquals(new BigDecimal("897.25596"), ticker.getAsk());


        Assert.assertEquals(new Date(1393388594814000L), ticker.getTimestamp());
    }
}
