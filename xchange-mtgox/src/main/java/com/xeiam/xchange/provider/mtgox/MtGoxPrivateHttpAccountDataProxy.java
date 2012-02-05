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
package com.xeiam.xchange.provider.mtgox;

import java.util.Set;

import com.xeiam.xchange.AuthenticationOptions;
import com.xeiam.xchange.SynchronousMarketDataProxy;
import com.xeiam.xchange.interfaces.CachedDataSession;
import com.xeiam.xchange.interfaces.PrivateExchangeSession;
import com.xeiam.xchange.marketdata.Tick;

public class MtGoxPrivateHttpAccountDataProxy extends SynchronousMarketDataProxy implements CachedDataSession, PrivateExchangeSession {

  @Override
  public boolean connect(AuthenticationOptions authenticationOptions) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean disconnect(AuthenticationOptions authenticationOptions) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Tick getExchangeTick(String symbol) {
    // TODO Auto-generated method stub
    return null;
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
