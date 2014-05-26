package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOrder;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class HitbtcTradeService extends HitbtcTradeServiceRaw implements PollingTradeService {
	public HitbtcTradeService(ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
	}

	@Override
	public OpenOrders getOpenOrders() throws ExchangeException,
	NotAvailableFromExchangeException,
	NotYetImplementedForExchangeException, IOException {

		HitbtcOrder[] openOrdersRaw = getOpenOrdersRaw();
		return HitbtcAdapters.adaptOpenOrders(openOrdersRaw);
	}

	@Override
	public String placeMarketOrder(MarketOrder marketOrder)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {

		HitbtcExecutionReport placeMarketOrderRaw = placeMarketOrderRaw(marketOrder);
		return placeMarketOrderRaw.getClientOrderId();
	}

	@Override
	public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException,
	NotAvailableFromExchangeException,
	NotYetImplementedForExchangeException, IOException {

		HitbtcExecutionReport placeLimitOrderRaw = placeLimitOrderRaw(limitOrder);
		return placeLimitOrderRaw.getClientOrderId();
	}

	@Override
	public boolean cancelOrder(String orderId) throws ExchangeException,
	NotAvailableFromExchangeException,
	NotYetImplementedForExchangeException, IOException {
		
		HitbtcExecutionReportResponse cancelOrderRaw = cancelOrderRaw(orderId);
		return cancelOrderRaw.getCancelReject() == null && cancelOrderRaw.getExecutionReport() != null;
	}

	@Override
	public Trades getTradeHistory(Object... arguments) throws ExchangeException,
	NotAvailableFromExchangeException,
	NotYetImplementedForExchangeException, IOException {

		 HitbtcOwnTrade[] tradeHistoryRaw = getTradeHistoryRaw(arguments);
		 return HitbtcAdapters.adaptTradeHistory(tradeHistoryRaw);
	}
}
