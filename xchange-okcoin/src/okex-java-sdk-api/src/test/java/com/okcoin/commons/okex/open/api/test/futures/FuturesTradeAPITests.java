package com.okcoin.commons.okex.open.api.test.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.CancelOrders;
import com.okcoin.commons.okex.open.api.bean.futures.param.Order;
import com.okcoin.commons.okex.open.api.bean.futures.param.Orders;
import com.okcoin.commons.okex.open.api.bean.futures.param.OrdersItem;
import com.okcoin.commons.okex.open.api.bean.futures.result.OrderResult;
import com.okcoin.commons.okex.open.api.enums.FuturesCurrenciesEnum;
import com.okcoin.commons.okex.open.api.enums.FuturesTransactionTypeEnum;
import com.okcoin.commons.okex.open.api.service.futures.FuturesTradeAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesTradeAPIServiceImpl;
import com.okcoin.commons.okex.open.api.utils.OrderIdUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Futures trade api tests
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/13 18:21
 */
public class FuturesTradeAPITests extends FuturesAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(FuturesMarketAPITests.class);

    private FuturesTradeAPIService tradeAPIService;

    String currency = FuturesCurrenciesEnum.EOS.name();

    @Before
    public void before() {
        config = config();
        tradeAPIService = new FuturesTradeAPIServiceImpl(config);
    }

    @Test
    public void testGetPositions() {
        JSONObject positions = tradeAPIService.getPositions();
        toResultString(LOG, "Positions", positions);
    }

    @Test
    public void testGetInstrumentPosition() {
        JSONObject positions = tradeAPIService.getInstrumentPosition(instrument_id);
        toResultString(LOG, "Instrument-Position", positions);
    }

    @Test
    public void testGetAccounts() {
        JSONObject accounts = tradeAPIService.getAccounts();
        toResultString(LOG, "Accounts", accounts);
    }

    @Test
    public void testGetAccountsByCurrency() {
        JSONObject accountsByCurrency = tradeAPIService.getAccountsByCurrency(currency);
        toResultString(LOG, "Accounts-Currency", accountsByCurrency);
    }

    @Test
    public void testGetAccountsLedgerByCurrency() {
        JSONArray ledger = tradeAPIService.getAccountsLedgerByCurrency(currency);
        toResultString(LOG, "Ledger", ledger);
    }

    @Test
    public void testGetAccountsHoldsByinstrument_id() {
        JSONObject ledger = tradeAPIService.getAccountsHoldsByInstrumentId(instrument_id);
        toResultString(LOG, "Ledger", ledger);
    }

    @Test
    public void testOrder() {
        Order order = new Order();
        order.setinstrument_id(instrument_id);
        order.setClient_oid(OrderIdUtils.generator());
        order.setType(FuturesTransactionTypeEnum.OPEN_SHORT.code());
        order.setPrice(10000D);
        order.setSize(1);
        order.setMatch_price(0);
        order.setLeverage(10D);
        OrderResult result = tradeAPIService.order(order);
        toResultString(LOG, "New-Order", result);
    }

    @Test
    public void testOrders() {
        Orders orders = new Orders();
        orders.setinstrument_id(instrument_id);
        orders.setLeverage(10.0);

        List<OrdersItem> orders_data = new ArrayList<>();

        OrdersItem item1 = new OrdersItem();
        item1.setClient_oid(OrderIdUtils.generator());
        item1.setType(FuturesTransactionTypeEnum.OPEN_SHORT.code());
        item1.setPrice(0.647);
        item1.setSize(1);
        item1.setMatch_price(1);

        OrdersItem item2 = new OrdersItem();
        item2.setClient_oid(OrderIdUtils.generator());
        item2.setType(FuturesTransactionTypeEnum.OPEN_SHORT.code());
        item2.setPrice(0.646);
        item2.setSize(1);
        item2.setMatch_price(1);

        OrdersItem item3 = new OrdersItem();
        item3.setClient_oid(OrderIdUtils.generator());
        item3.setType(FuturesTransactionTypeEnum.OPEN_SHORT.code());
        item3.setPrice(0.646);
        item3.setSize(1);
        item3.setMatch_price(1);

        orders_data.add(item1);
        orders_data.add(item2);
        orders_data.add(item3);

        orders.setOrders_data(orders_data);

        JSONObject result = tradeAPIService.orders(orders);
        toResultString(LOG, "Batch-Orders", result);
    }

    @Test
    public void testGetOrders() {
        int status = 0;
        JSONObject result = tradeAPIService.getOrders(instrument_id, status, 1, 2, 3);
        toResultString(LOG, "Get-Orders", result);
    }

    @Test
    public void testGetOrder() {
        long orderId = 1692325459303424L;
        JSONObject result = tradeAPIService.getOrder(instrument_id, orderId);
        toResultString(LOG, "Get-Order", result);
    }

    @Test
    public void testCancelOrder() {
        long orderId = 1692325459303424L;
        JSONObject result = tradeAPIService.cancelOrder(instrument_id, orderId);
        toResultString(LOG, "Cancel-Instrument-Order", result);
    }

    @Test
    public void testCancelOrders() {
        CancelOrders cancelOrders = new CancelOrders();
        List<Long> list = new ArrayList<>();
        list.add(1685508253675520L);
        list.add(1685508253675520L);
        list.add(1707122518266880L);
        list.add(1707122518266880L);
        cancelOrders.setOrder_ids(list);
        JSONObject result = tradeAPIService.cancelOrders(instrument_id, cancelOrders);
        toResultString(LOG, "Cancel-Instrument-Orders", result);
    }

    @Test
    public void testGetFills() {
        long orderId = 1707122518266880L;
        JSONArray result = tradeAPIService.getFills(instrument_id, orderId, from, to, 2);
        toResultString(LOG, "Get-Fills", result);
    }

    @Test
    public void testGetLeverRate() {
        JSONObject jsonObject = tradeAPIService.getInstrumentLeverRate(currency);
        toResultString(LOG, "LeverRate", jsonObject);
    }

    @Test
    public void testChangelevelRate() {
        JSONObject jsonObject = tradeAPIService.changeLevelRate(currency, instrument_id, "short", 20);
        toResultString(LOG, "LeverRate", jsonObject);
    }

    @Test
    public void testquancangChangelevelRate() {
        JSONObject jsonObject = tradeAPIService.changequancangLevelRate(currency, 20);
        toResultString(LOG, "LeverRate", jsonObject);
    }


}
