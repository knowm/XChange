package org.knowm.xchange.ftx;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.trade.FtxConditionalOrderRequestPayload;
import org.knowm.xchange.ftx.dto.trade.FtxConditionalOrderType;
import org.knowm.xchange.ftx.dto.trade.FtxModifyConditionalOrderRequestPayload;
import org.knowm.xchange.ftx.dto.trade.FtxOrderDto;
import org.knowm.xchange.ftx.dto.trade.FtxOrderFlags;
import org.knowm.xchange.ftx.dto.trade.FtxOrderSide;
import org.knowm.xchange.ftx.dto.trade.FtxOrderStatus;

public class FtxAdapterTest {

  @Test
  public void adaptCurrencyPairToFtxPair() {
    assertPair("BTC-USD", "BTC/USD", "BTC/USD");
    assertPair("BTC-PERP", "BTC/PERP", "BTC-PERP");
    assertPair("BTC-0625", "BTC/0625", "BTC-0625");
  }

  private void assertPair(String market, String expString, String expAdapted) {
    CurrencyPair currencyPair = new CurrencyPair(market);
    assertThat(currencyPair.toString()).isEqualTo(expString);
    assertThat(FtxAdapters.adaptCurrencyPairToFtxMarket(currencyPair)).isEqualTo(expAdapted);
  }

  @Test
  public void adaptOpenOrdersTest() throws IOException {
    InputStream is =
        FtxAdapterTest.class.getResourceAsStream("/responses/example-ftxOpenOrders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxResponse<List<FtxOrderDto>> ftxResponse =
        mapper.readValue(is, new TypeReference<FtxResponse<List<FtxOrderDto>>>() {});

    assertThat(FtxAdapters.adaptOpenOrders(ftxResponse).getOpenOrders().size()).isEqualTo(1);
  }

  @Test
  public void adaptUserTradesTest() throws IOException {
    InputStream is =
        FtxAdapterTest.class.getResourceAsStream("/responses/example-ftxOrderHistory.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxResponse<List<FtxOrderDto>> ftxResponse =
        mapper.readValue(is, new TypeReference<FtxResponse<List<FtxOrderDto>>>() {});

    assertThat(FtxAdapters.adaptOpenOrders(ftxResponse).getOpenOrders().size()).isEqualTo(1);
  }

  @Test
  public void adaptFtxOrderStatusToOrderStatusTest() {
    assertThat(FtxAdapters.adaptFtxOrderStatusToOrderStatus(FtxOrderStatus.NEW))
        .isEqualTo(OrderStatus.NEW);
    assertThat(FtxAdapters.adaptFtxOrderStatusToOrderStatus(FtxOrderStatus.CANCELLED))
        .isEqualTo(OrderStatus.CANCELED);
    assertThat(FtxAdapters.adaptFtxOrderStatusToOrderStatus(FtxOrderStatus.CLOSED))
        .isEqualTo(OrderStatus.CLOSED);
    assertThat(FtxAdapters.adaptFtxOrderStatusToOrderStatus(FtxOrderStatus.TRIGGERED))
        .isEqualTo(OrderStatus.NEW);
    assertThat(FtxAdapters.adaptFtxOrderStatusToOrderStatus(FtxOrderStatus.OPEN))
        .isEqualTo(OrderStatus.OPEN);
  }

  @Test
  public void adaptConditionalOrderToFtxOrderPayloadTest() {
    CurrencyPair currencyPair = new CurrencyPair(Currency.BTC.getCurrencyCode(), "PERP");
    OrderType orderType = OrderType.BID;
    BigDecimal amount = BigDecimal.valueOf(0.001);
    Date date = Date.from(LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC));
    BigDecimal limit = BigDecimal.valueOf(30000);
    BigDecimal stopPrice = BigDecimal.valueOf(42000);
    StopOrder stopOrder =
        new StopOrder(
            orderType, amount, currencyPair, null, date, stopPrice, limit, null, null, null, null);

    FtxModifyConditionalOrderRequestPayload result =
        FtxAdapters.adaptModifyConditionalOrderToFtxOrderPayload(stopOrder);

    assertThat(result.getOrderPrice()).isEqualTo(limit);
    assertThat(result.getSize()).isEqualTo(amount);
    assertThat(result.getTriggerPrice()).isEqualTo(stopPrice);
  }

  @Test
  public void adaptStopOrderToFtxOrderPayloadTest() {
    CurrencyPair currencyPair = new CurrencyPair(Currency.BTC.getCurrencyCode(), "PERP");
    OrderType orderType = OrderType.BID;
    BigDecimal amount = BigDecimal.valueOf(0.001);
    Date date = Date.from(LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC));
    BigDecimal limitPrice = BigDecimal.valueOf(30000);
    BigDecimal stopPrice = BigDecimal.valueOf(42000);

    StopOrder stopOrder =
        new StopOrder(
            orderType, amount, currencyPair, null, date, stopPrice, limitPrice, null, null, null, null);
    stopOrder.addOrderFlag(FtxOrderFlags.REDUCE_ONLY);
    stopOrder.addOrderFlag(FtxOrderFlags.RETRY_UNTIL_FILLED);

    FtxConditionalOrderRequestPayload result =
        FtxAdapters.adaptStopOrderToFtxOrderPayload(stopOrder);

    assertThat(result.getMarket()).isEqualTo("BTC-PERP");
    assertThat(result.getOrderPrice()).isEqualTo(limitPrice);
    assertThat(result.getSize()).isEqualTo(amount);
    assertThat(result.getTriggerPrice()).isEqualTo(stopPrice);
    assertThat(result.getType()).isEqualTo(FtxConditionalOrderType.stop);
    assertThat(result.getSide()).isEqualTo(FtxOrderSide.buy);
    assertThat(result.isReduceOnly()).isEqualTo(true);
    assertThat(result.isRetryUntilFilled()).isEqualTo(true);
  }
}
