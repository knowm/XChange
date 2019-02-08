package org.knowm.xchange.dragonex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dragonex.dto.DragonResult;
import org.knowm.xchange.dragonex.dto.DragonexException;
import org.knowm.xchange.dragonex.dto.trade.DealHistory;
import org.knowm.xchange.dragonex.dto.trade.DealHistoryRequest;
import org.knowm.xchange.dragonex.dto.trade.OrderHistory;
import org.knowm.xchange.dragonex.dto.trade.OrderHistoryRequest;
import org.knowm.xchange.dragonex.dto.trade.OrderPlacement;
import org.knowm.xchange.dragonex.dto.trade.OrderReference;
import org.knowm.xchange.dragonex.dto.trade.UserOrder;

public class DragonexTradeServiceRaw extends DragonexBaseService {

  public DragonexTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public UserOrder orderBuy(String token, OrderPlacement orderPlacement)
      throws DragonexException, IOException {
    DragonResult<UserOrder> orderBuy =
        exchange
            .dragonexAuthenticated()
            .orderBuy(utcNow(), token, exchange.signatureCreator(), ContentSHA1, orderPlacement);
    return orderBuy.getResult();
  }

  public UserOrder orderSell(String token, OrderPlacement orderPlacement)
      throws DragonexException, IOException {
    DragonResult<UserOrder> orderSell =
        exchange
            .dragonexAuthenticated()
            .orderSell(utcNow(), token, exchange.signatureCreator(), ContentSHA1, orderPlacement);
    return orderSell.getResult();
  }

  public UserOrder orderCancel(String token, OrderReference ref)
      throws DragonexException, IOException {
    DragonResult<UserOrder> order =
        exchange
            .dragonexAuthenticated()
            .orderCancel(utcNow(), token, exchange.signatureCreator(), ContentSHA1, ref);
    return order.getResult();
  }

  public UserOrder orderDetail(String token, OrderReference ref)
      throws DragonexException, IOException {
    DragonResult<UserOrder> order =
        exchange
            .dragonexAuthenticated()
            .orderDetail(utcNow(), token, exchange.signatureCreator(), ContentSHA1, ref);
    return order.getResult();
  }

  public OrderHistory orderHistory(String token, OrderHistoryRequest req)
      throws DragonexException, IOException {
    DragonResult<OrderHistory> orderHistory =
        exchange
            .dragonexAuthenticated()
            .orderHistory(utcNow(), token, exchange.signatureCreator(), ContentSHA1, req);
    return orderHistory.getResult();
  }

  public DealHistory dealHistory(String token, DealHistoryRequest req)
      throws DragonexException, IOException {
    DragonResult<DealHistory> dealHistory =
        exchange
            .dragonexAuthenticated()
            .dealHistory(utcNow(), token, exchange.signatureCreator(), ContentSHA1, req);
    return dealHistory.getResult();
  }
}
