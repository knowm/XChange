/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.examples.mtgox.v2.service.trade.streaming;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.examples.mtgox.v2.MtGoxV2ExamplesUtils;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v2.dto.account.streaming.MtGoxWalletUpdate;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOpenOrder;
import com.xeiam.xchange.mtgox.v2.dto.trade.streaming.MtGoxOrderCanceled;
import com.xeiam.xchange.mtgox.v2.dto.trade.streaming.MtGoxTradeLag;
import com.xeiam.xchange.mtgox.v2.service.streaming.MtGoxStreamingConfiguration;
import com.xeiam.xchange.mtgox.v2.service.streaming.SocketMessageFactory;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * Demonstrate streaming account and trade data from the MtGox Websocket API
 */
public class MtGoxWebSocketTradeDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    MtGoxWebSocketTradeDemo streamingTickerDemo = new MtGoxWebSocketTradeDemo();
    streamingTickerDemo.start();
  }

  public void start() throws ExecutionException, InterruptedException {

    // Use the default MtGox settings
    Exchange mtGoxExchange = MtGoxV2ExamplesUtils.createExchange();

    ExchangeStreamingConfiguration exchangeStreamingConfiguration = new MtGoxStreamingConfiguration(10, 10000, 60000, false, null);

    // Interested in the public streaming market data feed (no authentication)
    StreamingExchangeService streamingExchangeService = mtGoxExchange.getStreamingExchangeService(exchangeStreamingConfiguration);

    // Open the connections to the exchange
    streamingExchangeService.connect();

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<?> mtGoxMarketDataFuture = executorService.submit(new TradeDataRunnable(streamingExchangeService, mtGoxExchange));

    // the thread waits here until the Runnable is done.
    mtGoxMarketDataFuture.get();

    executorService.shutdown();

    // Disconnect and exit
    System.out.println(Thread.currentThread().getName() + ": Disconnecting...");
    streamingExchangeService.disconnect();
  }

  /**
   * Encapsulates some market data monitoring behavior
   */
  class TradeDataRunnable implements Runnable {

    private final StreamingExchangeService streamingExchangeService;
    private final Exchange exchange;

    /**
     * S Constructor
     * 
     * @param streamingExchangeService
     * @param exchange
     */
    public TradeDataRunnable(StreamingExchangeService streamingExchangeService, Exchange exchange) {

      this.streamingExchangeService = streamingExchangeService;
      this.exchange = exchange;
    }

    @Override
    public void run() {

      SocketMessageFactory socketMsgFactory = new SocketMessageFactory(exchange.getExchangeSpecification().getApiKey(), exchange.getExchangeSpecification().getSecretKey());

      try {
        while (true) {

          ExchangeEvent exchangeEvent = streamingExchangeService.getNextEvent();
          switch (exchangeEvent.getEventType()) {
          case CONNECT:

            // subscribe to "lag" channel "85174711-be64-4de1-b783-0628995d7914"
            // streamingExchangeService.send(socketMsgFactory.subscribeWithType("lag"));

            // subscribe to idKey
            streamingExchangeService.send(socketMsgFactory.idKey());
            // subscribe to private orders
            streamingExchangeService.send(socketMsgFactory.privateOrders());
            // subscribe to private info
            streamingExchangeService.send(socketMsgFactory.privateInfo());

            // LimitOrder: "I want to sell 0.01 BTC at $600"
            // streamingExchangeService.send(socketMsgFactory.addOrder(Order.OrderType.ASK, MoneyUtils.parseMoney("USD", 600f), new BigDecimal(0.01)));

            // LimitOrder: "I want to buy 10 BTC at $5"
            // streamingExchangeService.send(socketMsgFactory.addOrder(Order.OrderType.BID, MoneyUtils.parseMoney("USD", 5f), new BigDecimal(10)));

            // cancel order
            // streamingExchangeService.send(socketMsgFactory.cancelOrder("${oid}"));

            break;

          case ACCOUNT_INFO:
            MtGoxAccountInfo accountInfo = (MtGoxAccountInfo) exchangeEvent.getPayload();
            System.out.println("ACCOUNT INFO: " + accountInfo + " from: " + exchangeEvent.getData().toString());
            break;

          case USER_ORDERS_LIST:
            MtGoxOpenOrder[] orders = (MtGoxOpenOrder[]) exchangeEvent.getPayload();
            if (orders == null) {
              System.out.println("No orders for this user");
            }
            else {
              final int len = orders.length;
              for (int i = 0; i < len; i++) {
                System.out.println("USER ORDERS LIST (" + i + "): " + orders[i]);
              }
            }
            break;

          case PRIVATE_ID_KEY:
            String keyId = (String) exchangeEvent.getPayload();
            String msgToSend = socketMsgFactory.subscribeWithKey(keyId);
            streamingExchangeService.send(msgToSend);
            System.out.println("ID KEY: " + keyId);
            break;

          case TRADE_LAG:
            MtGoxTradeLag lag = (MtGoxTradeLag) exchangeEvent.getPayload();
            System.out.println("TRADE LAG: " + lag.toStringShort());
            break;

          case TRADE:
            System.out.println("TRADE! " + exchangeEvent.getData().toString());
            break;

          case TICKER:
            System.out.println("TICKER! " + exchangeEvent.getData().toString());
            break;

          case DEPTH:
            System.out.println("DEPTH! " + exchangeEvent.getData().toString());
            break;

          // only occurs when order placed via streaming API
          case USER_ORDER_ADDED:
            String orderAdded = (String) exchangeEvent.getPayload();
            System.out.println("ADDED USER ORDER: " + orderAdded);
            break;

          // only occurs when order placed via streaming API
          case USER_ORDER_CANCELED:
            MtGoxOrderCanceled orderCanceled = (MtGoxOrderCanceled) exchangeEvent.getPayload();
            System.out.println("CANCELED USER ORDER: " + orderCanceled + "\nfrom: " + exchangeEvent.getData());
            break;

          case USER_WALLET_UPDATE:
            MtGoxWalletUpdate walletUpdate = (MtGoxWalletUpdate) exchangeEvent.getPayload();
            System.out.println("USER WALLET UPDATE: " + walletUpdate + "\nfrom: " + exchangeEvent.getData());
            break;

          case USER_ORDER:
            MtGoxOpenOrder order = (MtGoxOpenOrder) exchangeEvent.getPayload();
            System.out.println("USER ORDER: " + order + "\nfrom: " + exchangeEvent.getData());
            break;

          case MESSAGE:
            System.out.println("MSG not parsed :(");
            break;

          default:
            break;

          }

        }

      } catch (InterruptedException e) {
        System.out.println("ERROR in Runnable!!!");
        return;
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

    }
  }
}
