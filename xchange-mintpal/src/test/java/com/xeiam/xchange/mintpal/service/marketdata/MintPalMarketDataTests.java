package com.xeiam.xchange.mintpal.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.mintpal.dto.MintPalBaseResponse;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrder;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;

/**
 * @author jamespedwards42
 */
public class MintPalMarketDataTests {

  @Test
  public void testUnmarshallAllTickers() throws JsonParseException, JsonMappingException, IOException {

    final InputStream is = MintPalMarketDataTests.class.getResourceAsStream("/marketdata/tickers.json");

    final ObjectMapper mapper = new ObjectMapper();
    final JavaType tickersType = mapper.getTypeFactory().constructParametricType(MintPalBaseResponse.class, mapper.getTypeFactory().constructCollectionType(List.class, MintPalTicker.class));
    final MintPalBaseResponse<List<MintPalTicker>> tickers = mapper.readValue(is, tickersType);

    assertThat(tickers.getStatus()).isEqualTo("success");
    final List<MintPalTicker> tickerList = tickers.getData();
    assertThat(tickerList).hasSize(1);
    final MintPalTicker ticker = tickerList.get(0);
    assertThat(ticker.getMarketId()).isEqualTo(88);
    assertThat(ticker.getCoin()).isEqualTo("365Coin");
    assertThat(ticker.getCode()).isEqualTo("365");
    assertThat(ticker.getExchange()).isEqualTo("BTC");
    assertThat(ticker.getLastPrice()).isEqualTo("0.20000000");
    assertThat(ticker.getYesterdayPrice()).isEqualTo("0.20000018");
    assertThat(ticker.getChange()).isEqualTo("-0.00");
    assertThat(ticker.getHigh24Hour()).isEqualTo("0.26500000");
    assertThat(ticker.getLow24Hour()).isEqualTo("0.20000000");
    assertThat(ticker.getVolume24Hour()).isEqualTo("0.453");
    assertThat(ticker.getTopBid()).isEqualTo("0.20000000");
    assertThat(ticker.getTopAsk()).isEqualTo("0.22900000");
  }

  @Test
  public void testUnmarshallOrders() throws JsonParseException, JsonMappingException, IOException {

    final InputStream is = MintPalMarketDataTests.class.getResourceAsStream("/marketdata/orders.json");

    final ObjectMapper mapper = new ObjectMapper();
    final JavaType tickersType = mapper.getTypeFactory().constructParametricType(MintPalBaseResponse.class, mapper.getTypeFactory().constructCollectionType(List.class, MintPalPublicOrders.class));
    final MintPalBaseResponse<List<MintPalPublicOrders>> ordersResponse = mapper.readValue(is, tickersType);

    assertThat(ordersResponse.getStatus()).isEqualTo("success");
    final List<MintPalPublicOrders> orderbook = ordersResponse.getData();

    assertThat(orderbook).hasSize(2);
    final MintPalPublicOrders asksWrapper = orderbook.get(0).getType().equals("buy") ? orderbook.get(1) : orderbook.get(0);
    final List<MintPalPublicOrder> asks = asksWrapper.getOrders();
    assertThat(asksWrapper.getCount()).isEqualTo(asks.size());

    final MintPalPublicOrder ask = asks.get(0);
    assertThat(ask.getPrice()).isEqualTo("0.01289999");
    assertThat(ask.getAmount()).isEqualTo("0.04599935");
    assertThat(ask.getTotal()).isEqualTo("0.00059339");
  }

  @Test
  public void testUnmarshallPublicTrades() throws JsonParseException, JsonMappingException, IOException {

    final InputStream is = MintPalMarketDataTests.class.getResourceAsStream("/marketdata/trades.json");

    final ObjectMapper mapper = new ObjectMapper();
    final JavaType tickersType = mapper.getTypeFactory().constructParametricType(MintPalBaseResponse.class, mapper.getTypeFactory().constructCollectionType(List.class, MintPalPublicTrade.class));
    final MintPalBaseResponse<List<MintPalPublicTrade>> tradesResponse = mapper.readValue(is, tickersType);

    final List<MintPalPublicTrade> trades = tradesResponse.getData();
    assertThat(trades).hasSize(2);

    final MintPalPublicTrade trade = trades.get(0);
    assertThat(trade.getTime().getTime()).isEqualTo(1405056569000L);
    assertThat(trade.getType()).isEqualTo("SELL");
    assertThat(trade.getPrice()).isEqualTo("0.00000004");
    assertThat(trade.getAmount()).isEqualTo("2299494.19282106");
    assertThat(trade.getTotal()).isEqualTo("0.09197970");
  }

}
