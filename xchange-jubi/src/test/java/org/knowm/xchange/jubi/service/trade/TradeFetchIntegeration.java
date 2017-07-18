package org.knowm.xchange.jubi.service.trade;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.jubi.JubiExchange;
import org.knowm.xchange.jubi.dto.trade.JubiOrderStatus;
import org.knowm.xchange.jubi.service.JubiTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Dzf on 2017/7/18.
 */
public class TradeFetchIntegeration {
  @Test
  public void TradeFetchTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchangeWithApiKeys(JubiExchange.class.getName(),
            "your pubic api key", "your secret api key");
    TradeService tradeService = exchange.getTradeService();
    UserTrades userTradesAll = tradeService.getTradeHistory(((JubiTradeServiceRaw)tradeService).createJubiTradeHistoryParams(new CurrencyPair("doge", "cny")));
    System.out.println(userTradesAll);
    UserTrades userTradesSince = tradeService.getTradeHistory(((JubiTradeServiceRaw)tradeService).createJubiTradeHistoryParams(new CurrencyPair("doge", "cny"), new Date(1483200000)));
    System.out.println(userTradesSince);
    OpenOrders openOrders = tradeService.getOpenOrders(((JubiTradeServiceRaw)tradeService).createJubiOpenOrdersParams(new CurrencyPair("doge", "cny")));
    System.out.println(openOrders);
    JubiOrderStatus orderStatus = ((JubiTradeServiceRaw) tradeService).getOrderStatus(new BigDecimal(6860502), new CurrencyPair("doge", "cny"));
    System.out.println(orderStatus);
  }
}
