package org.knowm.xchange.bitmarket.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.bitmarket.BitMarketAssert;
import org.knowm.xchange.bitmarket.BitMarketTestSupport;
import org.knowm.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import org.knowm.xchange.bitmarket.dto.account.BitMarketDepositResponse;
import org.knowm.xchange.bitmarket.dto.account.BitMarketWithdrawResponse;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketCancelResponse;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketTradeResponse;

public class BitMarketDtoTest extends BitMarketTestSupport {

  @Test
  public void shouldParseMarketAccountInfo() throws IOException {
    // when
    BitMarketAccountInfoResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/account/example-info-data",
            BitMarketAccountInfoResponse.class);

    // then
    verifySuccessResponse(response);
    BitMarketAssert.assertEquals(response.getData().getBalance(), PARSED_BALANCE);
    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test
  public void shouldParseMarketAccountInfoError() throws IOException {
    verifyErrorResponse(BitMarketAccountInfoResponse.class);
  }

  @Test
  public void shouldParseCancelOrder() throws IOException {
    // when
    BitMarketCancelResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-cancel-order",
            BitMarketCancelResponse.class);

    // then
    verifySuccessResponse(response);
    BitMarketAssert.assertEquals(response.getData(), PARSED_BALANCE);
    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test
  public void shouldParseCancelOrderError() throws IOException {
    verifyErrorResponse(BitMarketCancelResponse.class);
  }

  @Test
  public void shouldParseDeposit() throws IOException {
    // when
    BitMarketDepositResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/account/example-deposit-data",
            BitMarketDepositResponse.class);

    // then
    verifySuccessResponse(response);
    assertThat(response.getData()).isEqualTo("BITMarket");
    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test
  public void shouldParseDepositError() throws IOException {
    verifyErrorResponse(BitMarketDepositResponse.class);
  }

  @Test
  public void shouldParseWithdraw() throws IOException {
    // when
    BitMarketWithdrawResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/account/example-withdraw-data",
            BitMarketWithdrawResponse.class);

    // then
    verifySuccessResponse(response);
    assertThat(response.getData()).isEqualTo("12345");
    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test
  public void shouldParseWithdrawError() throws IOException {
    verifyErrorResponse(BitMarketWithdrawResponse.class);
  }

  @Test
  public void shouldParseMarketTrade() throws IOException {
    // when
    BitMarketTradeResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-trade-data",
            BitMarketTradeResponse.class);

    // then
    verifySuccessResponse(response);

    BitMarketAssert.assertEquals(response.getData().getOrder(), PARSED_TRADE_ORDER);
    BitMarketAssert.assertEquals(response.getData().getBalance(), PARSED_TRADE_BALANCE);

    verifyResponseLimit(response.getLimit(), 39, 6000, 1432920000L);
  }

  @Test
  public void shouldParseMarketTradeError() throws IOException {
    verifyErrorResponse(BitMarketTradeResponse.class);
  }

  @Test
  public void shouldParseOrderBook() throws IOException {
    // when
    BitMarketOrderBook response =
        parse(
            "org/knowm/xchange/bitmarket/dto/marketdata/example-order-book-data",
            BitMarketOrderBook.class);

    // then
    BitMarketAssert.assertEquals(response, PARSED_ORDER_BOOK);
  }

  @Test
  public void shouldParseTicker() throws IOException {
    // when
    BitMarketTicker response =
        parse(
            "org/knowm/xchange/bitmarket/dto/marketdata/example-ticker-data",
            BitMarketTicker.class);

    // then
    BitMarketAssert.assertEquals(response, PARSED_TICKER);
  }

  @Test
  public void shouldParseTrades() throws IOException {
    // given
    final BitMarketTrade[] expectedParsedTrades = expectedParsedTrades();

    // when
    BitMarketTrade[] trades =
        parse(
            "org/knowm/xchange/bitmarket/dto/marketdata/example-trades-data",
            BitMarketTrade[].class);

    // then
    assertThat(trades).hasSize(3);
    for (int i = 0; i < trades.length; i++) {
      BitMarketAssert.assertEquals(trades[i], expectedParsedTrades[i]);
    }
  }
}
