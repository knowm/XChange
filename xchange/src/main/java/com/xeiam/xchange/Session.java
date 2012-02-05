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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.exceptions.NotConnectedException;

/**
 *  
 * <p>
 * Session to provide the following to consuming applications:
 * </p>
 *  
 * <ul>
 *  
 * <li>Entry point to the exchange</li>  
 * </ul>
 * 
 * @since 0.0.1  
 */
public class Session {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(Session.class);

  private final AuthenticationOptions authenticationOptions;

  private ExchangeProxy exchangeProxy;

  public Session(AuthenticationOptions authenticationOptions) {
    this.authenticationOptions = authenticationOptions;
  }

  /**
   * <p>
   * Starts the session using the underlying exchange provider
   * </p>
   * 
   * @throws NotConnectedException If something goes wrong
   */
  public void start() throws NotConnectedException {

    log.debug("Starting Session");

    // Attempt to create an instance of the exchange provider
    String exchangeProviderClassName = authenticationOptions.getExchangeProviderClassName();
    try {

      // Attempt to locate the exchange provider on the classpath
      Class exchangeProviderClass = Class.forName(exchangeProviderClassName);

      // Test that the class implements ExchangeProvider
      if (ExchangeProxy.class.isAssignableFrom(exchangeProviderClass)) {
        // Instantiate through the default constructor
        exchangeProxy = (ExchangeProxy) exchangeProviderClass.newInstance();
      } else {
        throw new NotConnectedException("Class '" + exchangeProviderClassName + "' does not implement ExchangeProvider");
      }
    } catch (ClassNotFoundException e) {
      throw new NotConnectedException("Problem starting exchange provider (class not found)", e);
    } catch (InstantiationException e) {
      throw new NotConnectedException("Problem starting exchange provider (instantiation)", e);
    } catch (IllegalAccessException e) {
      throw new NotConnectedException("Problem starting exchange provider (illegal access)", e);
    }

    // Must be OK to be here
    log.debug("Instantiated '{}' OK. Starting provider...", exchangeProviderClassName);

    exchangeProxy.connect(authenticationOptions);

  }

  public void stop() {

    log.debug("Stopping Session");

    if (exchangeProxy != null) {
      exchangeProxy.disconnect(authenticationOptions);
    }

    log.debug("Stopped OK");
  }

}
