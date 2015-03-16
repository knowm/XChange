package com.xeiam.xchange.cryptonit.v2.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptonit.v2.CryptonitAdapters;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

import java.io.IOException;

/**
 * Created by Yar.kh on 02/10/14.
 */
public class CryptonitTradeService extends CryptonitTradeServiceRaw implements PollingTradeService {

    public CryptonitTradeService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {

        CryptonitOrders placedOrders = getPlacedOrders();
        System.out.println(placedOrders);

        return new OpenOrders(CryptonitAdapters.adaptOrders(placedOrders));
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        return null;
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        CryptonitOrders orders = placeOrder(limitOrder);
        if (orders != null && orders.getOrders().size() == 1) {
            for (String s : orders.getOrders().keySet()) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean cancelOrder(String orderId) throws IOException {
        return cancelCryptonitOrder(orderId);
    }

    @Override
    public UserTrades getTradeHistory(Object... arguments) throws IOException {

        CryptonitOrders placedOrders = getFilledOrders();
        System.out.println(placedOrders);

        return new UserTrades(CryptonitAdapters.adaptFilledOrders(placedOrders), Trades.TradeSortType.SortByTimestamp);
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
