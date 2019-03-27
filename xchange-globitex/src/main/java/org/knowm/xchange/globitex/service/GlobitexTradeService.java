package org.knowm.xchange.globitex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.globitex.GlobitexAdapters;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

import java.io.IOException;

public class GlobitexTradeService extends GlobitexTradeServiceRaw implements TradeService {

    public GlobitexTradeService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        return GlobitexAdapters.adaptToUserTrades(
                getGlobitexUserTrades((TradeHistoryParamsAll) params),
                exchange.getExchangeMetaData().getCurrencies());
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return new TradeHistoryParamsAll();
    }


}
