package com.xeiam.xchange.btcmarkets.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import com.xeiam.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import com.xeiam.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderRequest;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderResponse;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsMyTradingRequest;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsPlaceOrderResponse;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsUserTrade;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class BTCMarketsDtoTest extends BTCMarketsDtoTestSupport {

  @Test
  public void shouldParseBalances() throws IOException {
    final BTCMarketsBalance[] response = parse(BTCMarketsBalance[].class);

    assertThat(response).hasSize(3);
    assertThat(response[2].getCurrency()).isEqualTo("LTC");
    assertThat(response[2].getBalance()).isEqualTo(new BigDecimal("10.00000000"));
    assertThat(response[2].getPendingFunds()).isEqualTo(new BigDecimal("0E-8"));
    assertThat(response[2].toString()).isEqualTo("BTCMarketsBalance{pendingFunds=0E-8, balance=10.00000000, currency='LTC'}");
  }

  @Test
  public void shouldParseNullAvailabilityBalances() throws IOException {
    // when
    final BTCMarketsBalance[] response = parse("NullAvailabilityBalances", BTCMarketsBalance[].class);

    // then
    assertThat(response).hasSize(3);

    assertThat(response[0].getCurrency()).isEqualTo("AUD");
    assertThat(response[0].getBalance()).isNull();
    assertThat(response[0].getPendingFunds()).isEqualTo(new BigDecimal("10.00000000"));
    assertThat(response[0].getAvailable()).isNull();;
    assertThat(response[0].toString()).isEqualTo("BTCMarketsBalance{pendingFunds=10.00000000, balance=null, currency='AUD'}");

    assertThat(response[1].getCurrency()).isEqualTo("BTC");
    assertThat(response[1].getBalance()).isEqualTo(new BigDecimal("10.00000000"));
    assertThat(response[1].getPendingFunds()).isNull();
    assertThat(response[1].getAvailable()).isNull();
    assertThat(response[1].toString()).isEqualTo("BTCMarketsBalance{pendingFunds=null, balance=10.00000000, currency='BTC'}");

    assertThat(response[2].getCurrency()).isEqualTo("LTC");
    assertThat(response[2].getBalance()).isNull();
    assertThat(response[2].getPendingFunds()).isNull();
    assertThat(response[2].getAvailable()).isNull();
    assertThat(response[2].toString()).isEqualTo("BTCMarketsBalance{pendingFunds=null, balance=null, currency='LTC'}");
  }

  @Test
  public void shouldSerializeCancelOrderRequest() throws UnsupportedEncodingException, JsonProcessingException {
    assertThatSerializesCorrectly(new BTCMarketsCancelOrderRequest(6840125478L));
  }

  @Test
  public void shouldFailWhenParsingFailedCancelOrderResponseAsResponse() throws IOException {
    try {
      parse(BTCMarketsCancelOrderResponse.class);
      assertThat(true).as("Should throw exception").isFalse();
    } catch (JsonMappingException ignored) { }
  }

  @Test
  public void shouldParseEmptyCancelOrderResponse() throws IOException {
    // when
    final BTCMarketsCancelOrderResponse response = parse("EmptyCancelOrderResponse", BTCMarketsCancelOrderResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();;
  }

  @Test
  public void shouldParseNullCancelOrderResponse() throws IOException {
    // when
    final BTCMarketsCancelOrderResponse response = parse("NullCancelOrderResponse", BTCMarketsCancelOrderResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();;
  }

  @Test
  public void shouldParseCancelOrderResponseAsException() throws IOException {
    // when
    final BTCMarketsException ex = parse("CancelOrderResponse", BTCMarketsException.class);

    // then
    assertThat(ex.getSuccess()).isTrue();
    assertThat(ex.getErrorCode()).isNull();

    List<BTCMarketsException> responses = ex.getResponses();
    assertThat(responses).hasSize(2);
    assertThat(responses.get(0).getSuccess()).isTrue();
    assertThat(responses.get(0).getErrorCode()).isNull();;
    assertThat(responses.get(0).getMessage()).contains("(HTTP status code: 0)");
    assertThat(responses.get(0).getId()).isEqualTo(6840125484L);
    assertThat(responses.get(1).getSuccess()).isFalse();
    assertThat(responses.get(1).getErrorCode()).isEqualTo(3);
    assertThat(responses.get(1).getMessage()).contains("order does not exist.");
    assertThat(responses.get(1).getId()).isEqualTo(6840125478L);
  }

  @Test
  public void shouldFailWhenParsingFailedPlaceOrderResponseAsResponse() throws IOException {
    try {
      parse("Error-PlaceOrderResponse", BTCMarketsPlaceOrderResponse.class);
      assertThat(true).as("Should throw exception").isFalse();
    } catch (JsonMappingException ignored) { }
  }

  @Test
  public void shouldParseFailedPlaceOrderResponseAsException() throws IOException {
    // when
    final BTCMarketsException ex = parse("Error-PlaceOrderResponse", BTCMarketsException.class);

    // then
    assertThat(ex.getSuccess()).isFalse();
    assertThat(ex.getErrorCode()).isEqualTo(3);
    assertThat(ex.getMessage()).contains("Invalid argument.");
    assertThat(ex.getResponses()).isNull();
    assertThat(ex.getId()).isEqualTo(0);
    assertThat(ex.getClientRequestId()).isEqualTo("abc-cdf-1000");
  }

  @Test
  public void shouldSerializeMyTradingRequest() throws UnsupportedEncodingException, JsonProcessingException {
    final BTCMarketsMyTradingRequest request = new BTCMarketsMyTradingRequest(
      "AUD", "BTC", 10, new Date(33434568724000L)
    );
    assertThatSerializesCorrectly(request);
  }

  @Test
  public void shouldSerializePlaceOrderRequest() throws UnsupportedEncodingException, JsonProcessingException {
    assertThatSerializesCorrectly(new BTCMarketsOrder(
        new BigDecimal("0.10000000"),
        new BigDecimal("130.00000000"),
        "AUD", "BTC",
        BTCMarketsOrder.Side.Bid,
        BTCMarketsOrder.Type.Limit,
        "abc-cdf-1000"
    ));
  }

  @Test
  public void shoudParseOrderBook() throws IOException {
    // when
    final BTCMarketsOrderBook response = parse(BTCMarketsOrderBook.class);

    // then
    assertThat(response.getCurrency()).isEqualTo("AUD");
    assertThat(response.getInstrument()).isEqualTo("BTC");
    assertThat(response.getTimestamp().getTime()).isEqualTo(1442997827000L);

    List<BigDecimal[]> asks = response.getAsks();
    assertThat(asks).hasSize(135);
    assertThat(asks.get(2)[0]).isEqualTo(new BigDecimal("329.41"));
    assertThat(asks.get(2)[1]).isEqualTo(new BigDecimal("10.0"));
    assertThat(response.getBids()).hasSize(94);
    assertThat(response.toString()).isEqualTo(
        String.format("BTCMarketsOrderBook{currency='AUD', instrument='BTC', timestamp=%s, bids=94, asks=135}", new Date(1442997827000L)));
  }

  @Test
  public void shouldParseOrders() throws IOException {
    // when
    final BTCMarketsOrders response = parse(BTCMarketsOrders.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();

    List<BTCMarketsOrder> ordersList = response.getOrders();
    assertThat(ordersList).hasSize(2);

    BTCMarketsOrder order = ordersList.get(1);
    assertThat(order.getId()).isEqualTo(4345675);
    assertThat(order.getCurrency()).isEqualTo("AUD");
    assertThat(order.getInstrument()).isEqualTo("BTC");
    assertThat(order.getOrderSide()).isEqualTo(BTCMarketsOrder.Side.Ask);
    assertThat(order.getOrdertype()).isEqualTo(BTCMarketsOrder.Type.Limit);
    assertThat(order.getCreationTime().getTime()).isEqualTo(1378636912705L);
    assertThat(order.getStatus()).isEqualTo("Fully Matched");
    assertThat(order.getErrorMessage()).isNull();
    assertThat(order.getPrice()).isEqualTo("130.00000000");
    assertThat(order.getVolume()).isEqualTo("0.10000000");
    assertThat(order.getOpenVolume()).isEqualTo("0E-8");
    assertThat(order.getClientRequestId()).isNull();
    assertThat(order.getTrades()).hasSize(1);

    BTCMarketsUserTrade trade = order.getTrades().get(0);
    assertThat(trade.getId()).isEqualTo(5345677);
    assertThat(trade.getCreationTime().getTime()).isEqualTo(1378636913151L);
    assertThat(trade.getDescription()).isNull();
    assertThat(trade.getPrice()).isEqualTo("130.00000000");
    assertThat(trade.getVolume()).isEqualTo("0.10000000");
    assertThat(trade.getFee()).isEqualTo("0.00100000");
    assertThat(order.toString()).isEqualTo(
        String.format("BTCMarketsOrder{volume=0.10000000, price=130.00000000, currency='AUD', instrument='BTC', "
            + "orderSide=Ask, ordertype=Limit, clientRequestId='null', id=4345675, creationTime=%s, "
            + "status='Fully Matched', errorMessage='null', openVolume=0E-8, "
            + "trades=[BTCMarketsUserTrade{id=5345677, side='null', description='null', price=130.00000000, volume=0.10000000, "
            + "fee=0.00100000, creationTime=%s}]}", new Date(1378636912705L), new Date(1378636913151L)));
  }

  @Test
  public void shouldParsePlaceOrderResponse() throws IOException {
    // when
    final BTCMarketsPlaceOrderResponse response = parse(BTCMarketsPlaceOrderResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();
    assertThat(response.getId()).isEqualTo(100);
    assertThat(response.getClientRequestId()).isEqualTo("abc-cdf-1000");
  }

  @Test
  public void shouldParseTicker() throws IOException {
    // when
    final BTCMarketsTicker response = parse(BTCMarketsTicker.class);

    // then
    assertThat(response.getBestBid()).isEqualTo("137.00000000");
    assertThat(response.getBestAsk()).isEqualTo("140.00000000");
    assertThat(response.getLastPrice()).isEqualTo("140.00000000");
    assertThat(response.getCurrency()).isEqualTo("AUD");
    assertThat(response.getInstrument()).isEqualTo("BTC");
    assertThat(response.getTimestamp().getTime()).isEqualTo(1378878117000L);
    assertThat(response.toString()).isEqualTo(String.format(
        "BTCMarketsTicker{bestBid=137.00000000, bestAsk=140.00000000, lastPrice=140.00000000, currency='AUD', instrument='BTC', timestamp=%s}",
        new Date(1378878117000L)));
  }

  @Test
  public void shouldParseTradeHistory() throws IOException {
    // when
    final BTCMarketsTradeHistory response = parse(BTCMarketsTradeHistory.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();

    List<BTCMarketsUserTrade> userTrades = response.getTrades();
    assertThat(userTrades).hasSize(3);

    BTCMarketsUserTrade userTrade = userTrades.get(0);
    assertThat(userTrade.getId()).isEqualTo(45118157L);
    assertThat(userTrade.getCreationTime().getTime()).isEqualTo(1442994673684L);
    assertThat(userTrade.getDescription()).isNull();
    assertThat(userTrade.getPrice()).isEqualTo("330.00000000");
    assertThat(userTrade.getVolume()).isEqualTo("0.00100000");
    assertThat(userTrade.getSide()).isEqualTo(BTCMarketsOrder.Side.Bid);
    assertThat(userTrade.getFee()).isEqualTo("0.00280499");
    assertThat(userTrade.toString()).isEqualTo(String.format(
        "BTCMarketsUserTrade{id=45118157, side='Bid', description='null', price=330.00000000, volume=0.00100000, fee=0.00280499, creationTime=%s}",
            new Date(1442994673684L)));
    assertThat(userTrades.get(1).getSide()).isEqualTo(BTCMarketsOrder.Side.Ask);
    assertThat(response.toString()).isEqualTo("BTCMarketsTradeHistory{success=true, errorMessage='null', errorCode=null}");
  }

  private <T> void assertThatSerializesCorrectly(T request) throws JsonProcessingException, UnsupportedEncodingException {
    final String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request);
    final InputStream expected = getStream(getBaseFileName(request.getClass()));
    assertThat(new ByteArrayInputStream(json.getBytes("UTF-8"))).hasContentEqualTo(expected);
  }

}