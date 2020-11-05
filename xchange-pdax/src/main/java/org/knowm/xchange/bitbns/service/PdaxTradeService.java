package org.knowm.xchange.bitbns.service;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.PdaxAdapters;
import org.knowm.xchange.bitbns.dto.BitbnsLimitOrder;
import org.knowm.xchange.bitbns.dto.PdaxCancleOrderResponse;
import org.knowm.xchange.bitbns.dto.PdaxException;
import org.knowm.xchange.bitbns.dto.PdaxOrderPlaceStatusResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class PdaxTradeService extends PdaxTradeServiceRaw implements TradeService {

  public PdaxTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

	 PdaxOrderPlaceStatusResponse newOrder = placeCoindcxLimitOrder(limitOrder);

    // The return value contains details of any trades that have been immediately executed as a
    // result
    // of this order. Make these available to the application if it has provided a GeminiLimitOrder.
    if (limitOrder instanceof BitbnsLimitOrder) {
      BitbnsLimitOrder raw = (BitbnsLimitOrder) limitOrder;
      raw.setResponse(newOrder);
    }

    return String.valueOf(newOrder.getData().getOrderId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
	  PdaxCancleOrderResponse orderResponse=  cancelOrderById(orderId);
	  if(orderResponse.getData().getStatus().equals("CANCEL_REQUEST_SUBMITTED")){
//		  Cancel request is received and order should be cancelled
		  return true;
	  }else if(orderResponse.getData().getStatus().equals("ORDER_CANCEL_WRONG_STATE")){
//		  Order cannot be cancelled. This may be caused by the order being matched/traded already
		  return false;
	  }else if(orderResponse.getData().getStatus().equals("ORDER_NOT_FOUND")){
		  throw new PdaxException("Order does not exist");
	  }
    return false;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }
  
  @Override
	public Collection<Order> getOrder(String... orderIds) throws IOException {
	  	return PdaxAdapters.adaptOrders(getorderByOrderId(orderIds));
	}

  
}
