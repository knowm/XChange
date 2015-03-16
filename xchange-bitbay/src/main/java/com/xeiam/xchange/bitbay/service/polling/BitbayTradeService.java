package com.xeiam.xchange.bitbay.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitbay.BitbayAdapters;
import com.xeiam.xchange.bitbay.dto.BitbayBaseResponse;
import com.xeiam.xchange.bitbay.dto.trade.BitbayOrder;
import com.xeiam.xchange.bitbay.dto.trade.BitbayTradeResponse;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.util.List;

/**
 * @author yarkh
 */
public class BitbayTradeService extends BitbayTradeServiceRaw implements PollingTradeService {


    public BitbayTradeService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        List<BitbayOrder> orders = getOrders();
        return BitbayAdapters.adaptOpenOrders(orders);
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        return null;
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        BitbayTradeResponse response = placeBitbayOrder(limitOrder);
        if (response.getSuccess() == null || Integer.valueOf(response.getOrderId()) == 0) {
            throw new ExchangeException(response.getMessage());
        }
        return response.getOrderId();
    }

    @Override
    public boolean cancelOrder(String orderId) throws IOException {
        BitbayBaseResponse response = cancelBitbayOrder(orderId);
        if (response.getSuccess() == null) {
            throw new ExchangeException(response.getMessage());
        }
        return response.getSuccess() != null;
    }

    @Override
    public UserTrades getTradeHistory(Object... arguments) throws IOException {
        return null;
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
