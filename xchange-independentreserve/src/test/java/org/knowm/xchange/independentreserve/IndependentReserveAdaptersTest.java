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
}
