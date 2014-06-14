/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.service.streaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeException;

/**
 * <p>
 * Abstract base class to provide the following to XChange clients:
 * </p>
 * <ul>
 * <li>Simple extension point for a {@link Runnable} designed for use with an ExecutorService</li>
 * </ul>
 */
public abstract class ExchangeEventListener {

  private final Logger log = LoggerFactory.getLogger(ExchangeEventListener.class);

  /**
   * Constructor
   */
  public ExchangeEventListener() {

  }

  /**
   * <p>
   * Client code is expected to implement this in a manner specific to their own application
   * </p>
   * 
   * @param event The exchange event containing the information
   */
  public abstract void handleEvent(ExchangeEvent event) throws ExchangeException;
}
