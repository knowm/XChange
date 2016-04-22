package org.knowm.xchange.examples.cryptsy;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptsy.CryptsyExchange;
import org.knowm.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbook;
import org.knowm.xchange.cryptsy.service.polling.CryptsyAccountServiceRaw;
import org.knowm.xchange.cryptsy.service.polling.CryptsyMarketDataServiceRaw;
import org.knowm.xchange.cryptsy.service.polling.CryptsyPublicMarketDataService;
import org.knowm.xchange.cryptsy.service.polling.CryptsyTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.polling.account.PollingAccountService;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;
import org.knowm.xchange.service.polling.trade.PollingTradeService;

/**
 * This demo shows ALL the functions currently implemented in the Cryptsy Exchange.
 *
 * Note that requestDepositAddress is commented out, as too many requests on this method seem to lead to
 * Cryptsy blocking that method for the specific account.
 *
 * This is running using a test account (Slitil53x/Jeic5OHahx), since all methods require authentication.
 */

/**
 * @author ObsessiveOrange
 */
public class CryptsyDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    ExchangeSpecification exSpec = new ExchangeSpecification(CryptsyExchange.class);

    exSpec.setApiKey("0feb4d7258aa828a76355e32940de94d3298bed2");
    exSpec.setSecretKey("b03caa2f83e6a42cd2780f0a245f020302eb4fe2a63d268135c927c4d88d265be8e4e559e9f3c812");

    Exchange cryptsyExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    PollingAccountService accountService = cryptsyExchange.getPollingAccountService();
    PollingMarketDataService marketDataService = cryptsyExchange.getPollingMarketDataService();
    PollingTradeService tradeService = cryptsyExchange.getPollingTradeService();

    generic(accountService, marketDataService, tradeService);
    raw((CryptsyAccountServiceRaw) accountService, (CryptsyMarketDataServiceRaw) marketDataService, (CryptsyTradeServiceRaw) tradeService);
    publicAPI((CryptsyExchange) cryptsyExchange);
  }

  private static void generic(PollingAccountService accountService, PollingMarketDataService marketDataService, PollingTradeService tradeService)
      throws IOException, InterruptedException {

    System.out.println("\nOpenOrders:\n" + tradeService.getOpenOrders());
    Thread.sleep(500);

    System.out.println("\nWallet:\n" + accountService.getAccountInfo());
    Thread.sleep(500);

    // System.out.println("\nrequestDepositAddress:\n" + accountService.requestDepositAddress("BTC"));
    // Thread.sleep(500);

    System.out.println("\ngetOrderBook:\n" + marketDataService.getOrderBook(new CurrencyPair("NET", "BTC")));
    Thread.sleep(500);

    System.out.println("\ngetTicker:\n" + marketDataService.getTicker(new CurrencyPair("NET", "BTC")));
    Thread.sleep(500);

    System.out.println("\ngetTrades:\n" + marketDataService.getTrades(new CurrencyPair("NET", "BTC")));
    Thread.sleep(500);

    String orderID = tradeService.placeLimitOrder(
        new LimitOrder(OrderType.BID, new BigDecimal("50.000000"), new CurrencyPair("NET", "BTC"), null, null, new BigDecimal("0.00000001")));
    System.out.println("\nPlaceOrder:\n" + orderID);
    Thread.sleep(500);

    System.out.println("\nOpenOrders:\n" + tradeService.getOpenOrders());
    Thread.sleep(500);

    System.out.println("\nCancelOrder:\n" + tradeService.cancelOrder(orderID));
    Thread.sleep(500);

    System.out.println("\nOpenOrders:\n" + tradeService.getOpenOrders());
    Thread.sleep(500);

    System.out.println("\nTradeHistory:\n" + tradeService.getTradeHistory(tradeService.createTradeHistoryParams()));
    Thread.sleep(500);

  }

  private static void raw(CryptsyAccountServiceRaw accountService, CryptsyMarketDataServiceRaw marketDataService, CryptsyTradeServiceRaw tradeService)
      throws IOException, InterruptedException {

    /*
     * All the raw methods are listed here: * *Account getCryptsyAccountInfo() getCryptsyTransactions() generateNewCryptsyDepositAddress(Integer,
     * String) getCurrentCryptsyDepositAddresses() makeCryptsyWithdrawal(String, BigDecimal) checkTransfers() getWalletStatus() * *MarketData
     * getCryptsyOrderBook(int) getCryptsyTrades(int) getCryptsyMarkets() * *Trade getCryptsySingleMarketTradeHistory(int, int...)
     * getCryptsyTradeHistory(Date, Date) getCryptsySingleMarketOpenOrders(int) getCryptsyOpenOrders() placeCryptsyLimitOrder(int, CryptsyOrderType,
     * BigDecimal, BigDecimal) cancelSingleCryptsyLimitOrder(int) cancelMarketCryptsyLimitOrders(int) cancelAllCryptsyLimitOrders()
     * calculateCryptsyFees(CryptsyOrderType, BigDecimal, BigDecimal)
     */

    System.out.println("\nOpenOrders:\n" + tradeService.getCryptsyOpenOrders());
    Thread.sleep(500);

    System.out.println("\nWallet:\n" + accountService.getCryptsyAccountInfo());
    Thread.sleep(500);

    // System.out.println("\nrequestNewDepositAddress:\n" + accountService.generateNewCryptsyDepositAddress(null, "BTC"));
    // Thread.sleep(500);

    System.out.println("\ngetCurrentDepositAddresses:\n" + accountService.getCurrentCryptsyDepositAddresses());
    Thread.sleep(500);

    System.out.println("\ngetTransactions:\n" + accountService.getCryptsyTransactions());
    Thread.sleep(500);

    System.out.println("\ngetTransferHistory:\n" + accountService.getTransferHistory());
    Thread.sleep(500);

    System.out.println("\ngetOrderBook:\n" + marketDataService.getCryptsyOrderBook(3));
    Thread.sleep(500);

    System.out.println("\ngetTrades:\n" + marketDataService.getCryptsyTrades(134));
    Thread.sleep(500);

    System.out.println("\ngetMarkets:\n" + marketDataService.getCryptsyMarkets());
    Thread.sleep(500);

    int orderID = tradeService.placeCryptsyLimitOrder(134, CryptsyOrderType.Buy, new BigDecimal("50.000000"), new BigDecimal("0.00000001"))
        .getReturnValue();
    System.out.println("\nPlaceOrder:\n" + orderID);
    Thread.sleep(500);

    System.out.println("\nPlaceOrder:\n"
        + tradeService.placeCryptsyLimitOrder(134, CryptsyOrderType.Buy, new BigDecimal("50.100000"), new BigDecimal("0.00000001")).getReturnValue());
    Thread.sleep(500);

    System.out.println("\nPlaceOrder:\n"
        + tradeService.placeCryptsyLimitOrder(134, CryptsyOrderType.Buy, new BigDecimal("50.010000"), new BigDecimal("0.00000001")).getReturnValue());
    Thread.sleep(500);

    System.out.println("\nPlaceOrder:\n"
        + tradeService.placeCryptsyLimitOrder(134, CryptsyOrderType.Buy, new BigDecimal("50.001000"), new BigDecimal("0.00000001")).getReturnValue());
    Thread.sleep(500);

    System.out.println("\nOpenOrders:\n" + tradeService.getCryptsyOpenOrders());
    Thread.sleep(500);

    System.out.println("\nCheckMarket-SpecificOpenOrders:\n" + tradeService.getCryptsySingleMarketOpenOrders(134));
    Thread.sleep(500);

    System.out.println("\nCancelLimitOrder:\n" + tradeService.cancelSingleCryptsyLimitOrder(orderID));
    Thread.sleep(500);

    System.out.println("\nCheckMarket-SpecificOpenOrders:\n" + tradeService.getCryptsySingleMarketOpenOrders(134));
    Thread.sleep(500);

    System.out.println("\nCancelMarket-SpecificOrders:\n" + tradeService.cancelMarketCryptsyLimitOrders(134));
    Thread.sleep(500);

    System.out.println("\nOpenOrders:\n" + tradeService.getCryptsyOpenOrders());
    Thread.sleep(500);

    System.out.println("\nCancelAllOrders:\n" + tradeService.cancelAllCryptsyLimitOrders());
    Thread.sleep(500);

    System.out.println("\nOpenOrders:\n" + tradeService.getCryptsyOpenOrders());
    Thread.sleep(500);

    System.out.println("\nSingleMarketTradeHistory:\n" + tradeService.getCryptsySingleMarketTradeHistory(134));
    Thread.sleep(500);

    System.out.println("\nTradeHistory:\n" + tradeService.getCryptsyTradeHistory(new Date(0), new Date()));
    Thread.sleep(500);

    System.out
        .println("\nCalculateFees:\n" + tradeService.calculateCryptsyFees(CryptsyOrderType.Buy, new BigDecimal("500"), new BigDecimal("0.15182")));
    Thread.sleep(500);

    System.out.println("\nOpenOrders:\n" + tradeService.getCryptsyOpenOrders());
    Thread.sleep(500);
  }

  private static void publicAPI(CryptsyExchange cryptsyExchange) throws ExchangeException, IOException, InterruptedException {

    CryptsyPublicMarketDataService publicMarketDataService = (CryptsyPublicMarketDataService) cryptsyExchange.getPollingPublicMarketDataService();
    final int DOGE_LTC_MARKET_ID = 135;
    Map<Integer, CryptsyPublicMarketData> singleMarketData = publicMarketDataService.getCryptsyMarketData(DOGE_LTC_MARKET_ID);
    System.out.println(singleMarketData);

    Map<Integer, CryptsyPublicOrderbook> singleOrderBook = publicMarketDataService.getCryptsyOrderBook(DOGE_LTC_MARKET_ID);
    System.out.println(singleOrderBook);

    System.out.println("\ngetOrderBook:\n" + publicMarketDataService.getOrderBook(new CurrencyPair("NET", "BTC")));
    Thread.sleep(500);

    System.out.println("\ngetTicker:\n" + publicMarketDataService.getTicker(new CurrencyPair("NET", "BTC")));
    Thread.sleep(500);

    System.out.println("\ngetTrades:\n" + publicMarketDataService.getTrades(new CurrencyPair("NET", "BTC")));
    Thread.sleep(500);

  }
}
