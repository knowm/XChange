package com.xeiam.xchange.mtgox.v1.demo;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.dto.marketdata.Tick;
import com.xeiam.xchange.mtgox.v1.service.marketdata.MtGoxPublicHttpMarketDataService;

/**
 * Demonstrates how to query market data from Mt Gox using their public Http market data API
 */
public class MtGoxPublicHttpMarketDataDemo {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(MtGoxPublicHttpMarketDataDemo.class);

  /**
   * @param args Not required
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {

    MtGoxPublicHttpMarketDataService mtGoxPublicHttpMarketDataService = new MtGoxPublicHttpMarketDataService();
    for (int i = 0; i < 10; i++) {
      Tick tick = mtGoxPublicHttpMarketDataService.getTick("BTCUSD");
      log.debug("tick: " + tick.toString());
      // TODO Re-instate these (not sure where they went)
//      int last = mtGoxPublicHttpMarketDataService.getLast("BTCUSD");
//      log.debug("last: " + last);
//      long volume = mtGoxPublicHttpMarketDataService.getVolume("BTCUSD");
//      log.debug("volume: " + volume);
//      Thread.sleep(2000);
    }

  }

}
