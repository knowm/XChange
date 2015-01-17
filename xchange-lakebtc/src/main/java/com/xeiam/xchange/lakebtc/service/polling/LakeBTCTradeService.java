package com.xeiam.xchange.lakebtc.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCCancelResponse;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCOrderResponse;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.DefaultTradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by cristian.lucaci on 12/19/2014.
 */
public class LakeBTCTradeService extends LakeBTCTradeServiceRaw implements PollingTradeService {

    /**
     * Constructor
     *
     * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
     */
    public LakeBTCTradeService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> tonceFactory) {
        super(exchangeSpecification, tonceFactory);
    }

    @Override
    public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        final LakeBTCOrderResponse response = placeLakeBTCMarketOrder(marketOrder);
        return response.getId();
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        final LakeBTCOrderResponse response = placeLakeBTCLimitOrder(limitOrder);
        return response.getId();
    }

    @Override
    public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        final LakeBTCCancelResponse response = cancelLakeBTCOrder(orderId);
        return Boolean.valueOf(response.getResult());
    }

    @Override
    public UserTrades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return new DefaultTradeHistoryParamsTimeSpan();
    }

    @Override public Map<CurrencyPair, ? extends TradeServiceHelper> getTradeServiceHelperMap() throws NotAvailableFromExchangeException{
        throw new NotAvailableFromExchangeException();
    }
}
