package com.xeiam.xchange.cryptotrade.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptotrade.CryptoTradeAdapters;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeMarketDataJsonTest;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrades;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTradeJsonTest;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;

public class CryptoTradeAdapterTest {

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeAdapterTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeTicker cryptoTradeTicker = mapper.readValue(is, CryptoTradeTicker.class);

    Ticker adaptedTicker = CryptoTradeAdapters.adaptTicker(CurrencyPair.BTC_USD, cryptoTradeTicker);

    assertThat(adaptedTicker.getLast()).isEqualTo(new BigDecimal("128"));
    assertThat(adaptedTicker.getLow()).isEqualTo(new BigDecimal("127.9999"));
    assertThat(adaptedTicker.getHigh()).isEqualTo(new BigDecimal("129.1"));
    assertThat(adaptedTicker.getVolume()).isEqualTo("5.4");
    assertThat(adaptedTicker.getAsk()).isEqualTo(new BigDecimal("129.1"));
    assertThat(adaptedTicker.getBid()).isEqualTo(new BigDecimal("128"));
    assertThat(adaptedTicker.getCurrencyPair().baseSymbol).isEqualTo(Currencies.BTC);
    assertThat(adaptedTicker.getTimestamp()).isNull();
  }

  @Test
  public void testAdaptDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeAdapterTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeDepth depth = mapper.readValue(is, CryptoTradeDepth.class);

    OrderBook orderBook = CryptoTradeAdapters.adaptOrderBook(CurrencyPair.BTC_USD, depth);

    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks.size()).isEqualTo(3);

    LimitOrder ask = asks.get(0);
    assertThat(ask.getLimitPrice()).isEqualTo(new BigDecimal("102"));
    assertThat(ask.getTradableAmount()).isEqualTo("0.81718312");

    List<LimitOrder> bids = orderBook.getBids();
    assertThat(bids.size()).isEqualTo(3);

    LimitOrder bid = bids.get(2);
    assertThat(bid.getLimitPrice()).isEqualTo(new BigDecimal("99.03"));
    assertThat(bid.getTradableAmount()).isEqualTo("4");
  }

  @Test
  public void testAdaptAccountInfo() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeAdapterTest.class.getResourceAsStream("/account/example-account-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeAccountInfo accountInfo = mapper.readValue(is, CryptoTradeAccountInfo.class);

    AccountInfo adaptedAccountInfo = CryptoTradeAdapters.adaptAccountInfo("test", accountInfo);

    assertThat(adaptedAccountInfo.getUsername()).isEqualTo("test");

    List<Wallet> wallets = adaptedAccountInfo.getWallets();
    assertThat(wallets.size()).isEqualTo(11);
    for (Wallet wallet : wallets) {
      if (wallet.getCurrency().equals(Currencies.BTC))
        assertThat(wallet.getBalance()).isEqualTo(new BigDecimal("12098.91081965"));
    }
  }

  @Test
  public void testAdaptOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeAdapterTest.class.getResourceAsStream("/trade/example-order-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeOrders orderHistory = mapper.readValue(is, CryptoTradeOrders.class);

    OpenOrders orders = CryptoTradeAdapters.adaptOpenOrders(orderHistory);

    assertThat(orders.getOpenOrders()).hasSize(1);

    LimitOrder order = orders.getOpenOrders().get(0);
    assertThat(order.getId()).isEqualTo("5");
    assertThat(order.getLimitPrice()).isEqualTo("300");
    assertThat(order.getTradableAmount()).isEqualTo("1");
    assertThat(order.getCurrencyPair().baseSymbol).isEqualTo(Currencies.BTC);
    assertThat(order.getCurrencyPair().counterSymbol).isEqualTo(Currencies.USD);
    assertThat(order.getType()).isEqualTo(OrderType.BID);
    assertThat(order.getTimestamp()).isEqualTo(new Date(1370944500));
  }

  @Test
  public void testAdaptTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeTradeJsonTest.class.getResourceAsStream("/trade/example-trade-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeTrades tradeHistory = mapper.readValue(is, CryptoTradeTrades.class);

    Trades trades = CryptoTradeAdapters.adaptTrades(tradeHistory);

    assertThat(trades.getlastID()).isEqualTo(17);
    assertThat(trades.getTrades()).hasSize(2);

    Trade trade = trades.getTrades().get(1);
    assertThat(trade.getPrice()).isEqualTo("128");
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade.getTimestamp()).isEqualTo(new Date(1370965122));
    assertThat(trade.getTradableAmount()).isEqualTo("0.1");
    assertThat(trade.getId()).isEqualTo("17");
    assertThat(trade.getOrderId()).isEqualTo("1");
  }

  @Test
  public void testAdaptPublicTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradePublicTrades publicTradeHistory = mapper.readValue(is, CryptoTradePublicTrades.class);

    Trades trades = CryptoTradeAdapters.adaptPublicTradeHistory(CurrencyPair.BTC_USD, publicTradeHistory.getPublicTrades());

    assertThat(trades.getlastID()).isEqualTo(399394);

    List<Trade> tradeList = trades.getTrades();
    assertThat(tradeList).hasSize(2);

    Trade trade = tradeList.get(0);
    assertThat(trade.getId()).isEqualTo("399328");
    assertThat(trade.getTimestamp().getTime()).isEqualTo(1405856801000L);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade.getTradableAmount()).isEqualTo("0.08540439");
    assertThat(trade.getPrice()).isEqualTo("618.19");
  }
}
