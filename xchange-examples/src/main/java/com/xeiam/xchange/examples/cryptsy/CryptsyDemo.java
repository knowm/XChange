/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.cryptsy;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.cryptsy.CryptsyExchange;
import com.xeiam.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyAccountServiceRaw;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyMarketDataServiceRaw;
import com.xeiam.xchange.cryptsy.service.polling.CryptsyTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * This demo shows ALL the functions currently implemented in the Cryptsy Exchange.
 * 
 * Note that requestDepositAddress is commented out, as too many requests on this method seem to lead to 
 * Cryptsy blocking that method for the specific account.
 * 
 * All methods require authentication - replace API and Secret keys below to run. The current set is defunct
 */

/**
 * @author ObsessiveOrange
 */
public class CryptsyDemo {
  
  public static void main(String[] args) throws IOException, ExchangeException, NotAvailableFromExchangeException,
      NotYetImplementedForExchangeException, InterruptedException {
  
    ExchangeSpecification exSpec = new ExchangeSpecification(CryptsyExchange.class);
    exSpec.setApiKey("9fb95ad332d882cb54f462e23ee34843099bd240");
    exSpec.setSecretKey("f19473bbb4ab38087165812434951cf89e0479c84ef19a974d780c64f18b7031a1360c3ff7bc67b9");
    
    Exchange cryptsyExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    PollingAccountService accountService = cryptsyExchange.getPollingAccountService();
    PollingMarketDataService marketDataService = cryptsyExchange.getPollingMarketDataService();
    PollingTradeService tradeService = cryptsyExchange.getPollingTradeService();
    
    generic(accountService, marketDataService, tradeService);
    raw((CryptsyAccountServiceRaw) accountService, (CryptsyMarketDataServiceRaw) marketDataService, (CryptsyTradeServiceRaw) tradeService);
  }
  
  private static void generic(PollingAccountService accountService, PollingMarketDataService marketDataService, PollingTradeService tradeService)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException, InterruptedException {
  
    System.out.println("\nAccountInfo:\n" + accountService.getAccountInfo());
    Thread.sleep(500);
    
    // System.out.println("\nrequestDepositAddress:\n" + accountService.requestDepositAddress("BTC"));
    // Thread.sleep(500);
    
    System.out.println("\ngetOrderBook:\n" + marketDataService.getOrderBook(CurrencyPair.LTC_BTC));
    Thread.sleep(500);
    
    System.out.println("\ngetTicker:\n" + marketDataService.getTicker(CurrencyPair.LTC_BTC));
    Thread.sleep(500);
    
    System.out.println("\ngetTrades:\n" + marketDataService.getTrades(CurrencyPair.LTC_BTC));
    Thread.sleep(500);
    
    String orderID =
        tradeService.placeLimitOrder(new LimitOrder(OrderType.BID, new BigDecimal("50000"), CurrencyPair.WDC_BTC, null, null, new BigDecimal(
            "0.00000001")));
    System.out.println("\nPlaceOrder:\n" + orderID);
    Thread.sleep(500);
    
    System.out.println("\nPlaceOrder:\n"
        + tradeService.placeLimitOrder(new LimitOrder(OrderType.BID, new BigDecimal("50000"), CurrencyPair.NMC_BTC, null, null, new BigDecimal(
            "0.00000001"))));
    Thread.sleep(500);
    
    System.out.println("\nPlaceOrder:\n"
        + tradeService.placeLimitOrder(new LimitOrder(OrderType.BID, new BigDecimal("50000"), CurrencyPair.DOGE_BTC, null, null, new BigDecimal(
            "0.00000001"))));
    Thread.sleep(500);
    
    System.out.println("\nPlaceOrder:\n"
        + tradeService.placeLimitOrder(new LimitOrder(OrderType.BID, new BigDecimal("50000"), CurrencyPair.LTC_BTC, null, null, new BigDecimal(
            "0.00000001"))));
    Thread.sleep(500);
    
    System.out.println("\nOpenOrders:\n" + tradeService.getOpenOrders());
    Thread.sleep(500);
    
    System.out.println("\nCancelOrder:\n" + tradeService.cancelOrder(orderID));
    Thread.sleep(500);
    
    System.out.println("\nOpenOrders:\n" + tradeService.getOpenOrders());
    Thread.sleep(500);
    
    System.out.println("\nTradeHistory:\n" + tradeService.getTradeHistory());
    Thread.sleep(500);
  }
  
  private static void
      raw(CryptsyAccountServiceRaw accountService, CryptsyMarketDataServiceRaw marketDataService, CryptsyTradeServiceRaw tradeService)
          throws IOException, InterruptedException {
  
    /*
     * All the raw methods are listed here:
     * *
     * *Account
     * getCryptsyAccountInfo()
     * getCryptsyTransactions()
     * generateNewCryptsyDepositAddress(Integer, String)
     * getCurrentCryptsyDepositAddresses()
     * makeCryptsyWithdrawal(String, BigDecimal)
     * checkTransfers()
     * getWalletStatus()
     * *
     * *MarketData
     * getCryptsyOrderBook(int)
     * getCryptsyTrades(int)
     * getCryptsyMarkets()
     * *
     * *Trade
     * getCryptsySingleMarketTradeHistory(int, int...)
     * getCryptsyTradeHistory(Date, Date)
     * getCryptsySingleMarketOpenOrders(int)
     * getCryptsyOpenOrders()
     * placeCryptsyLimitOrder(int, CryptsyOrderType, BigDecimal, BigDecimal)
     * cancelSingleCryptsyLimitOrder(int)
     * cancelMarketCryptsyLimitOrders(int)
     * cancelAllCryptsyLimitOrders()
     * calculateCryptsyFees(CryptsyOrderType, BigDecimal, BigDecimal)
     */
    
    System.out.println("\nAccountInfo:\n" + accountService.getCryptsyAccountInfo());
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
    
    System.out.println("\ngetTrades:\n" + marketDataService.getCryptsyTrades(3));
    Thread.sleep(500);
    
    System.out.println("\ngetMarkets:\n" + marketDataService.getCryptsyMarkets());
    Thread.sleep(500);
    
    int orderID =
        tradeService.placeCryptsyLimitOrder(3, CryptsyOrderType.Buy, new BigDecimal("50000"), new BigDecimal("0.00000001")).getReturnValue();
    System.out.println("\nPlaceOrder:\n" + orderID);
    Thread.sleep(500);
    
    System.out.println("\nPlaceOrder:\n"
        + tradeService.placeCryptsyLimitOrder(14, CryptsyOrderType.Buy, new BigDecimal("45000"), new BigDecimal("0.00000003")));
    Thread.sleep(500);
    
    System.out.println("\nPlaceOrder:\n"
        + tradeService.placeCryptsyLimitOrder(3, CryptsyOrderType.Buy, new BigDecimal("26100"), new BigDecimal("0.00000001")));
    Thread.sleep(500);
    
    System.out.println("\nPlaceOrder:\n"
        + tradeService.placeCryptsyLimitOrder(14, CryptsyOrderType.Buy, new BigDecimal("52000"), new BigDecimal("0.00000015")));
    Thread.sleep(500);
    
    System.out.println("\nOpenOrders:\n" + tradeService.getCryptsyOpenOrders());
    Thread.sleep(500);
    
    System.out.println("\nCheckMarket-SpecificOpenOrders:\n" + tradeService.getCryptsySingleMarketOpenOrders(14));
    Thread.sleep(500);
    
    System.out.println("\nCheckMarket-SpecificOpenOrders:\n" + tradeService.getCryptsySingleMarketOpenOrders(3));
    Thread.sleep(500);
    
    System.out.println("\nCancelLimitOrder:\n" + tradeService.cancelSingleCryptsyLimitOrder(orderID));
    Thread.sleep(500);
    
    System.out.println("\nCancelMarket-SpecificOrders:\n" + tradeService.cancelMarketCryptsyLimitOrders(14));
    Thread.sleep(500);
    
    System.out.println("\nCancelAllOrders:\n" + tradeService.cancelAllCryptsyLimitOrders());
    Thread.sleep(500);
    
    System.out.println("\nOpenOrders:\n" + tradeService.getCryptsyOpenOrders());
    Thread.sleep(500);
    
    System.out.println("\nSingleMarketTradeHistory:\n" + tradeService.getCryptsySingleMarketTradeHistory(14));
    Thread.sleep(500);
    
    System.out.println("\nTradeHistory:\n" + tradeService.getCryptsyTradeHistory(new Date(0), new Date()));
    Thread.sleep(500);
    
    System.out.println("\nCalculateFees:\n"
        + tradeService.calculateCryptsyFees(CryptsyOrderType.Buy, new BigDecimal("500"), new BigDecimal("0.15182")));
    Thread.sleep(500);
    
    System.out.println("\nOpenOrders:\n" + tradeService.getCryptsyOpenOrders());
    Thread.sleep(500);
  }
}
