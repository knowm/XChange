package org.xchange.kraken.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class KrakenPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

    protected KrakenPollingTradeService(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
        // TODO Auto-generated constructor stub
    }

    @Override
    public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
       throw new NotYetImplementedForExchangeException();
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();

    }

    @Override
    public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Trades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
        throw new NotYetImplementedForExchangeException();
    }

}
