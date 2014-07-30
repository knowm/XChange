package com.xeiam.xchange.justcoin.service.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinOrder;

/**
 * @author jamespedwards42
 */
public class JustcoinOrdersTest {

  private String id;
  private String tradableIdentifier;
  private String transactionCurrency;
  private BigDecimal price;
  private BigDecimal amount;
  private Date orderCreatedAt;
  private JustcoinOrder justcoinOrder;

  @Before
  public void before() {

    // initialize expected values
    id = "1895549";
    tradableIdentifier = Currencies.BTC;
    transactionCurrency = Currencies.LTC;
    price = BigDecimal.valueOf(45.000);
    amount = BigDecimal.valueOf(0.02000);
    try {
      orderCreatedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse("2014-01-24T05:25:47.774Z");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    justcoinOrder =
        new JustcoinOrder(id, JustcoinUtils.getApiMarket(tradableIdentifier, transactionCurrency), "ask", price, amount, BigDecimal.valueOf(0.02000), BigDecimal.ZERO, BigDecimal.ZERO, orderCreatedAt);
  }

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinOrdersTest.class.getResourceAsStream("/trade/example-orders-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final JustcoinOrder[] justcoinOrders = mapper.readValue(is, new JustcoinOrder[0].getClass());
    final JustcoinOrder deserializedOrder = justcoinOrders[0];

    assertThat(deserializedOrder).isEqualTo(justcoinOrder);
  }

  @Test
  public void testAdapter() {

    final LimitOrder limitOrder = JustcoinAdapters.adaptLimitOrder(justcoinOrder);

    assertThat(limitOrder.getId()).isEqualTo(id);
    assertThat(limitOrder.getLimitPrice()).isEqualTo(price);
    assertThat(limitOrder.getTimestamp()).isEqualTo(orderCreatedAt);
    assertThat(limitOrder.getTradableAmount()).isEqualTo(amount);
    assertThat(limitOrder.getCurrencyPair().baseSymbol).isEqualTo(tradableIdentifier);
    assertThat(limitOrder.getCurrencyPair().counterSymbol).isEqualTo(transactionCurrency);
    assertThat(limitOrder.getType()).isEqualTo(OrderType.ASK);
  }
}
