package org.known.xchange.acx.service.trade;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.known.xchange.acx.AcxApi;
import org.known.xchange.acx.AcxMapper;
import org.known.xchange.acx.AcxSignatureCreator;
import org.known.xchange.acx.dto.marketdata.AcxOrder;
import org.known.xchange.acx.utils.ArgUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static org.known.xchange.acx.utils.AcxUtils.getAcxMarket;

public class AcxTradeService implements TradeService {
    private final AcxApi api;
    private final AcxMapper mapper;
    private final AcxSignatureCreator signatureCreator;
    private final String accessKey;

    public AcxTradeService(AcxApi api, AcxMapper mapper, AcxSignatureCreator signatureCreator, String accessKey) {
        this.api = api;
        this.mapper = mapper;
        this.signatureCreator = signatureCreator;
        this.accessKey = accessKey;
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        return getOpenOrders(createOpenOrdersParams());
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        long tonce = System.currentTimeMillis();
        OpenOrdersParamCurrencyPair param = ArgUtils.tryCast(params, OpenOrdersParamCurrencyPair.class);
        CurrencyPair currencyPair = param.getCurrencyPair();
        List<AcxOrder> orders = api.getOrders(accessKey, tonce, getAcxMarket(currencyPair), signatureCreator);
        return new OpenOrders(mapper.mapOrders(currencyPair, orders));
    }

    @Override
    public DefaultOpenOrdersParamCurrencyPair createOpenOrdersParams() {
        return new DefaultOpenOrdersParamCurrencyPair();
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        long tonce = System.currentTimeMillis();
        String market = getAcxMarket(limitOrder.getCurrencyPair());
        String side = mapper.getOrderType(limitOrder.getType());
        String volume = limitOrder.getOriginalAmount().setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
        String price = limitOrder.getLimitPrice().setScale(4, BigDecimal.ROUND_DOWN).toPlainString();
        AcxOrder order = api.createOrder(accessKey, tonce, market, side, volume, price, "limit", signatureCreator);
        return order.id;
    }

    @Override
    public String placeStopOrder(StopOrder stopOrder) throws IOException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public boolean cancelOrder(String orderId) throws IOException {
        long tonce = System.currentTimeMillis();
        AcxOrder order = api.cancelOrder(accessKey, tonce, orderId, signatureCreator);
        return order != null;
    }

    @Override
    public boolean cancelOrder(CancelOrderParams orderParams) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public Collection<Order> getOrder(String... orderIds) {
        throw new NotAvailableFromExchangeException();
    }

    // not available

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public void verifyOrder(LimitOrder limitOrder) {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public void verifyOrder(MarketOrder marketOrder) {
        throw new NotAvailableFromExchangeException();
    }
}
