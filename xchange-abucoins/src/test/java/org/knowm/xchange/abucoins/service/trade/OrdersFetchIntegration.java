package org.knowm.xchange.abucoins.service.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.abucoins.AbucoinsExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;

/**
 * @author bryant_harris
 */
public class OrdersFetchIntegration {

  @Test
  public void accountsFetchTest() throws Exception {
    ExchangeSpecification exSpec = new AbucoinsExchange().getDefaultExchangeSpecification();
    // TODO Don't hardcode within code.
    //exSpec.setPassword("--Passphrase--");
    //exSpec.setApiKey("--Key--");
    //exSpec.setSecretKey("--Secret--");
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange( exSpec );
    TradeService tradeService = exchange.getTradeService();
    
    OpenOrders openOrders = tradeService.getOpenOrders();
    assertThat(openOrders).isNotNull();
    System.out.println(openOrders);
    
    String orderID = tradeService.placeLimitOrder( new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
                                                   .limitPrice(new BigDecimal("500"))
                                                   .originalAmount(new BigDecimal("0.75"))
                                                   .build());
    
    Collection<Order> orders = tradeService.getOrder(orderID);
    for ( Order order : orders )
      tradeService.cancelOrder(order.getId());
  }

}
