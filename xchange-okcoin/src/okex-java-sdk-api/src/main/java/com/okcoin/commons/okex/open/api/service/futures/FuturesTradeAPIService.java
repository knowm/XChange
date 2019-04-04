package com.okcoin.commons.okex.open.api.service.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.CancelOrders;
import com.okcoin.commons.okex.open.api.bean.futures.param.ClosePosition;
import com.okcoin.commons.okex.open.api.bean.futures.param.Order;
import com.okcoin.commons.okex.open.api.bean.futures.param.Orders;
import com.okcoin.commons.okex.open.api.bean.futures.result.OrderResult;

import java.util.List;

/**
 * Futures Trade API Service
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 18:52
 */
public interface FuturesTradeAPIService {

    /**
     * Get all of futures contract position list
     */
    JSONObject getPositions();

    /**
     * Get the futures contract product position
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     */
    JSONObject getInstrumentPosition(String instrumentId);

    /**
     * Get all of futures contract account list
     */
    JSONObject getAccounts();

    /**
     * Get the futures contract currency account
     *
     * @param currency {@link com.okcoin.commons.okex.open.api.enums.FuturesCurrenciesEnum}
     *                 eg: FuturesCurrenciesEnum.BTC.name()
     */
    JSONObject getAccountsByCurrency(String currency);

    /**
     * Get the futures contract currency ledger
     *
     * @param currency {@link com.okcoin.commons.okex.open.api.enums.FuturesCurrenciesEnum}
     *                 eg: FuturesCurrenciesEnum.BTC.name()
     */
    JSONArray getAccountsLedgerByCurrency(String currency);

    /**
     * Get the futures contract product holds
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     */
    JSONObject getAccountsHoldsByInstrumentId(String instrumentId);

    /**
     * Create a new order
     */
    OrderResult order(Order order);

    /**
     * Batch create new order.(Max of 5 orders are allowed per request))
     */
    JSONObject orders(Orders orders);

    /**
     * Cancel the order
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     * @param orderId   the order id provided by okex.com eg: 372238304216064
     */
    JSONObject cancelOrder(String instrumentId, long orderId);

    /**
     * Batch Cancel the orders of this product id
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     */
    JSONObject cancelOrders(String instrumentId, CancelOrders cancelOrders);

    /**
     * Get all of futures contract order list
     *
     * @param status   Order status: 0: waiting for transaction 1: 1: part of the deal 2: all transactions.
     * @param from    Paging content after requesting this id .
     * @param to     Paging content prior to requesting this id.
     * @param limit    Number of results per request. Maximum 100. (default 100)
     *                 {@link com.okcoin.commons.okex.open.api.bean.futures.CursorPageParams}
     * @return
     */
    JSONObject getOrders(String instrument_id, int status, int from, int to, int limit);

    /**
     * Get all of futures contract a order by order id
     *
     * @param instrumentId  eg: futures id
     */
    JSONObject getOrder(String instrumentId,long orderId);

    /**
     * Get all of futures contract transactions.
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     * @param orderId   the order id provided by okex.com eg: 372238304216064
     * @param from    Paging content after requesting this id .
     * @param to     Paging content prior to requesting this id.
     * @param limit     Number of results per request. Maximum 100. (default 100)
     *                  {@link com.okcoin.commons.okex.open.api.bean.futures.CursorPageParams}
     * @return
     */
    JSONArray getFills(String instrumentId, long orderId, int from, int to, int limit);

    /**
     * Get the futures LeverRate
     *
     * @param currency eg: btc
     */
    JSONObject getInstrumentLeverRate(String currency);

    /**
     * Change the futures LeverRate
     *
     * @param currency  eg: btc
     * @param instrumentId  eg: BTC-USD-0331
     * @param leverage  eg: 10/20
     * @param direction  eg: 1/2
     */
    JSONObject changeLevelRate( String currency,String instrumentId,String direction, int leverage);


    JSONObject changequancangLevelRate( String currency, int leverage);
}
