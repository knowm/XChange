package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.PpBatchOrder;
import com.okcoin.commons.okex.open.api.bean.swap.param.PpCancelOrderVO;
import com.okcoin.commons.okex.open.api.bean.swap.param.PpOrder;
import com.okcoin.commons.okex.open.api.bean.swap.param.PpOrders;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiCancelOrderVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiOrderResultVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiOrderVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.OrderCancelResult;
import com.okcoin.commons.okex.open.api.service.swap.SwapTradeAPIService;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapTradeAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class SwapTradeTest extends SwapBaseTest {
    private SwapTradeAPIService tradeAPIService;

    @Before
    public void before() {
        config = config();
        tradeAPIService = new SwapTradeAPIServiceImpl(config);
    }

    @Test
    public void order() {
        PpOrder ppOrder = new PpOrder(null, "1", "1", "1", "", "BTC-USD-SWAP");
        String jsonObject = tradeAPIService.order(ppOrder);
        ApiOrderVO apiOrderVO = JSONObject.parseObject(jsonObject, ApiOrderVO.class);
        System.out.println("success");
        System.out.println(apiOrderVO.getError_code());
    }


    @Test
    public void batchOrder() {
        List<PpBatchOrder> list = new LinkedList<>();
        list.add(new PpBatchOrder("123qqqq", "2", "1", "0", "5"));
        list.add(new PpBatchOrder("qwerry", "1", "2", "0", "12"));
        PpOrders ppOrders = new PpOrders();
        ppOrders.setInstrument_id(instrument_id);
        ppOrders.setOrder_data(list);
        String jsonObject = tradeAPIService.orders(ppOrders);
        ApiOrderResultVO apiOrderResultVO = JSONObject.parseObject(jsonObject, ApiOrderResultVO.class);
        System.out.println("success");
        apiOrderResultVO.getOrder_info().forEach(vo -> System.out.println(vo.getClient_oid()));
    }

    @Test
    public void cancelOrder() {
        String jsonObject = tradeAPIService.cancelOrder(instrument_id, "64-5e-30bc50302-0");
        ApiCancelOrderVO apiCancelOrderVO = JSONObject.parseObject(jsonObject, ApiCancelOrderVO.class);
        System.out.println("success");
        System.out.println(apiCancelOrderVO.getOrder_id());
    }

    @Test
    public void batchCancelOrder() {
        PpCancelOrderVO ppCancelOrderVO = new PpCancelOrderVO();
        ppCancelOrderVO.getIds().add("");
        System.out.println(JSONObject.toJSONString(ppCancelOrderVO));
        String jsonObject = tradeAPIService.cancelOrders(instrument_id, ppCancelOrderVO);
        OrderCancelResult orderCancelResult = JSONObject.parseObject(jsonObject, OrderCancelResult.class);
        System.out.println("success");
        System.out.println(orderCancelResult.getInstrument_id());
    }
}
