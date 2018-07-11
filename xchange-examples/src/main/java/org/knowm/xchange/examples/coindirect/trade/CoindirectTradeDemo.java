package org.knowm.xchange.examples.coindirect.trade;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectExchange;
import org.knowm.xchange.coindirect.service.CoindirectAccountService;
import org.knowm.xchange.coindirect.service.CoindirectMarketDataService;
import org.knowm.xchange.coindirect.service.CoindirectTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.coindirect.CoindirectDemoUtils;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;

import java.io.IOException;

public class CoindirectTradeDemo {
    public static void main(String[] args) throws IOException {
        Exchange exchange = CoindirectDemoUtils.createExchange();

        /* create a data service from the exchange */
        TradeService tradeService = exchange.getTradeService();

        generic(exchange, tradeService);
        raw((CoindirectExchange) exchange, (CoindirectTradeService) tradeService);
    }

    private static void generic(Exchange exchange, TradeService tradeService) throws IOException {

        OpenOrders openOrders = tradeService.getOpenOrders();

        System.out.println("Got open orders "+openOrders);

        UserTrades userTrades = tradeService.getTradeHistory(null);

        System.out.println("Got user trades "+userTrades);

//        String depositAddress = accountService.requestDepositAddress(Currency.BTC);
//        System.out.println("Deposit Address: " + depositAddress);

        // String transactionHash = accountService.withdrawFunds(new BigDecimal(".01"), "XXX");
        // System.out.println("Bitcoin blockchain transaction hash: " + transactionHash);
    }

    public static void raw(CoindirectExchange exchange, CoindirectTradeService tradeService) throws IOException {



    }
}
