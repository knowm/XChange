package com.xeiam.xchange.examples.hitbtc.trade;

import java.io.IOException;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v3.service.polling.BTCETradeService;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.examples.hitbtc.HitbtcExampleUtils;
import com.xeiam.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class HitbtcTradingDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = HitbtcExampleUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    generic(tradeService);
    raw((HitbtcTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    BTCETradeService.BTCETradeHistoryParams params = new BTCETradeService.BTCETradeHistoryParams();
    //params.setCurrencyPair(CurrencyPair.BTC_EUR);
    UserTrades accountInfo = tradeService.getTradeHistory(params);
    System.out.println(accountInfo);
  }

  private static void raw(HitbtcTradeServiceRaw tradeService) throws IOException {

    HitbtcOwnTrade[] trades = tradeService.getTradeHistoryRaw(0, 100, null);

    System.out.println(Arrays.toString(trades));
  }
}
