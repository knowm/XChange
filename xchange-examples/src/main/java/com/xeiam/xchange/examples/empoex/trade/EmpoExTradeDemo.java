package com.xeiam.xchange.examples.empoex.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.empoex.service.polling.EmpoExTradeServiceRaw;
import com.xeiam.xchange.examples.empoex.EmpoExDemoUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class EmpoExTradeDemo {

  static CurrencyPair currencyPair = new CurrencyPair("VPN", "BTC");

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Exchange empoex = EmpoExDemoUtils.getExchange();
    PollingTradeService tradeService = empoex.getPollingTradeService();

    generic(tradeService);
    raw((EmpoExTradeServiceRaw) tradeService);
  }

  public static void generic(PollingTradeService tradeService) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    System.out.println(tradeService.getOpenOrders());
  }

  public static void raw(EmpoExTradeServiceRaw tradeService) throws IOException {

    System.out.println(tradeService.getEmpoExOpenOrders());
  }

}
