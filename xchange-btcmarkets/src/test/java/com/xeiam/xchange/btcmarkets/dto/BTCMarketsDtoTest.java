package com.xeiam.xchange.btcmarkets.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

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

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class BTCMarketsDtoTest extends BTCMarketsDtoTestSupport {

  @Test
  public void shouldParseBalances() throws Exception {
    final BTCMarketsBalance[] response = parse(BTCMarketsBalance[].class);

    assertThat(response).hasSize(3);
    assertThat(response[2].getCurrency()).isEqualTo("LTC");
    assertThat(response[2].getBalance()).isEqualTo(new BigDecimal("10.00000000"));
    assertThat(response[2].getPendingFunds()).isEqualTo(new BigDecimal("0E-8"));
  }

  @Test
  public void shouldSerializeCancelOrderRequest() throws Exception {
    assertThatSerializesCorrectly(new BTCMarketsCancelOrderRequest(6840125478L));
  }

  @Test
  public void shouldFailWhenParsingFailedCancelOrderResponseAsResponse() throws Exception {
    try {
      parse(BTCMarketsCancelOrderResponse.class);
      assertThat(true).as("Should throw exception").isFalse();
    } catch (JsonMappingException ignored) { }
  }

  @Test
  public void shouldParseCancelOrderResponseAsException() throws Exception {
    final BTCMarketsException ex = parse("CancelOrderResponse", BTCMarketsException.class);

    assertThat(ex.getSuccess()).isTrue();
    assertThat(ex.getErrorCode()).isNull();
//    assertThat(ex.getMessage()).isNull();
    assertThat(ex.getResponses()).hasSize(1);
    assertThat(ex.getResponses().get(0).getSuccess()).isFalse();
    assertThat(ex.getResponses().get(0).getErrorCode()).isEqualTo(3);
    assertThat(ex.getResponses().get(0).getMessage()).contains("order does not exist.");
    assertThat(ex.getResponses().get(0).getId()).isEqualTo(6840125478L);
  }

  @Test
  public void shouldFailWhenParsingFailedPlaceOrderResponseAsResponse() throws Exception {
    try {
      parse("Error-PlaceOrderResponse", BTCMarketsPlaceOrderResponse.class);
      assertThat(true).as("Should throw exception").isFalse();
    } catch (JsonMappingException ignored) { }
  }

  @Test
  public void shouldParseFailedPlaceOrderResponseAsException() throws Exception {
    final BTCMarketsException ex = parse("Error-PlaceOrderResponse", BTCMarketsException.class);

    assertThat(ex.getSuccess()).isFalse();
    assertThat(ex.getErrorCode()).isEqualTo(3);
    assertThat(ex.getMessage()).contains("Invalid argument.");
    assertThat(ex.getResponses()).isNull();
    assertThat(ex.getId()).isEqualTo(0);
    assertThat(ex.getClientRequestId()).isEqualTo("abc-cdf-1000");
  }

  @Test
  public void shouldSerializeMyTradingRequest() throws Exception {
    final BTCMarketsMyTradingRequest request = new BTCMarketsMyTradingRequest(
      "AUD", "BTC", 10, new Date(33434568724000L)
    );
    assertThatSerializesCorrectly(request);
  }

  @Test
  public void shouldSerializePlaceOrderRequest() throws Exception {
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
  public void shoudParseOrderBook() throws Exception {
    final BTCMarketsOrderBook response = parse(BTCMarketsOrderBook.class);

    assertThat(response.getCurrency()).isEqualTo("AUD");
    assertThat(response.getInstrument()).isEqualTo("BTC");
    assertThat(response.getTimestamp().getTime()).isEqualTo(1442997827000L);
    assertThat(response.getAsks()).hasSize(135);
    assertThat(response.getAsks().get(2)[0]).isEqualTo(new BigDecimal("329.41"));
    assertThat(response.getAsks().get(2)[1]).isEqualTo(new BigDecimal("10.0"));
    assertThat(response.getBids()).hasSize(94);
  }

  @Test
  public void shouldParseOrders() throws Exception {
    final BTCMarketsOrders response = parse(BTCMarketsOrders.class);

    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();
    assertThat(response.getOrders()).hasSize(2);
    assertThat(response.getOrders().get(1).getId()).isEqualTo(4345675);
    assertThat(response.getOrders().get(1).getCurrency()).isEqualTo("AUD");
    assertThat(response.getOrders().get(1).getInstrument()).isEqualTo("BTC");
    assertThat(response.getOrders().get(1).getOrderSide()).isEqualTo(BTCMarketsOrder.Side.Ask);
    assertThat(response.getOrders().get(1).getOrdertype()).isEqualTo(BTCMarketsOrder.Type.Limit);
    assertThat(response.getOrders().get(1).getCreationTime().getTime()).isEqualTo(1378636912705L);
    assertThat(response.getOrders().get(1).getStatus()).isEqualTo("Fully Matched");
    assertThat(response.getOrders().get(1).getErrorMessage()).isNull();
    assertThat(response.getOrders().get(1).getPrice()).isEqualTo("130.00000000");
    assertThat(response.getOrders().get(1).getVolume()).isEqualTo("0.10000000");
    assertThat(response.getOrders().get(1).getOpenVolume()).isEqualTo("0E-8");
    assertThat(response.getOrders().get(1).getClientRequestId()).isNull();
    assertThat(response.getOrders().get(1).getTrades()).hasSize(1);
    assertThat(response.getOrders().get(1).getTrades().get(0).getId()).isEqualTo(5345677);
    assertThat(response.getOrders().get(1).getTrades().get(0).getCreationTime().getTime()).isEqualTo(1378636913151L);
    assertThat(response.getOrders().get(1).getTrades().get(0).getDescription()).isNull();
    assertThat(response.getOrders().get(1).getTrades().get(0).getPrice()).isEqualTo("130.00000000");
    assertThat(response.getOrders().get(1).getTrades().get(0).getVolume()).isEqualTo("0.10000000");
    assertThat(response.getOrders().get(1).getTrades().get(0).getFee()).isEqualTo("0.00100000");
  }

  @Test
  public void shouldParsePlaceOrderResponse() throws Exception {
    final BTCMarketsPlaceOrderResponse response = parse(BTCMarketsPlaceOrderResponse.class);

    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();
    assertThat(response.getId()).isEqualTo(100);
    assertThat(response.getClientRequestId()).isEqualTo("abc-cdf-1000");
  }

  @Test
  public void shouldParseTicker() throws Exception {
    final BTCMarketsTicker response = parse(BTCMarketsTicker.class);

    assertThat(response.getBestBid()).isEqualTo("137.00000000");
    assertThat(response.getBestAsk()).isEqualTo("140.00000000");
    assertThat(response.getLastPrice()).isEqualTo("140.00000000");
    assertThat(response.getCurrency()).isEqualTo("AUD");
    assertThat(response.getInstrument()).isEqualTo("BTC");
    assertThat(response.getTimestamp().getTime()).isEqualTo(1378878117000L);
  }

  @Test
  public void shouldParseTradeHistory() throws Exception {
    final BTCMarketsTradeHistory response = parse(BTCMarketsTradeHistory.class);

    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();
    assertThat(response.getTrades()).hasSize(3);
    assertThat(response.getTrades().get(0).getId()).isEqualTo(45118157L);
    assertThat(response.getTrades().get(0).getCreationTime().getTime()).isEqualTo(1442994673684L);
    assertThat(response.getTrades().get(0).getDescription()).isNull();
    assertThat(response.getTrades().get(0).getPrice()).isEqualTo("330.00000000");
    assertThat(response.getTrades().get(0).getVolume()).isEqualTo("0.00100000");
    assertThat(response.getTrades().get(0).getSide()).isEqualTo(BTCMarketsOrder.Side.Bid);
    assertThat(response.getTrades().get(0).getFee()).isEqualTo("0.00280499");
    assertThat(response.getTrades().get(1).getSide()).isEqualTo(BTCMarketsOrder.Side.Ask);
  }

  //////////////////////////////////////////////////////////////////////////////////////////

  private <T> void assertThatSerializesCorrectly(T request) throws JsonProcessingException, UnsupportedEncodingException {
    final String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request);
    final InputStream expected = getStream(getBaseFileName(request.getClass()));
    assertThat(new ByteArrayInputStream(json.getBytes("UTF-8"))).hasContentEqualTo(expected);
  }

}