package org.knowm.xchange.bitbns.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.dto.PdaxException;
import org.knowm.xchange.bitbns.dto.BitbnsOrderStatusResponse;
import org.knowm.xchange.bitbns.dto.OrderStatusBody;
import org.knowm.xchange.bitbns.dto.PadxNewOrderRequest;
import org.knowm.xchange.bitbns.dto.PdaxCancleOrderResponse;
import org.knowm.xchange.bitbns.dto.PdaxOrder;
import org.knowm.xchange.bitbns.dto.PdaxOrderPlaceStatusResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

public class PdaxTradeServiceRaw extends BitbnsBaseService {

	/**
	 * Constructor
	 *
	 * @param exchange
	 */
	public PdaxTradeServiceRaw(Exchange exchange) {
		super(exchange);
	}

	public PdaxOrderPlaceStatusResponse placeCoindcxLimitOrder(LimitOrder limitOrder) throws IOException {

		CurrencyPair currencyPair = limitOrder.getCurrencyPair();
		String traded_currency = currencyPair.base.toString();
		String settlement_currency = currencyPair.counter.toString();
		String type = limitOrder.getType().equals(Order.OrderType.BID) ? "BUY" : "SELL";
		// String orderType = GeminiOrderType.toString();

		PadxNewOrderRequest request = new PadxNewOrderRequest("LIMIT", type, traded_currency, settlement_currency,
				limitOrder.getOriginalAmount(), limitOrder.getLimitPrice(), "", false);
		// String reqBody = new ObjectMapper().writeValueAsString(request);
		try {
			PdaxOrderPlaceStatusResponse newOrder = pdax.newOrder(apiKey, signatureCreator, request);

			return newOrder;
		} catch (PdaxException e) {
			throw handleException(e);
		}
	}

	public List<PdaxOrder> getorderByOrderId(String... orderId) {
		List<PdaxOrder> orderList = new ArrayList<>();
		for (String order : orderId) {
			PdaxOrder pdaxOrder = pdax.getOrderByOrderId(apiKey, signatureCreator, order);
			orderList.add(pdaxOrder);
		}
		return orderList;
	}

	public PdaxCancleOrderResponse cancelOrderById(String orderId) {
		return pdax.cancelOrderByOrderId(apiKey, signatureCreator, orderId);
	}

}
