/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.mtgox.v1.service.marketdata.streaming;

/**
 * Test combining streaming event queues at MtGox
 */
public class StreamingAllDemo {

  public static void main(String[] args) {

    StreamingAllDemo allDemo = new StreamingAllDemo();
    allDemo.start();
  }

  public void start() {

    // // Use the default MtGox settings
    // Exchange mtGoxExchange = ExchangeFactory.INSTANCE.createExchange(MtGoxExchange.class.getName());
    //
    // // Interested in the public streaming market data feed (no authentication)
    // StreamingMarketDataService streamingMarketDataService = mtGoxExchange.getStreamingMarketDataService();
    //
    // // Get blocking queue that receives exchange event data (this starts the event processing as well)
    // BlockingQueue<ExchangeEvent> tradeQueue = streamingMarketDataService.getEventQueue(Currencies.BTC, Currencies.USD, ExchangeEventType.TRADE);
    // BlockingQueue<ExchangeEvent> depthQueue = streamingMarketDataService.getEventQueue(Currencies.BTC, Currencies.USD, ExchangeEventType.DEPTH);
    //
    // // Note: Recommend not combining ticker events into common queue due to the higher rate of production
    // // BlockingQueue<ExchangeEvent> tickerQueue = MG_Market_Stream.getEventQueue(Currencies.BTC, Currencies.USD, ExchangeEventType.TICKER);
    //
    // BlockingQueue<ExchangeEvent> eventQueue = new LinkedBlockingQueue<ExchangeEvent>(2048);
    //
    // for (int i = 0; i < 10; i++) {
    //
    // try {
    //
    // // Fill exchange events first
    // while (!tradeQueue.isEmpty()) {
    // ExchangeEvent tradeEvent = tradeQueue.take();
    // eventQueue.put(tradeEvent);
    // System.out.println("Exchange event: " + tradeEvent.getEventType().name() + ", " + tradeEvent.getData());
    //
    // }
    // while (!depthQueue.isEmpty()) {
    // ExchangeEvent depthEvent = depthQueue.take();
    // eventQueue.put(depthEvent);
    // System.out.println("Exchange event: " + depthEvent.getEventType().name() + ", " + depthEvent.getData());
    //
    // }
    // // while (!tickerQueue.isEmpty()) {
    // // ExchangeEvent tickerEvent = tickerQueue.take();
    // // eventQueue.put(tickerEvent);
    // // System.out.println("Exchange event: " + tickerEvent.getEventType().name() + ", " + tickerEvent.getData());
    // //
    // // }
    //
    // // Process exchange events
    // while (!eventQueue.isEmpty()) {
    // ExchangeEvent event = eventQueue.take();
    //
    // // if (event.getEventType().equals(ExchangeEventType.TICKER)) {
    // // ProcessTick(event.getPayload());
    // // }
    // if (event.getEventType().equals(ExchangeEventType.TRADE)) {
    //
    // ProcessTrade(event.getPayload());
    // }
    // if (event.getEventType().equals(ExchangeEventType.DEPTH)) {
    // ProcessDepth(event.getPayload());
    // }
    // }
    //
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // }
  }

  private static void ProcessTrade(Object payload) {

    System.out.println(payload.toString());
  }

  private static void ProcessDepth(Object payload) {

    System.out.println(payload.toString());
  }

}
