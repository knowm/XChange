package org.knowm.xchange.btcmarkets.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.btcmarkets.BtcMarketsAssert;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransferHistoryResponse;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsUserTrade;
import org.knowm.xchange.btcmarkets.service.BTCMarketsTestSupport;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

public class BTCMarketsDtoTest extends BTCMarketsTestSupport {

  @Test
  public void shouldParseBalances() throws IOException {
    final BTCMarketsBalance[] response = parse(BTCMarketsBalance[].class);

    assertThat(response).hasSize(3);
    assertThat(response[2].getCurrency()).isEqualTo("LTC");
    assertThat(response[2].getBalance()).isEqualTo(new BigDecimal("10.00000000"));
    assertThat(response[2].getPendingFunds()).isEqualTo(new BigDecimal("0E-8"));
    assertThat(response[2].toString())
        .isEqualTo("BTCMarketsBalance{pendingFunds=0E-8, balance=10.00000000, currency='LTC'}");
  }

  @Test
  public void shouldParseNullAvailabilityBalances() throws IOException {
    // given
    final BTCMarketsBalance[] expectedBtcMarketsBalances = expectedBtcMarketsBalances();

    // when
    final BTCMarketsBalance[] response =
        parse(
            "org/knowm/xchange/btcmarkets/dto/" + "NullAvailabilityBalances",
            BTCMarketsBalance[].class);

    // then
    assertThat(response).hasSize(3);
    for (int i = 0; i < response.length; i++) {
      BtcMarketsAssert.assertEquals(response[i], expectedBtcMarketsBalances[i]);
    }
  }

  @Test
  public void shouldFailWhenParsingFailedCancelOrderResponseAsResponse() throws IOException {
    try {
      parse(BTCMarketsCancelOrderResponse.class);
      assertThat(true).as("Should throw exception").isFalse();
    } catch (JsonMappingException ignored) {
    }
  }

  @Test
  public void shouldParseEmptyCancelOrderResponse() throws IOException {
    // when
    final BTCMarketsCancelOrderResponse response =
        parse(
            "org/knowm/xchange/btcmarkets/dto/" + "EmptyCancelOrderResponse",
            BTCMarketsCancelOrderResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();
  }

  @Test
  public void shouldParseNullCancelOrderResponse() throws IOException {
    // when
    final BTCMarketsCancelOrderResponse response =
        parse(
            "org/knowm/xchange/btcmarkets/dto/" + "NullCancelOrderResponse",
            BTCMarketsCancelOrderResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();
  }

  @Test
  public void shouldParseCancelOrderResponseAsException() throws IOException {
    // when
    final BTCMarketsException ex =
        parse(
            "org/knowm/xchange/btcmarkets/dto/" + "CancelOrderResponse", BTCMarketsException.class);

    // then
    assertThat(ex.getSuccess()).isTrue();
    assertThat(ex.getErrorCode()).isNull();

    List<BTCMarketsException> responses = ex.getResponses();
    assertThat(responses).hasSize(2);

    BTCMarketsException response1 = responses.get(0);
    assertThat(response1.getSuccess()).isTrue();
    assertThat(response1.getErrorCode()).isNull();
    assertThat(response1.getMessage()).contains("(HTTP status code: 0)");
    assertThat(response1.getId()).isEqualTo(6840125484L);

    BTCMarketsException response2 = responses.get(1);
    assertThat(response2.getSuccess()).isFalse();
    assertThat(response2.getErrorCode()).isEqualTo(3);
    assertThat(response2.getMessage()).contains("order does not exist.");
    assertThat(response2.getId()).isEqualTo(6840125478L);
  }

  @Test
  public void shouldFailWhenParsingFailedPlaceOrderResponseAsResponse() throws IOException {
    try {
      parse("Error-PlaceOrderResponse", BTCMarketsPlaceOrderResponse.class);
      assertThat(true).as("Should throw exception").isFalse();
    } catch (IllegalArgumentException ignored) {
    }
  }

  @Test
  public void shouldParseFailedPlaceOrderResponseAsException() throws IOException {
    // when
    final BTCMarketsException ex =
        parse(
            "org/knowm/xchange/btcmarkets/dto/" + "Error-PlaceOrderResponse",
            BTCMarketsException.class);

    // then
    assertThat(ex.getSuccess()).isFalse();
    assertThat(ex.getErrorCode()).isEqualTo(3);
    assertThat(ex.getMessage()).contains("Invalid argument.");
    assertThat(ex.getResponses()).isNull();
    assertThat(ex.getId()).isEqualTo(0);
    assertThat(ex.getClientRequestId()).isEqualTo("abc-cdf-1000");
  }

  @Test
  public void shoudParseOrderBook() throws IOException {
    // given
    final LimitOrder[] expectedAsks = expectedAsks();
    final LimitOrder[] expectedBids = expectedBids();

    // when
    final BTCMarketsOrderBook response =
        parse("org/knowm/xchange/btcmarkets/dto/" + "ShortOrderBook", BTCMarketsOrderBook.class);

    // then
    assertThat(response.getCurrency()).isEqualTo("AUD");
    assertThat(response.getInstrument()).isEqualTo("BTC");
    assertThat(response.getTimestamp().getTime()).isEqualTo(1442997827000L);

    List<BigDecimal[]> asks = response.getAsks();
    assertThat(asks).hasSize(3);

    for (int i = 0; i < asks.size(); i++) {
      BtcMarketsAssert.assertEquals(
          expectedAsks[i], Order.OrderType.ASK, CurrencyPair.BTC_AUD, asks.get(i));
    }

    List<BigDecimal[]> bids = response.getBids();
    assertThat(bids).hasSize(2);
    for (int i = 0; i < bids.size(); i++) {
      BtcMarketsAssert.assertEquals(
          expectedBids[i], Order.OrderType.BID, CurrencyPair.BTC_AUD, bids.get(i));
    }

    assertThat(response.toString())
        .isEqualTo(
            String.format(
                "BTCMarketsOrderBook{currency='AUD', instrument='BTC', timestamp=%s, bids=2, asks=3}",
                new Date(1442997827000L)));
  }

  @Test
  public void shouldParseOrders() throws IOException {
    // given
    final BTCMarketsOrder[] expectedParsedBtcMarketsOrders = expectedParsedBtcMarketsOrders();

    // when
    final BTCMarketsOrders response = parse(BTCMarketsOrders.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();

    List<BTCMarketsOrder> ordersList = response.getOrders();
    assertThat(ordersList).hasSize(2);
    for (int i = 0; i < ordersList.size(); i++) {
      BtcMarketsAssert.assertEquals(ordersList.get(i), expectedParsedBtcMarketsOrders[i]);
    }
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
    assertThat(response.toString())
        .isEqualTo(
            "BTCMarketsPlaceOrderResponse{success=true, errorMessage='null', errorCode=null}");
  }

  @Test
  public void shouldParseTicker() throws IOException {
    // when
    final BTCMarketsTicker response = parse(BTCMarketsTicker.class);

    // then
    BtcMarketsAssert.assertEquals(response, EXPECTED_BTC_MARKETS_TICKER);
  }

  @Test
  public void shouldParseTradeHistory() throws IOException {
    // given
    final List<BTCMarketsUserTrade> expectedParsedBtcMarketsUserTrades =
        expectedParsedBtcMarketsUserTrades();

    // when
    final BTCMarketsTradeHistory response = parse(BTCMarketsTradeHistory.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getErrorCode()).isNull();
    assertThat(response.getErrorMessage()).isNull();

    List<BTCMarketsUserTrade> userTrades = response.getTrades();
    assertThat(userTrades).hasSize(3);
    for (int i = 0; i < userTrades.size(); i++) {
      BtcMarketsAssert.assertEquals(userTrades.get(i), expectedParsedBtcMarketsUserTrades.get(i));
    }
  }

  @Test
  public void shouldParseFundTransfers() throws IOException {
    // given
    final BTCMarketsFundtransferHistoryResponse
        expectedParsedBtcMarketsFundtransferHistoryResponse =
            expectedParsedBTCMarketsFundtransferHistoryResponse();

    // when
    final BTCMarketsFundtransferHistoryResponse response =
        parse(BTCMarketsFundtransferHistoryResponse.class);

    assertThat(response.toString())
        .isEqualTo(expectedParsedBtcMarketsFundtransferHistoryResponse.toString());
  }
}
