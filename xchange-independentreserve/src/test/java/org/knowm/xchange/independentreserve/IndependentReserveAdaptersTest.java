package org.knowm.xchange.independentreserve;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOrderDetailsResponse;

public class IndependentReserveAdaptersTest {

  @Test
  public void adaptOrderDetails() throws InvalidFormatException {
    IndependentReserveOrderDetailsResponse orderDetailsResponse =
        new IndependentReserveOrderDetailsResponse(
            "abcf-123",
            "2014-09-23T12:39:34.3817763Z",
            "MarketBid",
            new BigDecimal(5.0),
            new BigDecimal(4.0),
            new BigDecimal(100),
            new BigDecimal(95),
            new BigDecimal(0),
            "PartiallyFilled",
            "Xbt",
            "Usd");
    MarketOrder order =
        (MarketOrder) IndependentReserveAdapters.adaptOrderDetails(orderDetailsResponse);
    assertThat(order.getId()).isEqualTo("abcf-123");
    assertThat(order.getTimestamp())
        .isEqualTo(Date.from(ZonedDateTime.of(2014, 9, 23, 12, 39, 34, 0, UTC).toInstant()));
    assertThat(order.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(order.getOriginalAmount()).isEqualByComparingTo(new BigDecimal(5));
    assertThat(order.getCumulativeAmount()).isEqualByComparingTo(new BigDecimal(4));
    assertThat(order.getAveragePrice()).isEqualByComparingTo(new BigDecimal(95));
    assertThat(order.getFee()).isNull();
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.PARTIALLY_FILLED);
    assertThat(order.getCurrencyPair()).isEqualTo(new CurrencyPair("Xbt", "Usd"));
  }

  @Test
  public void adaptOrderDetailsWithFailedStatus() throws InvalidFormatException {
    // A market order Independent Reserve accepted (200 + GUID) and then failed internally:
    // GetOrderDetails returns Status "Failed" with zero fill. See OrderStatus.cs in the official
    // dotNetApiClient: Failed(7) = "Order failed to execute".
    Order order =
        IndependentReserveAdapters.adaptOrderDetails(
            orderDetailsResponseWithStatus("Failed", new BigDecimal(0)));
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.REJECTED);
  }

  @Test
  public void adaptOrderDetailsWithPartiallyFilledAndFailedStatus() throws InvalidFormatException {
    // PartiallyFilledAndFailed(8) = "Order was partially executed but later failed and will not
    // execute further" — terminal with a partial fill, like PartiallyFilledAndCancelled.
    Order order =
        IndependentReserveAdapters.adaptOrderDetails(
            orderDetailsResponseWithStatus("PartiallyFilledAndFailed", new BigDecimal(2)));
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.PARTIALLY_CANCELED);
  }

  @Test
  public void adaptOrderDetailsWithPartiallyFilledAndExpiredStatus() throws InvalidFormatException {
    // PartiallyFilledAndExpired(6) is terminal with a partial fill as well; mapping it to EXPIRED
    // would hide the partial execution, so all three PartiallyFilledAnd* statuses adapt to
    // PARTIALLY_CANCELED while a plain Expired stays EXPIRED.
    Order order =
        IndependentReserveAdapters.adaptOrderDetails(
            orderDetailsResponseWithStatus("PartiallyFilledAndExpired", new BigDecimal(2)));
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.PARTIALLY_CANCELED);
  }

  @Test
  public void adaptOrderDetailsWithExpiredStatus() throws InvalidFormatException {
    Order order =
        IndependentReserveAdapters.adaptOrderDetails(
            orderDetailsResponseWithStatus("Expired", new BigDecimal(0)));
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.EXPIRED);
  }

  private IndependentReserveOrderDetailsResponse orderDetailsResponseWithStatus(
      String status, BigDecimal volumeFilled) throws InvalidFormatException {
    return new IndependentReserveOrderDetailsResponse(
        "abcf-123",
        "2014-09-23T12:39:34.3817763Z",
        "MarketBid",
        new BigDecimal(5.0),
        volumeFilled,
        new BigDecimal(100),
        new BigDecimal(95),
        new BigDecimal(0),
        status,
        "Xbt",
        "Usd");
  }
}
