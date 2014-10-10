package com.xeiam.xchange.mintpal.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mintpal.MintPalAdapters;
import com.xeiam.xchange.mintpal.dto.MintPalBaseResponse;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;
import com.xeiam.xchange.mintpal.service.marketdata.MintPalMarketDataTest;

/**
 * @author jamespedwards42
 */
public class MintPalAdapterTest {

  @Test
  public void testAdaptTicker() throws JsonParseException, JsonMappingException, IOException {

    final InputStream is = MintPalMarketDataTest.class.getResourceAsStream("/marketdata/tickers.json");

    final ObjectMapper mapper = new ObjectMapper();
    final JavaType tickersType = mapper.getTypeFactory().constructParametricType(MintPalBaseResponse.class, mapper.getTypeFactory().constructCollectionType(List.class, MintPalTicker.class));
    final MintPalBaseResponse<List<MintPalTicker>> tickers = mapper.readValue(is, tickersType);

    final List<MintPalTicker> tickerList = tickers.getData();
    final MintPalTicker ticker = tickerList.get(0);
    final Ticker adaptedTicker = MintPalAdapters.adaptTicker(ticker);

    assertThat(adaptedTicker.getCurrencyPair()).isEqualTo(new CurrencyPair("365", "BTC"));
    assertThat(adaptedTicker.getLast()).isEqualTo("0.20000000");
    assertThat(adaptedTicker.getHigh()).isEqualTo("0.26500000");
    assertThat(adaptedTicker.getLow()).isEqualTo("0.20000000");
    assertThat(adaptedTicker.getVolume()).isEqualTo("2.265");
    assertThat(adaptedTicker.getBid()).isEqualTo("0.20000000");
    assertThat(adaptedTicker.getAsk()).isEqualTo("0.22900000");
  }

  @Test
  public void testAdaptOrders() throws JsonParseException, JsonMappingException, IOException {

    final InputStream is = MintPalAdapterTest.class.getResourceAsStream("/marketdata/orders.json");

    final ObjectMapper mapper = new ObjectMapper();
    final JavaType tickersType = mapper.getTypeFactory().constructParametricType(MintPalBaseResponse.class, mapper.getTypeFactory().constructCollectionType(List.class, MintPalPublicOrders.class));
    final MintPalBaseResponse<List<MintPalPublicOrders>> ordersResponse = mapper.readValue(is, tickersType);

    final List<MintPalPublicOrders> orderbook = ordersResponse.getData();
    final OrderBook adaptedOrderbook = MintPalAdapters.adaptOrderBook(CurrencyPair.LTC_BTC, orderbook);

    final List<LimitOrder> asks = adaptedOrderbook.getAsks();
    assertThat(asks).hasSize(2);

    final LimitOrder ask = asks.get(0);
    assertThat(ask.getLimitPrice()).isEqualTo("0.01289999");
    assertThat(ask.getTradableAmount()).isEqualTo("0.04599935");
    assertThat(ask.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
  }

  @Test
  public void testAdaptPublicTrades() throws JsonParseException, JsonMappingException, IOException {

    final InputStream is = MintPalAdapterTest.class.getResourceAsStream("/marketdata/trades.json");

    final ObjectMapper mapper = new ObjectMapper();
    final JavaType tickersType = mapper.getTypeFactory().constructParametricType(MintPalBaseResponse.class, mapper.getTypeFactory().constructCollectionType(List.class, MintPalPublicTrade.class));
    final MintPalBaseResponse<List<MintPalPublicTrade>> tradesResponse = mapper.readValue(is, tickersType);

    final List<MintPalPublicTrade> trades = tradesResponse.getData();
    final Trades adaptedTrades = MintPalAdapters.adaptPublicTrades(CurrencyPair.LTC_BTC, trades);
    assertThat(adaptedTrades.getlastID()).isEqualTo(0);
    assertThat(adaptedTrades.getTradeSortType()).isEqualTo(TradeSortType.SortByTimestamp);
    final List<Trade> tradesList = adaptedTrades.getTrades();
    assertThat(tradesList).hasSize(2);

    final Trade trade = tradesList.get(0);
    assertThat(trade.getTimestamp().getTime()).isEqualTo(1405056569000L);
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade.getPrice()).isEqualTo("0.00000004");
    assertThat(trade.getTradableAmount()).isEqualTo("2299494.19282106");
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
  }
}
