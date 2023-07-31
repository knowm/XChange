package org.knowm.xchange.ascendex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexException;
import org.knowm.xchange.ascendex.dto.enums.AccountCategory;
import org.knowm.xchange.ascendex.dto.trade.*;

import javax.ws.rs.QueryParam;

public class AscendexTradeServiceRaw extends AscendexBaseService {

    private static final String ACCOUNT_CASH_CATEGORY = "cash";

    public AscendexTradeServiceRaw(Exchange exchange) {
        super(exchange);
    }

    /**
     * default cash
     *
     * @param payload
     * @return
     * @throws AscendexException
     * @throws IOException
     */
    public AscendexOrderResponse placeAscendexOrder(AscendexPlaceOrderRequestPayload payload)
            throws AscendexException, IOException {

        return placeAscendexOrder(payload, AccountCategory.cash);
    }


    public AscendexOrderResponse placeAscendexOrder(AscendexPlaceOrderRequestPayload payload, AccountCategory accountCategory)
            throws AscendexException, IOException {

        return checkResult(
                ascendexAuthenticated.placeOrder(
                        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator,
                        accountCategory,
                        payload));
    }

    public AscendexOrderResponse cancelAscendexOrder(AscendexCancelOrderRequestPayload payload)
            throws AscendexException, IOException {
        return checkResult(
                ascendexAuthenticated.cancelOrder(
                        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator,
                        payload.getAccountCategory(),
                        payload));
    }

    public AscendexOrderResponse cancelAllAscendexOrdersBySymbol(AccountCategory accountCategory, String symbol)
            throws AscendexException, IOException {
        return checkResult(
                ascendexAuthenticated.cancelAllOrders(
                        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator,
                        accountCategory,
                        symbol == null ? null : symbol.toUpperCase()));
    }

    public List<AscendexOpenOrdersResponse> getAscendexOpenOrders(AccountCategory accountCategory, String symbol)
            throws AscendexException, IOException {

        return checkResult(
                ascendexAuthenticated.getOpenOrders(
                        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator,
                        accountCategory,
                        symbol == null ? symbol : symbol.toUpperCase()));
    }

    public AscendexOpenOrdersResponse getAscendexOrderById(AccountCategory accountCategory, String orderId)
            throws AscendexException, IOException {
        return checkResult(
                ascendexAuthenticated.getOrderById(
                        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator,
                        accountCategory,
                        orderId));
    }

    public List<AscendexOpenOrdersResponse> getAscendexUserTrades(AccountCategory accountCategory, String symbol)
            throws AscendexException, IOException {
        return getAscendexUserTrades(accountCategory,
                symbol,
                50,
                true);
    }

    public List<AscendexOpenOrdersResponse> getAscendexUserTrades(AccountCategory accountCategory, String symbol, Integer size, Boolean executedOnly)
            throws AscendexException, IOException {
        return checkResult(
                ascendexAuthenticated.getOrdersHistory(
                        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator,
                        accountCategory,
                        size,
                        symbol == null ? symbol : symbol.toUpperCase(),
                        executedOnly));
    }

    public List<AscendexHistoryOrderResponse> getAscendexOrdersHistoryV2(AccountCategory accountCategory)throws AscendexException, IOException {
        return getAscendexOrdersHistoryV2(accountCategory,null,null,null,null,null);
    }

    public List<AscendexHistoryOrderResponse> getAscendexOrdersHistoryV2(AccountCategory accountCategory,
                                                                         String symbol,
                                                                         Long startTime,
                                                                         Long endTime,
                                                                         Long seqNum,
                                                                         Integer limit)
            throws AscendexException, IOException {
        return checkResult(
                ascendexAuthenticated.getOrdersHistoryV2(
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator,
                        accountCategory,
                        symbol == null ? symbol : symbol.toUpperCase(),
                        startTime,
                        endTime,
                        seqNum,
                        limit

                )
        );
    }
}
