package org.knowm.xchange.bitbns;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.bitbns.dto.PdaxOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public final class PdaxAdapters {

	public static List<Order> adaptOrders(List<PdaxOrder> pdaxOrder) {
		List<Order> orders = new ArrayList<>();
		for (PdaxOrder order : pdaxOrder) {
			orders.add(adaptOrder(order));
		}
		return orders;
	}

	private static Order adaptOrder(PdaxOrder openOrder) {
		Order order = null;
		OrderType orderType = adaptOrderType(openOrder.getData().getSide());
		CurrencyPair currencyPair = adaptCurrencyPair(openOrder.getData().getTradeCurrency()+openOrder.getData().getSettlementCurrency());
		if (openOrder.getData().getOrderType().equals("MARKET")) {
			order = new MarketOrder(orderType, 
					openOrder.getData().getAmount(), 
					currencyPair, 
					String.valueOf(openOrder.getData().getOrderId()),
					new Date(openOrder.getData().getTimestamp()),
					BigDecimal.ZERO,
					openOrder.getData().getTrades().get(0).getTradedCurrencyFillAmount(), 
					BigDecimal.ZERO,
					adaptOrderStatus(openOrder.getData().getOrderStatus()),
					"");
		}
		if (openOrder.getData().getOrderType().equals("LIMIT")) {
			order = new LimitOrder(orderType, 
					openOrder.getData().getAmount(), 
					currencyPair, 
					String.valueOf(openOrder.getData().getOrderId()),
					new Date(openOrder.getData().getTimestamp()),
					openOrder.getData().getLimitPrice(),
					BigDecimal.ZERO,
					openOrder.getData().getTrades().get(0).getTradedCurrencyFillAmount(), 
					BigDecimal.ZERO,
					adaptOrderStatus(openOrder.getData().getOrderStatus()),
					"");
		}
//		if (openOrder.isStop()) {
//			order = new StopOrder(orderType, openOrder.getAmount(), currencyPair, String.valueOf(openOrder.getId()),
//					openOrder.getCreatedAt(), openOrder.getStopPrice(), openOrder.getPrice(),
//					openOrder.getFieldCashAmount().divide(openOrder.getFieldAmount(), 8, BigDecimal.ROUND_DOWN),
//					openOrder.getFieldAmount(), openOrder.getFieldFees(), adaptOrderStatus(openOrder.getState()),
//					openOrder.getClOrdId(),
//					openOrder.getOperator().equals("lte") ? Intention.STOP_LOSS : Intention.TAKE_PROFIT);
//		}

//		if (openOrder.getData().getFieldAmount().compareTo(BigDecimal.ZERO) == 0) {
//			order.setAveragePrice(BigDecimal.ZERO);
//		} else {
//			order.setAveragePrice(
//					openOrder.getFieldCashAmount().divide(openOrder.getFieldAmount(), 8, BigDecimal.ROUND_DOWN));
//		}
		return order;
	}

	public static List<CurrencyPair> adaptCurrencyPairs(Collection<String> GeminiSymbol) {

		List<CurrencyPair> currencyPairs = new ArrayList<>();
		for (String symbol : GeminiSymbol) {
			currencyPairs.add(adaptCurrencyPair(symbol));
		}
		return currencyPairs;
	}

	public static CurrencyPair adaptCurrencyPair(String GeminiSymbol) {

		String tradableIdentifier = GeminiSymbol.substring(0, 3).toUpperCase();
		String transactionCurrency = GeminiSymbol.substring(3).toUpperCase();
		return new CurrencyPair(tradableIdentifier, transactionCurrency);
	}

	public static OrderType adaptOrderType(String orderType) {
		if (orderType.startsWith("buy")) {
			return OrderType.BID;
		}
		if (orderType.startsWith("sell")) {
			return OrderType.ASK;
		}
		return null;
	}

	private static OrderStatus adaptOrderStatus(String orderStatus) {

		if (orderStatus.equals("PENDING_SUBMISSION"))
			return OrderStatus.PENDING_NEW;

		if (orderStatus.equals("ACTIVE"))
			return OrderStatus.PARTIALLY_FILLED;

		if (orderStatus.equals("EXPIRED"))
			return OrderStatus.EXPIRED;

		if (orderStatus.equals("PARTIAL_FILL"))
			return OrderStatus.PARTIALLY_FILLED;

		if (orderStatus.equals("FULL_FILL"))
			return OrderStatus.FILLED;

		if (orderStatus.equals("NO_FILL"))
			return OrderStatus.CANCELED;
		

		throw new NotYetImplementedForExchangeException();
	}
}
