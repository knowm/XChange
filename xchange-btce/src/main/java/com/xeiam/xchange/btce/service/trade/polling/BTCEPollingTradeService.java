package com.xeiam.xchange.btce.service.trade.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;

/**
* @author Matija Mazi <br/>
* @created 1/8/13 8:51 PM
*/
public class BTCEPollingTradeService implements PollingTradeService {

  public BTCEPollingTradeService(ExchangeSpecification exchangeSpecification) {
    // todo!

  }

  @Override
  public OpenOrders getOpenOrders() {
    // todo!
    return null;
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {
    // todo!
    return null;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {
    // todo!
    return null;
  }

  @Override
  public boolean cancelOrder(String orderId) {
    // todo!
    return false;
  }
}
