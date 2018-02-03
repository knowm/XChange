package org.knowm.xchange.gdax.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

import java.io.IOException;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.gdax.GDAX;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.dto.GDAXException;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Tests for {@link GDAXTradeServiceRaw}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GDAXAdapters.class)
public class GDAXTradeServiceTest {

  private static final String ORDER_ID = "Whatevs";

  @Mock private Exchange mockExchange;
  @Mock private ExchangeSpecification mockExchangeSpecification;
  @Mock private GDAX mockGdax;
  @Mock private GDAXOrder mockGdaxOrder;
  @Mock private Order mockOrder;

  private TradeService service;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    when(mockExchange.getExchangeSpecification()).thenReturn(mockExchangeSpecification);
    service = new GDAXTradeService(mockExchange) {
      @Override
      protected GDAX gdax() {
        return mockGdax;
      }
    };
  }

  /**
   * See <a href="https://docs.gdax.com/#get-an-order">The GDAX API</a>. 404 is returned if an order was cancelled.
   * @throws IOException
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testGetOrder404() throws IOException {

    final GDAXException fourOhFour = new GDAXException("Not found");
    fourOhFour.setHttpStatusCode(404);

    when(mockGdax.getOrder(eq(ORDER_ID), anyString(), any(ParamsDigest.class),
        any(SynchronizedValueFactory.class), anyString())).thenThrow(fourOhFour);

    final Collection<Order> orders = service.getOrder(ORDER_ID);

    assertEquals(1, orders.size());
    final Order order = orders.iterator().next();

    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.CANCELED);
    assertThat(order.getId()).isEqualTo(ORDER_ID);
  }


  /**
   * Make sure we handle non 404 cases correctly.
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testGetOrder200() throws IOException {

    PowerMockito.mockStatic(GDAXAdapters.class);

    when(mockGdax.getOrder(eq(ORDER_ID), anyString(), any(ParamsDigest.class),
        any(SynchronizedValueFactory.class), anyString())).thenReturn(mockGdaxOrder);

    PowerMockito.when(GDAXAdapters.adaptOrder(mockGdaxOrder)).thenReturn(mockOrder);

    final Collection<Order> orders = service.getOrder(ORDER_ID);

    assertEquals(1, orders.size());
    Assert.assertSame(mockOrder, orders.iterator().next());
  }
}