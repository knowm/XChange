package org.knowm.xchange.ccex.service.pooling;

import static org.knowm.xchange.service.polling.trade.params.TradeHistoryParamsZero.PARAMS_ZERO;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.CCEXAdapters;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;

public class CCEXTradeService extends CCEXTradeServiceRaw implements PollingTradeService{

	public CCEXTradeService(Exchange exchange) {
		super(exchange);
	}

	@Override
	public OpenOrders getOpenOrders() throws IOException {
		
		return new OpenOrders(CCEXAdapters.adaptOpenOrders(getCCEXOpenOrders()));
	}

	@Override
	public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotAvailableFromExchangeException();
	}

	@Override
	public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
		String id = placeCCEXLimitOrder(limitOrder);

	    return id;
	}

	@Override
	public boolean cancelOrder(String orderId) throws IOException {
		return cancelCCEXLimitOrder(orderId);
	}

	@Override
	public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
		return new UserTrades(CCEXAdapters.adaptUserTrades(getCCEXTradeHistory()), TradeSortType.SortByTimestamp);
	}

	@Override
	public TradeHistoryParams createTradeHistoryParams() {
		return PARAMS_ZERO;
	}

	@Override
	public Collection<Order> getOrder(String... orderIds) throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotYetImplementedForExchangeException();
	}

}