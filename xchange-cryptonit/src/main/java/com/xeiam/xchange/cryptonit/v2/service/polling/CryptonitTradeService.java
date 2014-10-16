package com.xeiam.xchange.cryptonit.v2.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.cryptonit.v2.CryptonitAdapters;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

import java.io.IOException;

/**
 * Created by Yar.kh on 02/10/14.
 */
public class CryptonitTradeService extends CryptonitTradeServiceRaw implements PollingTradeService {

    public CryptonitTradeService(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
    }

    @Override
    public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

        CryptonitOrders placedOrders = getPlacedOrders();
        System.out.println(placedOrders);

        return new OpenOrders(CryptonitAdapters.adaptOrders(placedOrders));
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        CryptonitOrders orders = placeOrder(limitOrder);
        if (orders != null && orders.getOrders().size() == 1) {
            for (String s : orders.getOrders().keySet()) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return cancelCryptonitOrder(orderId);
    }

    @Override
    public Trades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

        CryptonitOrders placedOrders = getFilledOrders();
        System.out.println(placedOrders);

        return new Trades(CryptonitAdapters.adaptFilledOrders(placedOrders), Trades.TradeSortType.SortByTimestamp);
    }
}
