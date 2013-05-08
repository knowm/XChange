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
package com.xeiam.xchange.examples.mtgox.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.mtgox.v1.MtGoxExchange;
import com.xeiam.xchange.mtgox.v2.streaming.MtGoxStreamingConfiguration;
import com.xeiam.xchange.mtgox.v2.streaming.MtGoxWebsocketMarketDataService;
import com.xeiam.xchange.mtgox.v2.streaming.SocketMsgFactory;
import com.xeiam.xchange.mtgox.v2.streaming.dto.*;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Demonstrate streaming market data from the MtGox Websocket API
 * <p/>
 * Note: requesting certain "channels" or specific currencies does not work. I believe this is the fault of MtGox and not XChange
 */
public class MtGoxWebSocketDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MtGoxWebSocketDemo streamingTickerDemo = new MtGoxWebSocketDemo();
        streamingTickerDemo.start();
    }

    public void start() throws ExecutionException, InterruptedException {

        // Use the default MtGox settings
        Exchange mtGoxExchange = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());

        // Configure BTC/USD ticker stream for MtGox
        ExchangeStreamingConfiguration btcusdConfiguration = new MtGoxStreamingConfiguration(10, 10000, Currencies.BTC, Currencies.USD);

        // Interested in the public streaming market data feed (no authentication)
        StreamingExchangeService btcusdStreamingMarketDataService =
                new MtGoxWebsocketMarketDataService(mtGoxExchange.getExchangeSpecification(), (MtGoxStreamingConfiguration) btcusdConfiguration);

        // Open the connections to the exchange
        btcusdStreamingMarketDataService.connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> mtGoxMarketDataFuture = executorService.submit(new MarketDataRunnable(btcusdStreamingMarketDataService));

        // the thread waits here until the Runnable is done.
        mtGoxMarketDataFuture.get();

        executorService.shutdown();

        // Disconnect and exit
        System.out.println(Thread.currentThread().getName() + ": Disconnecting...");
        btcusdStreamingMarketDataService.disconnect();
    }

    /**
     * Encapsulates some market data monitoring behavior
     */
    class MarketDataRunnable implements Runnable {

        private final StreamingExchangeService streamingExchangeService;

        /**
         * Constructor
         *
         * @param streamingExchangeService
         */
        public MarketDataRunnable(StreamingExchangeService streamingExchangeService) {

            this.streamingExchangeService = streamingExchangeService;
        }

        @Override
        public void run() {
            // Put ("api_key", "secret")
            SocketMsgFactory socketMsgFactory = new SocketMsgFactory("", "");

            String oid = "";

            try {
                while (true) {

                    ExchangeEvent exchangeEvent = streamingExchangeService.getNextEvent();
                    switch (exchangeEvent.getEventType()) {
                        case CONNECT:
                            // unsubscribe to "default" channels
                            streamingExchangeService.send(socketMsgFactory.unsubscribeToChannel("dbf1dee9-4f2e-4a08-8cb7-748919a71b21"));

                            streamingExchangeService.send(socketMsgFactory.unsubscribeToChannel("d5f06780-30a8-4a48-a2f8-7ed181b4a13f"));
                            streamingExchangeService.send(socketMsgFactory.unsubscribeToChannel("24e67e0d-1cad-4cc0-9e7a-f8523ef460fe"));

                            //streamingExchangeService.send(socketMsgFactory.subscribeWithType("lag"));
                            streamingExchangeService.send(socketMsgFactory.idKey());
                            streamingExchangeService.send(socketMsgFactory.privateOrders());
                            streamingExchangeService.send(socketMsgFactory.privateInfo());

                            break;

                        case ACCOUNT_INFO:
                            MtGoxAccountInfo accountInfo = (MtGoxAccountInfo) exchangeEvent.getPayload();
                            System.out.println("ACCOUNT INFO: " + accountInfo + " from: " + exchangeEvent.getData().toString());
                            break;

                        case USER_ORDERS_LIST:
                            MtGoxOpenOrder[] orders = (MtGoxOpenOrder[]) exchangeEvent.getPayload();
                            if ( orders == null ) {
                                System.out.println("No orders for this user");
                            } else {
                                final int len = orders.length;
                                for ( int i = 0; i < len ; i++ ) {
                                    System.out.println("USER ORDERS LIST (" + i + "): " + orders[i]);
                                }
                            }
                            break;

                        case PRIVATE_ID_KEY:
                            String keyId = (String) exchangeEvent.getPayload();
                            String msgToSend = socketMsgFactory.subscribeWithKey(keyId);
                            streamingExchangeService.send(msgToSend);

                            //LimitOrder: "I want to sell 0.01 BTC at $600"
                            //streamingExchangeService.send(
                              //      socketMsgFactory.addOrder(Order.OrderType.ASK,
                                //            MoneyUtils.parseMoney("USD", 600f),
                                  //          new BigDecimal(0.01)));


                            //LimitOrder: "I want to buy 10 BTC at $5"
                            //streamingExchangeService.send(
                              //      socketMsgFactory.addOrder(Order.OrderType.BID,
                                //            MoneyUtils.parseMoney("USD", 5f), new BigDecimal(10)));

                            break;

                        case TRADE_LAG:
                            MtGoxTradeLag lag = (MtGoxTradeLag) exchangeEvent.getPayload();
                            System.out.println("TRADE LAG: " + lag + "\nfrom: " + exchangeEvent.getData());
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

                        case USER_ORDER_ADDED:
                            String orderAdded = (String) exchangeEvent.getPayload();
                            System.out.println("ADDED USER ORDER: " + orderAdded);
                            oid = orderAdded;
                            break;

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

                            if ( order.getOid().equals(oid) ) {
                                if ( order.getStatus().equals("open") ) {
                                    streamingExchangeService.send(socketMsgFactory.cancelOrder(oid));
                                    oid = "";
                                }

                            }

                            break;

                        case MESSAGE:
                            System.out.println("MSG not parsed :(");
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
