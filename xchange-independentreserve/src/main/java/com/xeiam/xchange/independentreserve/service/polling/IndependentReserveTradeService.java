package com.xeiam.xchange.independentreserve.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.independentreserve.IndependentReserveAdapters;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

import java.io.IOException;

/**
 * Author: Kamil Zbikowski
 * Date: 4/13/15
 */
public class IndependentReserveTradeService extends IndependentReserveTradeServiceRaw implements PollingTradeService{

    public IndependentReserveTradeService(Exchange exchange) {
        super(exchange);
    }


    /**
     * Assumes asking for the first 50 orders with the currency pair BTCUSD
     */
    @Override
    public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return IndependentReserveAdapters.adaptOpenOrders(getIndependentReserveOpenOrders(CurrencyPair.BTC_USD, 1));
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return independentReservePlaceLimitOrder(CurrencyPair.BTC_USD,
                limitOrder.getType(),
                limitOrder.getLimitPrice(),
                limitOrder.getTradableAmount());
    }



    @Override
    public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return independentReserveCancelOrder(orderId);
    }

    @Override
    public UserTrades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return IndependentReserveAdapters.adaptTradeHistory(getIndependentReserveTradeHistory(1));
    }


    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        return null;
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return null;
    }
}
