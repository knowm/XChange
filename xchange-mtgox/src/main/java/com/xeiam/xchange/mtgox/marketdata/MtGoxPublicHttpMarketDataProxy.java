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
package com.xeiam.xchange.mtgox.marketdata;

import java.io.IOException;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.exceptions.GenericExchangeException;
import com.xeiam.xchange.interfaces.CachedDataSession;
import com.xeiam.xchange.marketdata.SynchronousMarketDataProxy;
import com.xeiam.xchange.marketdata.Tick;
import com.xeiam.xchange.mtgox.MtGoxProperties;
import com.xeiam.xchange.utils.HttpUtils;

/**
 *
 */
public class MtGoxPublicHttpMarketDataProxy extends SynchronousMarketDataProxy implements CachedDataSession {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(MtGoxPublicHttpMarketDataProxy.class);

  @Override
  public Tick getExchangeTick(String symbol) {

    Tick ticker = null;

    // request data
    String tickJSON = HttpUtils.getJSON("https://mtgox.com/api/1/" + symbol + "/public/ticker");
    log.debug(tickJSON);

    // parse JSON
    MtGoxTicker mtGoxTicker = null;
    try {
      ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
      mtGoxTicker = mapper.readValue(tickJSON, MtGoxTicker.class);
      ticker = new Tick();
      // extract last
      ticker.setLast(Integer.parseInt(mtGoxTicker.getReturn().getLast_orig().getValue_int()));
      ticker.setVolume(Long.parseLong(mtGoxTicker.getReturn().getVol().getValue_int()));
    } catch (JsonParseException e) {
      throw new GenericExchangeException("Problem generating ticker (JSON parsing)", e);
    } catch (JsonMappingException e) {
      throw new GenericExchangeException("Problem generating ticker (JSON mapping)", e);
    } catch (IOException e) {
      throw new GenericExchangeException("Problem generating ticker (IO)", e);
    } catch (NumberFormatException e) {
      throw new GenericExchangeException("Problem generating ticker (number formatting)", e);
    }

    // return
    return ticker;

  }

  /**
   * <p>
   * According to Mt.Gox API docs (https://en.bitcoin.it/wiki/MtGox/API), data is cached for 10 seconds.
   * </p>
   */
  @Override
  public int getRefreshRate() {
    return MtGoxProperties.REFRESH_RATE;
  }

  @Override
  public Set<String> getExchangeSymbols() {
    return MtGoxProperties.MT_GOX_SYMBOLS;
  }

  @Override
  public Tick getExchangeDepth(String symbol) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Tick getExchangeTrades(String symbol) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Tick getExchangeFullDepth(String symbol) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Tick getExchangeCancelledTrades(String symbol) {
    // TODO Auto-generated method stub
    return null;
  }
}
