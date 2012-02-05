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
package com.xeiam.xchange;

import java.util.Set;

import com.xeiam.xchange.interfaces.PrivateExchangeSession;

/**
 *
 */
public abstract class ExchangeProxy {

  /**
   * Available symbols
   */
  private final Set<String> exchangeSymbols;

  protected boolean isConnected = false;

  /**
   * Constructor
   */
  public ExchangeProxy() {
    super();
    exchangeSymbols = getExchangeSymbols();
    if (!(this instanceof PrivateExchangeSession)) {
      // need to connect
      isConnected = true;
    }
  }

  public boolean connect(AuthenticationOptions authenticationOptions) {

    if (this instanceof PrivateExchangeSession) {
      // need to connect
      isConnected = ((PrivateExchangeSession) this).connect(authenticationOptions);
    } else {
      isConnected = true;
    }
    return isConnected;
  }

  public boolean disconnect(AuthenticationOptions authenticationOptions) {

    if (this instanceof PrivateExchangeSession) {
      // need to connect
      isConnected = ((PrivateExchangeSession) this).disconnect(authenticationOptions);
    } else {
      isConnected = true;
    }
    return isConnected;
  }

  /**
   * abstract methods
   */
  public abstract Set<String> getExchangeSymbols();

}
