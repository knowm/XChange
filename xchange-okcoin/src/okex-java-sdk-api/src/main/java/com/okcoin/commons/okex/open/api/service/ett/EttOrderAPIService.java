package com.okcoin.commons.okex.open.api.service.ett;

import com.okcoin.commons.okex.open.api.bean.ett.result.CursorPager;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttCancelOrderResult;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttCreateOrderResult;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttOrder;

import java.math.BigDecimal;

/**
 * @author chuping.cui
 * @date 2018/7/4
 */
public interface EttOrderAPIService {

    /**
     * Create a ett order
     *
     * @param ett       ett name
     * @param type      order type. 1. usdt subscription 2. usdt redemption 3. underlying redemption
     * @param amount    subscription usdt size
     * @param size      redemption ett size
     * @param clientOid client order id
     * @return create order result
     */
    EttCreateOrderResult createOrder(String ett, Integer type, BigDecimal amount, BigDecimal size, String clientOid);

    /**
     * Cancel order
     *
     * @param orderId order id
     * @return cancel order result
     */
    EttCancelOrderResult cancelOrder(String orderId);

    /**
     * Get all of ett account order list
     *
     * @param ett    ett name
     * @param type   order type. 1. subscription 2. redemption
     * @param status order status. 0. all 1. waiting 2. done 3. canceled
     * @param before before and after cursors are available via response headers OK-BEFORE and OK-AFTER. Your requests should use these cursor values when making requests for pages after the initial
     *               request. {@link CursorPager}
     * @param after  before and after cursors are available via response headers OK-BEFORE and OK-AFTER. Your requests should use these cursor values when making requests for pages after the initial
     *               request. {@link CursorPager}
     * @param limit  Number of results per request.
     * @return order info list
     */
    CursorPager<EttOrder> getOrder(String ett, Integer type, Integer status, String before, String after, int limit);

    /**
     * Get the ett account order info
     *
     * @param orderId order id
     * @return order info
     */
    EttOrder getOrder(String orderId);

}
