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
package com.xeiam.xchange.cryptotrade.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptotrade.CryptoTradeAdapters;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

public class CryptoTradeAdapterTests {

    @Test
    public void testAdaptTicker() throws IOException {

      // Read in the JSON from the example resources
      InputStream is = CryptoTradeAdapterTests.class.getResourceAsStream("/marketdata/example-ticker-data.json");

      // Use Jackson to parse it
      ObjectMapper mapper = new ObjectMapper();
      CryptoTradeTicker cryptoTradeTicker = mapper.readValue(is, CryptoTradeTicker.class);

      Ticker adaptedTicker = CryptoTradeAdapters.adaptTicker(Currencies.BTC, Currencies.USD, cryptoTradeTicker);
      
      assertThat(adaptedTicker.getLast()).isEqualTo(MoneyUtils.parseMoney(Currencies.USD, new BigDecimal("128")));
      assertThat(adaptedTicker.getLow()).isEqualTo(MoneyUtils.parseMoney(Currencies.USD, new BigDecimal("127.9999")));
      assertThat(adaptedTicker.getHigh()).isEqualTo(MoneyUtils.parseMoney(Currencies.USD, new BigDecimal("129.1")));
      assertThat(adaptedTicker.getVolume()).isEqualTo("693.8199");
      assertThat(adaptedTicker.getAsk()).isEqualTo(MoneyUtils.parseMoney(Currencies.USD, new BigDecimal("129.1")));
      assertThat(adaptedTicker.getBid()).isEqualTo(MoneyUtils.parseMoney(Currencies.USD, new BigDecimal("128")));
      assertThat(adaptedTicker.getTradableIdentifier()).isEqualTo(Currencies.BTC);
      assertThat(adaptedTicker.getTimestamp()).isNull();
    }
    
    @Test
    public void testAdaptDepth() throws IOException {

      // Read in the JSON from the example resources
      InputStream is = CryptoTradeAdapterTests.class.getResourceAsStream("/marketdata/example-depth-data.json");

      // Use Jackson to parse it
      ObjectMapper mapper = new ObjectMapper();
      CryptoTradeDepth depth = mapper.readValue(is, CryptoTradeDepth.class);
      
      List<LimitOrder> asks = CryptoTradeAdapters.adaptOrders(depth.getAsks(), Currencies.BTC, Currencies.USD, OrderType.ASK);
      assertThat(asks.size()).isEqualTo(3);
      
      LimitOrder ask = asks.get(0);
      assertThat(ask.getLimitPrice()).isEqualTo(MoneyUtils.parseMoney(Currencies.USD, new BigDecimal("102")));
      assertThat(ask.getTradableAmount()).isEqualTo("0.81718312");
      
      List<LimitOrder> bids = CryptoTradeAdapters.adaptOrders(depth.getBids(), Currencies.BTC, Currencies.USD, OrderType.BID);
      assertThat(bids.size()).isEqualTo(3);
      
      LimitOrder bid = bids.get(0);
      assertThat(bid.getLimitPrice()).isEqualTo(MoneyUtils.parseMoney(Currencies.USD, new BigDecimal("99.03")));
      assertThat(bid.getTradableAmount()).isEqualTo("4");
    }
    
    @Test
    public void testAdaptAccountInfo() throws IOException {
      
      // Read in the JSON from the example resources
      InputStream is = CryptoTradeAdapterTests.class.getResourceAsStream("/account/example-account-info-data.json");

      // Use Jackson to parse it
      ObjectMapper mapper = new ObjectMapper();
      CryptoTradeAccountInfo accountInfo = mapper.readValue(is, CryptoTradeAccountInfo.class);
      
      AccountInfo adaptedAccountInfo = CryptoTradeAdapters.adaptAccountInfo("test", accountInfo);
      
      assertThat(adaptedAccountInfo.getUsername()).isEqualTo("test");
      
      List<Wallet> wallets = adaptedAccountInfo.getWallets();
      assertThat(wallets.size()).isEqualTo(11);
      for (Wallet wallet : wallets) {
        if (wallet.getCurrency().equals(Currencies.BTC)) 
          assertThat(wallet.getBalance()).isEqualTo(MoneyUtils.parseMoney(Currencies.BTC, new BigDecimal("12098.91081965")));
      }

    }
}
