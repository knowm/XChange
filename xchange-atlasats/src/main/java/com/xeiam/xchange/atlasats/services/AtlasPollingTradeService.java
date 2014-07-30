package com.xeiam.xchange.atlasats.services;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class AtlasPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

	public AtlasPollingTradeService(
			ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
		// TODO Auto-generated constructor stub
	}

	@Override
	public OpenOrders getOpenOrders() throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String placeMarketOrder(MarketOrder marketOrder)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String placeLimitOrder(LimitOrder limitOrder)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean cancelOrder(String orderId) throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Trades getTradeHistory(Object... arguments)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CurrencyPair> getExchangeSymbols() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
