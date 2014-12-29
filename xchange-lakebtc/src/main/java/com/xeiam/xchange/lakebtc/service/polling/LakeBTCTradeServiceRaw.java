package com.xeiam.xchange.lakebtc.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.lakebtc.LakeBTCAuthenticated;
import com.xeiam.xchange.lakebtc.LakeBTCUtil;
import com.xeiam.xchange.lakebtc.dto.trade.*;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by cristian.lucaci on 12/19/2014.
 */
public class LakeBTCTradeServiceRaw extends LakeBTCBasePollingService<LakeBTCAuthenticated> {


    /**
     * Constructor
     *
     * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
     */
    public LakeBTCTradeServiceRaw(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> tonceFactory) {
        super(LakeBTCAuthenticated.class, exchangeSpecification, tonceFactory);
    }

    public LakeBTCOrderResponse placeLakeBTCMarketOrder(MarketOrder marketOrder) throws IOException {
        String pair = LakeBTCUtil.toPairString(marketOrder.getCurrencyPair());
        try {
            LakeBTCOrderResponse newOrder = null;
            switch (marketOrder.getType()) {
                case BID:
                    newOrder = btcLakeBTC.placeBuyOrder(signatureCreator, LakeBTCUtil.getNonce(),
                            //unit price, amount, currency concatenated by commas
                            new LakeBTCBuyOrderRequest(String.format("\"%s,%s,%s\"", "0", marketOrder.getTradableAmount().toString(), pair)));
                    break;
                case ASK:
                    newOrder = btcLakeBTC.placeSellOrder(signatureCreator, LakeBTCUtil.getNonce(),
                            //unit price, amount, currency concatenated by commas
                            new LakeBTCSellOrderRequest(String.format("\"%s,%s,%s\"", "0", marketOrder.getTradableAmount().toString(), pair)));
                    break;
            }
            return newOrder;
        } catch (IOException e) {
            throw new ExchangeException("LakeBTC returned an error: " + e.getMessage());
        }
    }

    public LakeBTCOrderResponse placeLakeBTCLimitOrder(LimitOrder limitOrder) throws IOException {
        String pair = LakeBTCUtil.toPairString(limitOrder.getCurrencyPair());
        try {
            LakeBTCOrderResponse newOrder = null;
            switch (limitOrder.getType()) {
                case BID:
                    newOrder = btcLakeBTC.placeBuyOrder(signatureCreator, LakeBTCUtil.getNonce(),
                            //unit price, amount, currency concatenated by commas
                            new LakeBTCBuyOrderRequest(String.format("\"%s,%s,%s\"", limitOrder.getLimitPrice(), limitOrder.getTradableAmount().toString(), pair)));
                    break;
                case ASK:
                    newOrder = btcLakeBTC.placeSellOrder(signatureCreator, LakeBTCUtil.getNonce(),
                            //unit price, amount, currency concatenated by commas
                            new LakeBTCSellOrderRequest(String.format("\"%s,%s,%s\"", limitOrder.getLimitPrice(), limitOrder.getTradableAmount().toString(), pair)));
                    break;
            }
            return newOrder;
        } catch (IOException e) {
            throw new ExchangeException("LakeBTC returned an error: " + e.getMessage());
        }
    }

    public LakeBTCCancelResponse cancelLakeBTCOrder(String orderId) throws IOException {
        try {
            return btcLakeBTC.cancelOrder(signatureCreator, LakeBTCUtil.getNonce(), new LakeBTCCancelRequest(orderId));
        } catch (Exception e) {
            throw new ExchangeException("LakeBTC returned an error: " + e.getMessage());
        }
    }

    public LakeBTCTradeResponse[] getLakeBTCTradeHistory(long timestamp) throws IOException {

        try {
            return btcLakeBTC.pastTrades(signatureCreator, LakeBTCUtil.getNonce(), new LakeBTCTradesRequest(String.valueOf(timestamp)));
        } catch (IOException e) {
            throw new ExchangeException("LakeBTC returned an error: " + e.getMessage());
        }
    }


    public LakeBTCOrdersResponse[] getLakeBTCOrders() throws IOException {
        return btcLakeBTC.getOrders(signatureCreator, LakeBTCUtil.getNonce(), new LakeBTCOrdersRequest());
    }
}
