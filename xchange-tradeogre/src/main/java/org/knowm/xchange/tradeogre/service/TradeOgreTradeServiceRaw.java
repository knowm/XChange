package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.tradeogre.TradeOgreAdapters;
import org.knowm.xchange.tradeogre.TradeOgreExchange;
import org.knowm.xchange.tradeogre.dto.trade.TradeOgreOrder;
import org.knowm.xchange.tradeogre.dto.trade.TradeOgreTrade;

public class TradeOgreTradeServiceRaw extends TradeOgreBaseService {

  protected TradeOgreTradeServiceRaw(TradeOgreExchange exchange) {
    super(exchange);
  }

  public String placeOrder(LimitOrder limitOrder) throws IOException {
    String market = TradeOgreAdapters.adaptCurrencyPair((CurrencyPair) limitOrder.getInstrument());
    String price = limitOrder.getLimitPrice().toPlainString();
    String quantity = limitOrder.getRemainingAmount().toPlainString();
    if (Order.OrderType.BID.equals(limitOrder.getType())) {
      return tradeOgre.buy(base64UserPwd, market, quantity, price).getUuid();
    }
    return tradeOgre.sell(base64UserPwd, market, quantity, price).getUuid();
  }

  public boolean cancelOrder(String id) throws IOException {
    return tradeOgre.cancel(base64UserPwd, id).isSuccess();
  }

  public List<TradeOgreOrder> getOrders() throws IOException {
    return tradeOgre.getOrders(base64UserPwd, null);
  }

  public List<TradeOgreTrade> getTradeHistory() throws IOException {
    return tradeOgre.getTradeHistory(base64UserPwd, null);
  }
}
