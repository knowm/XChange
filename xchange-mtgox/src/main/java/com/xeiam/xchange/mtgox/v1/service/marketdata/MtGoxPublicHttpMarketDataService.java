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
package com.xeiam.xchange.mtgox.v1.service.marketdata;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.MarketDataService;
import com.xeiam.xchange.dto.marketdata.Tick;
import com.xeiam.xchange.mtgox.v1.MtGoxProperties;
import com.xeiam.xchange.mtgox.v1.service.marketdata.dto.MtGoxTicker;
import com.xeiam.xchange.utils.HttpUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 *
 */
public class MtGoxPublicHttpMarketDataService implements MarketDataService, CachedDataSession {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(MtGoxPublicHttpMarketDataService.class);

  @Override
  public Tick getTick(String symbol) {

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
      throw new ExchangeException("Problem generating ticker (JSON parsing)", e);
    } catch (JsonMappingException e) {
      throw new ExchangeException("Problem generating ticker (JSON mapping)", e);
    } catch (IOException e) {
      throw new ExchangeException("Problem generating ticker (IO)", e);
    } catch (NumberFormatException e) {
      throw new ExchangeException("Problem generating ticker (number formatting)", e);
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
  public Tick getMarketDepth(String symbol) {
    return null;
  }

  @Override
  public Tick getTrades(String symbol) {
    return null;
  }

  @Override
  public Tick getMarketFullDepth(String symbol) {
    return null;
  }

  @Override
  public Tick getCancelledTrades(String symbol) {
    return null;
  }

  @Override
  public Collection<Tick> getLatestMarketData() {
    return null;
  }

  @Override
  public Collection<Tick> getHistoricalMarketData(DateTime validFrom, DateTime validTo) {
    return null;
  }
}
