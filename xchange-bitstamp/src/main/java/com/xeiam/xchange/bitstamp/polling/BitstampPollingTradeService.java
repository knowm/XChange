package com.xeiam.xchange.bitstamp.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.api.BitStamp;
import com.xeiam.xchange.bitstamp.api.model.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.xeiam.xchange.dto.Order.OrderType.ASK;
import static com.xeiam.xchange.dto.Order.OrderType.BID;

/**
 * @author Matija Mazi <br/>
 * @created 1/1/13 6:40 PM
 */
public class BitstampPollingTradeService extends BasePollingExchangeService implements PollingTradeService {
  private BitStamp bitstamp;

  public BitstampPollingTradeService(ExchangeSpecification exchangeSpecification, BitStamp bitstampEndpoint) {
    super(exchangeSpecification);
    this.bitstamp = bitstampEndpoint;
  }

  @Override
  public OpenOrders getOpenOrders() {
    List<Order> openOrders = bitstamp.getOpenOrders(getUser(), getPwd());
    List<LimitOrder> orders = new ArrayList<LimitOrder>();
    for (Order oo : openOrders) {
      orders.add(new LimitOrder(oo.getType() == 0 ? BID : ASK, new BigDecimal(oo.getAmount()), "BTC", "USD", Integer.toString(oo.getId()), BigMoney.of(CurrencyUnit.USD, oo.getPrice())));
    }
    return new OpenOrders(orders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {
    throw new UnsupportedOperationException("Placing market orders not supported by Bitstamp API.");
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) {
    Order ord;
    if (limitOrder.getType() == BID) {
      ord = bitstamp.buy(getUser(), getPwd(), limitOrder.getTradableAmount().doubleValue(), limitOrder.getLimitPrice().getAmount().doubleValue());
    } else {
      ord = bitstamp.sell(getUser(), getPwd(), limitOrder.getTradableAmount().doubleValue(), limitOrder.getLimitPrice().getAmount().doubleValue());
    }
    return Integer.toString(ord.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) {
    return bitstamp.cancelOrder(getUser(), getPwd(), Integer.parseInt(orderId)).equals("true");
  }

  private String getPwd() {
    return exchangeSpecification.getPassword();
  }

  private String getUser() {
    return exchangeSpecification.getUserName();
  }
}
