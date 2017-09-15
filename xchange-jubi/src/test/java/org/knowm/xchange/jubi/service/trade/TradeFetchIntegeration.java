package org.knowm.xchange.jubi.service.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.jubi.JubiExchange;
import org.knowm.xchange.jubi.dto.trade.JubiOrderStatus;
import org.knowm.xchange.jubi.service.JubiCancelOrderParams;
import org.knowm.xchange.jubi.service.JubiTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Created by Dzf on 2017/7/18.
 */
public class TradeFetchIntegeration {
  private final String apiKey = "your public api key";
  private final String secretKey = "your secret api key";

  @Test
  public void TradeFetchTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchangeWithApiKeys(JubiExchange.class.getName(), apiKey, secretKey);
    TradeService tradeService = exchange.getTradeService();
    UserTrades userTradesAll = tradeService.getTradeHistory(((JubiTradeServiceRaw) tradeService).createJubiTradeHistoryParams(new CurrencyPair("doge", "cny")));
    System.out.println(userTradesAll);
    UserTrades userTradesSince = tradeService.getTradeHistory(((JubiTradeServiceRaw) tradeService).createJubiTradeHistoryParams(new CurrencyPair("doge", "cny"), new Date(1483200000)));
    System.out.println(userTradesSince);
    OpenOrders openOrders = tradeService.getOpenOrders(((JubiTradeServiceRaw) tradeService).createJubiOpenOrdersParams(new CurrencyPair("doge", "cny")));
    System.out.println(openOrders);
    JubiOrderStatus orderStatus = ((JubiTradeServiceRaw) tradeService).getJubiOrderStatus(new BigDecimal(6860502), new CurrencyPair("doge", "cny"));
    System.out.println(orderStatus);
  }

  @Test
  public void OrderPlaceAndCancelTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchangeWithApiKeys(JubiExchange.class.getName(), apiKey, secretKey);
    TradeService tradeService = exchange.getTradeService();
    LimitOrder limitOrder = new LimitOrder(Order.OrderType.BID, new BigDecimal(2000000.0),
        new CurrencyPair("doge", "cny"), null, null, new BigDecimal(0.00001));
    String tradeId = "0";
    try {
      tradeId = tradeService.placeLimitOrder(limitOrder);
    } catch (ExchangeException ex) {
      System.out.println(ex);
    }
    System.out.println("Place Order Result: " + tradeId);
    boolean result = tradeService.cancelOrder(new JubiCancelOrderParams(new CurrencyPair("doge", "cny"), new BigDecimal(tradeId)));
    System.out.println("Cancel Order Result: " + result);
  }
}
