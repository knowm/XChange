package org.knowm.xchange.bl3p.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bl3p.Bl3pAdapters;
import org.knowm.xchange.bl3p.Bl3pUtils;
import org.knowm.xchange.bl3p.dto.trade.Bl3pNewOrder;
import org.knowm.xchange.bl3p.dto.trade.Bl3pOpenOrders;
import org.knowm.xchange.bl3p.service.params.CancelOrderByIdAndCurrencyPairParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Collection;

public class Bl3pTradeService extends Bl3pBaseService implements TradeService {

    public Bl3pTradeService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        return getOpenOrders(createOpenOrdersParams());
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        DefaultOpenOrdersParamCurrencyPair bl3pParams = (DefaultOpenOrdersParamCurrencyPair) params;

        Bl3pOpenOrders.Bl3pOpenOrder[] openOrders = this.bl3p.getOpenOrders(apiKey, signatureCreator, nonceFactory,
                Bl3pUtils.toPairString(bl3pParams.getCurrencyPair()))
                .getData()
                .getOrders();

        return Bl3pAdapters.adaptOpenOrders(bl3pParams.getCurrencyPair(), openOrders);
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        Bl3pNewOrder bl3pOrder = this.bl3p.createMarketOrder(apiKey, signatureCreator, nonceFactory,
                Bl3pUtils.toPairString(marketOrder.getCurrencyPair()),
                Bl3pUtils.toBl3pOrderType(marketOrder.getType()),
                Bl3pUtils.toSatoshi(marketOrder.getOriginalAmount()),
                "EUR");

        return "" + bl3pOrder.getData().getOrderId();
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        Bl3pNewOrder bl3pOrder = this.bl3p.createLimitOrder(apiKey, signatureCreator, nonceFactory,
                Bl3pUtils.toPairString(limitOrder.getCurrencyPair()),
                Bl3pUtils.toBl3pOrderType(limitOrder.getType()),
                Bl3pUtils.toSatoshi(limitOrder.getOriginalAmount()),
                Bl3pUtils.toEuroshi(limitOrder.getLimitPrice()),
                "EUR");

        return "" + bl3pOrder.getData().getOrderId();
    }

    @Override
    public String placeStopOrder(StopOrder stopOrder) throws IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
        if (orderParams instanceof CancelOrderByIdAndCurrencyPairParams) {
            CancelOrderByIdAndCurrencyPairParams params = (CancelOrderByIdAndCurrencyPairParams) orderParams;

            this.bl3p.cancelOrder(apiKey, signatureCreator, nonceFactory,
                    Bl3pUtils.toPairString(params.getCurrencyPair()),
                    params.getOrderId());

            return true;
        }

        return false;
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        return null;
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return new TradeHistoryParamsAll();
    }

    @Override
    public OpenOrdersParams createOpenOrdersParams() {
        return new DefaultOpenOrdersParamCurrencyPair(CurrencyPair.BTC_EUR);
    }

    @Override
    public void verifyOrder(LimitOrder limitOrder) {
    }

    @Override
    public void verifyOrder(MarketOrder marketOrder) {
    }

    @Override
    public Collection<Order> getOrder(String... orderIds) throws IOException {
        return null;
    }
}
