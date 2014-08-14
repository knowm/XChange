package com.xeiam.xchange.examples.vaultofsatoshi.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiExchange;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTrade;
import com.xeiam.xchange.vaultofsatoshi.service.polling.VaultOfSatoshiMarketDataServiceRaw;

/**
 * Demonstrate requesting Trades at Vault of Satoshi
 *
 * @author timmmolter
 */
public class VoSTradesDemo {

  static Exchange vos = ExchangeFactory.INSTANCE.createExchange(VaultOfSatoshiExchange.class.getName());

  // Interested in the public polling market data feed (no authentication)
  static PollingMarketDataService marketDataService = vos.getPollingMarketDataService();

  public static void main(String[] args) throws IOException {

    generic();
    raw();
  }

  public static void generic() throws IOException {

    // Get the latest trade data for BTC/CNY
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CAD);

    System.out.println(trades.toString());
    System.out.println("NumTrades=" + trades.getTrades().size());
    System.out.println("lastID=" + trades.getlastID());

    // Get the latest trade data for BTC/CNY
    trades = marketDataService.getTrades(CurrencyPair.BTC_CAD, 100, 350105);

    System.out.println(trades.toString());
    System.out.println("NumTrades=" + trades.getTrades().size());

  }

  public static void raw() throws IOException {

    // Get the latest trade data for BTC/CAD
    VosTrade[] trades = ((VaultOfSatoshiMarketDataServiceRaw) marketDataService).getVosTrades(CurrencyPair.BTC_CAD);

    for (VosTrade vosTrade : trades) {
      System.out.println(vosTrade.toString());
    }
    System.out.println("NumTrades=" + trades.length);

  }
}
