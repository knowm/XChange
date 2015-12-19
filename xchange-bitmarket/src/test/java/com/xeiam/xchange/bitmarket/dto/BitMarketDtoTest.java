package com.xeiam.xchange.bitmarket.dto;

import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketBalance;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketDepositResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketWithdrawResponse;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketCancelResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrder;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import com.xeiam.xchange.dto.Order;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class BitMarketDtoTest extends BitMarketDtoTestSupport {

  @Test public void shouldParseMarketAccountInfo() throws IOException {
    // when
    BitMarketAccountInfoResponse response = parse("account/example-info-data", BitMarketAccountInfoResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getError()).isEqualTo(0);
    assertThat(response.getErrorMsg()).isNull();

    BitMarketBalance balance = response.getData().getBalance();
    Map<String,BigDecimal> available = balance.getAvailable();
    Map<String,BigDecimal> blocked = balance.getBlocked();

    assertThat(available.get("PLN")).isEqualTo(new BigDecimal("4.166000000000"));
    assertThat(available.get("BTC")).isEqualTo(new BigDecimal("0.029140000000"));
    assertThat(available.get("LTC")).isEqualTo(new BigDecimal("10.301000000000"));
    assertThat(blocked.get("PLN")).isEqualTo(new BigDecimal("59.4"));
    assertThat(blocked.get("BTC")).isEqualTo(new BigDecimal("0"));
    assertThat(blocked.get("LTC")).isEqualTo(new BigDecimal("0.089"));

    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test public void shouldParseMarketAccountInfoError() throws IOException {
    verifyErrorResponse(BitMarketAccountInfoResponse.class);
  }

  @Test public void shouldParseCancelOrder() throws IOException {
    // when
    BitMarketCancelResponse response = parse("trade/example-cancel-order", BitMarketCancelResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getError()).isEqualTo(0);
    assertThat(response.getErrorMsg()).isNull();

    BitMarketBalance balance = response.getData();
    Map<String,BigDecimal> available = balance.getAvailable();
    Map<String,BigDecimal> blocked = balance.getBlocked();

    assertThat(available.get("PLN")).isEqualTo(new BigDecimal("4.166000000000"));
    assertThat(available.get("BTC")).isEqualTo(new BigDecimal("0.029140000000"));
    assertThat(available.get("LTC")).isEqualTo(new BigDecimal("10.301000000000"));
    assertThat(blocked.get("PLN")).isEqualTo(new BigDecimal("59.4"));
    assertThat(blocked.get("BTC")).isEqualTo(new BigDecimal("0"));
    assertThat(blocked.get("LTC")).isEqualTo(new BigDecimal("0.089"));

    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test public void shouldParseCancelOrderError() throws IOException {
    verifyErrorResponse(BitMarketCancelResponse.class);
  }

  @Test public void shouldParseDeposit() throws IOException {
    // when
    BitMarketDepositResponse response = parse("account/example-deposit-data", BitMarketDepositResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getError()).isEqualTo(0);
    assertThat(response.getErrorMsg()).isNull();
    assertThat(response.getData()).isEqualTo("BITMarket");

    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test public void shouldParseDepositError() throws IOException {
    verifyErrorResponse(BitMarketDepositResponse.class);
  }

  @Test public void shouldParseWithdraw() throws IOException {
    // when
    BitMarketWithdrawResponse response = parse("account/example-withdraw-data", BitMarketWithdrawResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getError()).isEqualTo(0);
    assertThat(response.getErrorMsg()).isNull();
    assertThat(response.getData()).isEqualTo("12345");

    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test public void shouldParseWithdrawError() throws IOException {
    verifyErrorResponse(BitMarketWithdrawResponse.class);
  }

  @Test public void shouldParseMarketTrade() throws IOException {
    // when
    BitMarketTradeResponse response = parse("trade/example-trade-data", BitMarketTradeResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getError()).isEqualTo(0);
    assertThat(response.getErrorMsg()).isNull();

    BitMarketOrder order = response.getData().getOrder();
    BitMarketBalance balance = response.getData().getBalance();
    Map<String,BigDecimal> available = balance.getAvailable();
    Map<String,BigDecimal> blocked = balance.getBlocked();

    assertThat(order.getId()).isEqualTo(31408);
    assertThat(order.getMarket()).isEqualTo("BTCPLN");
    assertThat(order.getAmount()).isEqualTo(new BigDecimal("0.50000000"));
    assertThat(order.getRate()).isEqualTo(new BigDecimal("4000.0000"));
    assertThat(order.getFiat()).isEqualTo(new BigDecimal("2000.00000000"));
    assertThat(order.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(order.getTime()).isEqualTo(1432916922);
    assertThat(available).hasSize(7);
    assertThat(available.get("PLN")).isEqualTo("1197.00000000");
    assertThat(available.get("EUR")).isEqualTo("0.00000000");
    assertThat(available.get("BTC")).isEqualTo("27.01000000");
    assertThat(available.get("LTC")).isEqualTo("0.00000000");
    assertThat(available.get("DOGE")).isEqualTo("0.00000000");
    assertThat(available.get("PPC")).isEqualTo("0.00000000");
    assertThat(available.get("LiteMineX")).isEqualTo("0.00000000");
    assertThat(blocked).hasSize(5);
    assertThat(blocked.get("PLN")).isEqualTo("570.00000000");
    assertThat(blocked.get("BTC")).isEqualTo("3.05000000");
    assertThat(blocked.get("EUR")).isEqualTo("0.00000000");
    assertThat(blocked.get("LTC")).isEqualTo("0.00000000");
    assertThat(blocked.get("LiteMineX")).isEqualTo("0.00000000");

    verifyResponseLimit(response.getLimit(), 39, 6000, 1432920000L);
  }

  @Test public void shouldParseMarketTradeError() throws IOException {
    verifyErrorResponse(BitMarketTradeResponse.class);
  }

  @Test public void shouldParseOrderBook() throws IOException {
    // when
    BitMarketOrderBook response = parse("marketdata/example-order-book-data", BitMarketOrderBook.class);

    // then
    assertThat(response.toString()).isEqualTo("BitMarketOrderBook{asks=[14.6999, 20.47];[14.7, 10.06627287];, "
        + "bids=[14.4102, 1.55];[14.4101, 27.77224019];[0, 52669.33019064];}");
  }

  @Test public void shouldParseTicker() throws IOException {
    // when
    BitMarketTicker response = parse("marketdata/example-ticker-data", BitMarketTicker.class);

    // then
    assertThat(response.toString()).isEqualTo("BitMarketTicker{ask=1794.5000, bid=1789.2301, last=1789.2001, "
        + "low=1756.5000, high=1813.5000, vwap=1785.8484, volume=455.69192487}");
  }

  @Test public void shouldParseTrades() throws IOException {
    // when
    BitMarketTrade[] trades = parse("marketdata/example-trades-data", BitMarketTrade[].class);

    // then
    assertThat(trades).hasSize(3);
    assertThat(trades[0].toString()).isEqualTo(
        "BitMarketTrade{tid='78455', price=14.6900, amount=27.24579867, date=1450344119}");
    assertThat(trades[1].toString()).isEqualTo(
        "BitMarketTrade{tid='78454', price=14.4105, amount=5.22284399, date=1450343831}");
    assertThat(trades[2].toString()).isEqualTo(
        "BitMarketTrade{tid='78453', price=14.4105, amount=0.10560487, date=1450303414}");
  }
}