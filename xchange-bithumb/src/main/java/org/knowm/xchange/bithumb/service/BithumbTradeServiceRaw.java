package org.knowm.xchange.bithumb.service;

import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.account.BithumbTransaction;
import org.knowm.xchange.bithumb.dto.trade.BithumbOpenOrdersParam;
import org.knowm.xchange.bithumb.dto.trade.BithumbTradeResponse;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import java.util.List;

public class BithumbTradeServiceRaw {

    public List<BithumbOrder> getBithumbOrders(BithumbOpenOrdersParam params) {
        throw new NotYetImplementedForExchangeException();
    }

    public BithumbTradeResponse placeBithumbMarketOrder(MarketOrder marketOrder) {
        throw new NotYetImplementedForExchangeException();
    }

    public BithumbTradeResponse placeBithumbLimitOrder(LimitOrder limitOrder) {
        throw new NotYetImplementedForExchangeException();
    }

    public boolean cancelBithumbOrder(String orderId) {
        throw new NotYetImplementedForExchangeException();
    }

    public List<BithumbTransaction> bithumbTransactions(BithumbTradeHistoryParams params) {
        throw new NotYetImplementedForExchangeException();
    }
}
