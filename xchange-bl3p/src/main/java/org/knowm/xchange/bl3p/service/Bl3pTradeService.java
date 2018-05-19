package org.knowm.xchange.bl3p.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bl3p.Bl3pAdapters;
import org.knowm.xchange.bl3p.Bl3pUtils;
import org.knowm.xchange.bl3p.dto.trade.Bl3pOpenOrders;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
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
        Bl3pOpenOrdersParams bl3pParams = (Bl3pOpenOrdersParams) params;

        Bl3pOpenOrders.Bl3pOpenOrder[] openOrders = this.bl3p.getOpenOrders(apiKey, signatureCreator, nonceFactory,
                Bl3pUtils.toPairString(bl3pParams.getCurrencyPair()))
                .getData()
                .getOrders();

        return Bl3pAdapters.adaptOpenOrders(bl3pParams.getCurrencyPair(), openOrders);
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        return null;
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        return null;
    }

    @Override
    public String placeStopOrder(StopOrder stopOrder) throws IOException {
        return null;
    }

    @Override
    public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
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
        return new Bl3pOpenOrdersParams();
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

    public static class Bl3pOpenOrdersParams implements OpenOrdersParams {

        private CurrencyPair currencyPair;

        public Bl3pOpenOrdersParams() {
            this.setCurrency(Currency.BTC);
        }

        public CurrencyPair getCurrencyPair() {
            return this.currencyPair;
        }

        public void setCurrency(Currency currency) {
            this.currencyPair = new CurrencyPair(currency, Currency.EUR);
        }

        @Override
        public boolean accept(LimitOrder order) {
            return false;
        }
    }
}
