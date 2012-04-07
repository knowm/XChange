/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.SymbolPair;
import com.xeiam.xchange.service.marketdata.MarketDataService;
import com.xeiam.xchange.service.marketdata.Ticker;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;
import com.xeiam.xchange.service.trade.AccountInfo;
import com.xeiam.xchange.service.trade.TradeService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connecting to Intersango BTC exchange</li>
 * <li>Retrieving market data</li>
 * <li>Retrieving basic trade data</li>
 * <li>Retrieving authenticated account data</li>
 * </ul>
 *
 * @since 0.0.1  
 */
public class IntersangoExchangeDemo {

  /**
   * @param args [0] is the API key provided by Intersango
   */
  public static void main(String[] args) {

    Map<String, Object> params = new HashMap<String, Object>();
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification("com.xeiam.xchange.intersango.v0_1.IntersangoExchange");
    exchangeSpecification.setUri("https://intersango.com");
    exchangeSpecification.setVersion("v0.1");
    exchangeSpecification.setApiKey(args[0]);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    // Demonstrate the public market data service
    demoMarketDataService(exchange);

    // Demonstrate the private account data service
    demoAccountService(exchange);

    // Demonstrate the streaming market data service
    demoStreamingMarketDataService(exchange);

  }

  /**
   * Demonstrates how to connect to the MarketDataService for MtGox
   *
   * @param exchange The exchange
   */
  private static void demoMarketDataService(Exchange exchange) {

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(SymbolPair.BTC_USD);

    // Perform a crude conversion from the internal representation
    double btcusd = (double) ticker.getLast() / 10000;

    System.out.printf("Current exchange rate for BTC to USD: %.4f%n", btcusd);
  }

  /**
   * Demonstrates how to connect to the AccountService for Intersango
   *
   * @param exchange The exchange
   */
  private static void demoAccountService(Exchange exchange) {

    // Interested in the private data feed (requires authentication)
    TradeService accountService = exchange.getTradeService();

    // Get the latest ticker data showing BTC to USD
    AccountInfo accountInfo = accountService.getAccountInfo();

    System.out.printf("Account info: %s%n", accountInfo);
  }

  /**
   * Demonstrates how to connect to the AccountService for Intersango
   *
   * @param exchange The exchange
   */
  private static void demoStreamingMarketDataService(Exchange exchange) {

    // Interested in the private data feed (requires authentication)
    StreamingMarketDataService streamingMarketDataService = exchange.getStreamingMarketDataService();

    // TODO Fix this
    streamingMarketDataService.addListener(null);

  }

}
