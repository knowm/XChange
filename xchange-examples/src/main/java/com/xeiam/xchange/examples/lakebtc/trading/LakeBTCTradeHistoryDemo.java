package com.xeiam.xchange.examples.lakebtc.trading;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import com.xeiam.xchange.lakebtc.dto.trade.LakeBTCTradeResponse;
import com.xeiam.xchange.lakebtc.service.polling.LakeBTCTradeServiceRaw;
import com.xeiam.xchange.service.polling.PollingTradeService;

import java.io.IOException;

/**
 * Created by Cristi on 12/22/2014.
 */
public class LakeBTCTradeHistoryDemo {


    public static void main(String[] args) throws IOException {
        Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
        generic(lakebtcExchange);
        raw(lakebtcExchange);
    }


    private static void generic(Exchange lakebtcExchange) throws IOException {

        PollingTradeService tradeService = lakebtcExchange.getPollingTradeService();

        // Get the trade history
        Trades trades = tradeService.getTradeHistory();
        System.out.println(trades);

        trades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
        System.out.println(trades);

    }

    private static void raw(Exchange lakeBtcExchange) throws IOException {
        LakeBTCTradeServiceRaw tradeService = (LakeBTCTradeServiceRaw) lakeBtcExchange.getPollingTradeService();

        // Get the trade history
        LakeBTCTradeResponse[] trades = tradeService.getLakeBTCTradeHistory(0);
        System.out.println(trades);

    }
}
