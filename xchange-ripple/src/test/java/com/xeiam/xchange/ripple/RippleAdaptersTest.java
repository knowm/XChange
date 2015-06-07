package com.xeiam.xchange.ripple;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBook;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBookTest;

public class RippleAdaptersTest {
  @Test
  public void testOrderBookAdapter() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = RippleOrderBookTest.class.getResourceAsStream("/marketdata/example-order-book.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderBook rippleOrderBook = mapper.readValue(is, RippleOrderBook.class);

    // Convert to xchange object and check field values
    final OrderBook orderBook = RippleAdapters.adaptOrderBook(rippleOrderBook, CurrencyPair.XRP_BTC);
    assertThat(orderBook.getBids().size()).isEqualTo(10);
    assertThat(orderBook.getAsks().size()).isEqualTo(10);

    final LimitOrder lastBid = orderBook.getBids().get(9);
    assertThat(lastBid.getType()).isEqualTo(OrderType.BID);
    assertThat(lastBid.getId()).isEqualTo("1303704");
    assertThat(lastBid.getTradableAmount()).isEqualTo(new BigDecimal("66314.537782"));
    assertThat(lastBid.getLimitPrice()).isEqualTo(new BigDecimal("0.00003317721777288062"));

    final LimitOrder firstAsk = orderBook.getAsks().get(0);
    assertThat(firstAsk.getType()).isEqualTo(OrderType.ASK);
    assertThat(firstAsk.getId()).isEqualTo("1011310");
    assertThat(firstAsk.getTradableAmount()).isEqualTo(new BigDecimal("35447.914936"));
    assertThat(firstAsk.getLimitPrice()).isEqualTo(new BigDecimal("0.00003380846624897726"));

  }

}
