package org.knowm.xchange.loyalbit.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.loyalbit.dto.account.LoyalbitBalance;
import org.knowm.xchange.loyalbit.dto.marketdata.LoyalbitOrderBook;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitOrder;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitSubmitOrderResponse;
import org.knowm.xchange.loyalbit.dto.trade.LoyalbitUserTransaction;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class LoyalbitDtoTest {

  private static ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBalance() throws Exception {
    final LoyalbitBalance balance = parse("balance.json", LoyalbitBalance.class);
    assertEquals(new BigDecimal("1283.24"), balance.getAvailableUsd());
    assertEquals(new BigDecimal("0.72530797"), balance.getAvailableBtc());
  }

  @Test
  public void testDeleteOrderSuccess() throws Exception {
    final LoyalbitBaseResponse response = parse("delete-order-success.json", LoyalbitBaseResponse.class);
    assertEquals("Order successfully deleted.", response.getMessage());
    assertEquals(1L, response.getStatus().longValue());
  }

  @Test
  public void testDeleteOrderError() throws Exception {
    try {
      parse("delete-order-error.json", LoyalbitBaseResponse.class);
      assertTrue("Expected an ExceptionalReturnContentException", false);
    } catch (JsonMappingException ignore) {
    }

    final LoyalbitException ex = parse("delete-order-error.json", LoyalbitException.class);
    assertTrue(ex.getMessage().contains("The order you tried to delete was executed before you requested the delete."));
    assertEquals(0L, ex.getStatus().longValue());
  }

  @Test
  public void testOpenOrders() throws Exception {
    final LoyalbitOrder[] response = parse("open-orders.json", LoyalbitOrder[].class);
    assertEquals(4, response.length);
    assertEquals(new BigDecimal("1.03000000"), response[2].getAmount());
    assertEquals(new BigDecimal("550.00"), response[2].getPrice());
    assertEquals(11056L, response[2].getId().longValue());
    assertEquals(1409700285953L, response[2].getMicrotime().getTime());
  }

  @Test
  public void testOrderBook() throws Exception {
    final LoyalbitOrderBook response = parse("orderbook.json", LoyalbitOrderBook.class);
    assertEquals(7, response.getBids().size());
    assertEquals(7, response.getAsks().size());
    for (List<BigDecimal> item : response.getAsks())
      assertEquals(2, item.size());
    for (List<BigDecimal> item : response.getBids())
      assertEquals(2, item.size());

    assertEquals(new BigDecimal("431.30"), response.getAsks().get(3).get(0));
    assertEquals(new BigDecimal("0.20442012"), response.getAsks().get(3).get(1));

    assertEquals(new BigDecimal("345.00"), response.getBids().get(3).get(0));
    assertEquals(new BigDecimal("0.30000000"), response.getBids().get(3).get(1));
  }

  @Test
  public void testSubmitOrderSuccess() throws Exception {
    final LoyalbitSubmitOrderResponse response = parse("submit-order-success.json", LoyalbitSubmitOrderResponse.class);

    assertEquals(1, response.getStatus().intValue());
    assertEquals("Order successfully submitted", response.getMessage());
    assertEquals(19, response.getOrderId().intValue());
  }

  @Test
  public void testSubmitOrderError() throws Exception {
    try {
      parse("submit-order-error.json", LoyalbitSubmitOrderResponse.class);
      assertTrue("Expected an ExceptionalReturnContentException", false);
    } catch (JsonMappingException ignore) {
    }

    final LoyalbitException ex = parse("submit-order-error.json", LoyalbitException.class);
    assertTrue(ex.getMessage().contains("Not enough USD funds."));
    assertEquals(0L, ex.getStatus().longValue());
  }

  @Test
  public void testTransactions() throws Exception {
    final LoyalbitUserTransaction[] response = parse("transactions.json", LoyalbitUserTransaction[].class);

    assertEquals(2, response.length);

    assertEquals(10872, response[0].getId().longValue());
    assertEquals(20431, response[0].getOrderId().longValue());
    assertEquals(1409529600475L, response[0].getMicrotime().getTime());
    assertEquals(LoyalbitOrder.Type.bid, response[0].getType());
    assertEquals(new BigDecimal("1.00000000"), response[0].getAmount());
    assertEquals(new BigDecimal("500.00"), response[0].getPrice());
    assertEquals(new BigDecimal("500.00"), response[0].getSubtotal());
    assertEquals(new BigDecimal("0.0040"), response[0].getFee());
    assertEquals(new BigDecimal("2.00"), response[0].getFeeUSD());
    assertEquals(new BigDecimal("502.00"), response[0].getTotal());

    assertEquals(11935, response[1].getId().longValue());
    assertEquals(22351, response[1].getOrderId().longValue());
    assertEquals(1409700874365L, response[1].getMicrotime().getTime());
    assertEquals(LoyalbitOrder.Type.ask, response[1].getType());
    assertEquals(new BigDecimal("1.00000000"), response[1].getAmount());
    assertEquals(new BigDecimal("550.00"), response[1].getPrice());
    assertEquals(new BigDecimal("550.00"), response[1].getSubtotal());
    assertEquals(new BigDecimal("0.0040"), response[1].getFee());
    assertEquals(new BigDecimal("2.20"), response[1].getFeeUSD());
    assertEquals(new BigDecimal("547.80"), response[1].getTotal());
  }

  private static <E> E parse(String filename, Class<E> type) throws java.io.IOException {
    InputStream is = LoyalbitDtoTest.class.getResourceAsStream("/" + filename);
    return mapper.readValue(is, type);
  }
}