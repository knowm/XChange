package org.knowm.xchange.abucoins.service.trade;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.abucoins.AbucoinsExchange;
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
  }

}
