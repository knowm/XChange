package com.xeiam.xchange.independentreserve.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.independentreserve.IndependentReserveAuthenticated;
import com.xeiam.xchange.independentreserve.dto.trade.*;
import com.xeiam.xchange.independentreserve.service.IndependentReserveDigest;
import com.xeiam.xchange.independentreserve.util.ExchangeEndpoint;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Author: Kamil Zbikowski
 * Date: 4/13/15
 */
public class IndependentReserveTradeServiceRaw  extends IndependentReserveBasePollingService {
    private final String TRADE_HISTORY_PAGE_SIZE = "50";
    private final IndependentReserveDigest signatureCreator;
    private final IndependentReserveAuthenticated independentReserveAuthenticated;

    /**
     * Constructor
     *
     * @param exchange
     */
    protected IndependentReserveTradeServiceRaw(Exchange exchange) {
        super(exchange);

        this.independentReserveAuthenticated = RestProxyFactory.createProxy(IndependentReserveAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
        this.signatureCreator = IndependentReserveDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
                exchange.getExchangeSpecification().getApiKey(), exchange.getExchangeSpecification().getSslUri());
    }


    /**
     *
     * @param currencyPair - currency pair
     * @param pageNumber -
     * @return
     * @throws IOException
     */
    public IndependentReserveOpenOrdersResponse getIndependentReserveOpenOrders(CurrencyPair currencyPair, Integer pageNumber) throws IOException {
        if(pageNumber <= 0){
            throw new IllegalArgumentException("Page number in IndependentReserve should be positive.");
        }
        Long nonce = exchange.getNonceFactory().createValue();
        String apiKey = exchange.getExchangeSpecification().getApiKey();
        IndependentReserveOpenOrderRequest independentReserveOpenOrderRequest = new IndependentReserveOpenOrderRequest(apiKey,
                nonce,
                currencyPair.baseSymbol,
                currencyPair.counterSymbol,
                pageNumber.toString(),
                TRADE_HISTORY_PAGE_SIZE);

        independentReserveOpenOrderRequest.setSignature(signatureCreator.digestParamsToString(ExchangeEndpoint.GET_OPEN_ORDERS,
                nonce, independentReserveOpenOrderRequest.getParameters()));

        IndependentReserveOpenOrdersResponse openOrders = independentReserveAuthenticated.getOpenOrders(independentReserveOpenOrderRequest);

        return openOrders;
    }

    public String independentReservePlaceLimitOrder(CurrencyPair currencyPair,
                                                    Order.OrderType type,
                                                    BigDecimal limitPrice,
                                                    BigDecimal tradableAmount) throws IOException {
        Long nonce = exchange.getNonceFactory().createValue();
        String apiKey = exchange.getExchangeSpecification().getApiKey();

        String orderType = null;
        if(type == Order.OrderType.ASK){
            orderType = "LimitOffer";
        }else if(type == Order.OrderType.BID){
            orderType = "LimitBid";
        }

        IndependentReservePlaceLimitOrderRequest independentReservePlaceLimitOrderRequest = new IndependentReservePlaceLimitOrderRequest(apiKey,
                nonce, currencyPair.baseSymbol, currencyPair.counterSymbol, orderType, limitPrice.toString(), tradableAmount.toString());
        independentReservePlaceLimitOrderRequest.setSignature(signatureCreator.digestParamsToString(ExchangeEndpoint.PLACE_LIMIT_ORDER,
                nonce, independentReservePlaceLimitOrderRequest.getParameters()));

        IndependentReservePlaceLimitOrderResponse independentReservePlaceLimitOrderResponse = independentReserveAuthenticated.placeLimitOrder(independentReservePlaceLimitOrderRequest);

        return independentReservePlaceLimitOrderResponse.getOrderGuid();
    }

    public boolean independentReserveCancelOrder(String orderId) throws IOException {
        Long nonce = exchange.getNonceFactory().createValue();
        String apiKey = exchange.getExchangeSpecification().getApiKey();

        IndependentReserveCancelOrderRequest independentReserveCancelOrderRequest = new IndependentReserveCancelOrderRequest(apiKey,
                nonce, orderId);

        independentReserveCancelOrderRequest.setSignature(signatureCreator.digestParamsToString(ExchangeEndpoint.CANCEL_ORDER,
                nonce, independentReserveCancelOrderRequest.getParameters()));

        IndependentReserveCancelOrderResponse independentReserveCancelOrderResponse = independentReserveAuthenticated.cancelOrder(independentReserveCancelOrderRequest);

        if(independentReserveCancelOrderResponse.getStatus() != null){
            return independentReserveCancelOrderResponse.getStatus().equals("Cancelled") || independentReserveCancelOrderResponse.getStatus().equals("PartiallyFilledAndCancelled")
                    || independentReserveCancelOrderResponse.getStatus().equals("Expired") || independentReserveCancelOrderResponse.getStatus().equals("Expired") ;
        }else{
            return false;
        }
    }

    public IndependentReserveTradeHistoryResponse getIndependentReserveTradeHistory(Integer pageNumber) throws IOException {
        if(pageNumber <= 0){
            throw new IllegalArgumentException("Page number in IndependentReserve should be positive.");
        }
        Long nonce = exchange.getNonceFactory().createValue();
        String apiKey = exchange.getExchangeSpecification().getApiKey();

        IndependentReserveTradeHistoryRequest independentReserveTradeHistoryRequest = new IndependentReserveTradeHistoryRequest(apiKey,
                nonce,
                pageNumber.toString(),
                TRADE_HISTORY_PAGE_SIZE);

        independentReserveTradeHistoryRequest.setSignature(signatureCreator.digestParamsToString(ExchangeEndpoint.GET_TRADES,
                nonce, independentReserveTradeHistoryRequest.getParameters()));

        IndependentReserveTradeHistoryResponse trades = independentReserveAuthenticated.getTradeHistory(independentReserveTradeHistoryRequest);

        return trades;
    }

}
