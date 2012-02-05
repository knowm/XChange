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

/**
 *  
 * <p>
 * Parameter Object to provide the following to {@link Session}:
 * </p>
 *  
 * <ul>
 *  
 * <li>Transfer of state to the Session</li>  
 * </ul>
 * 
 * @since 0.0.1  
 */
public class AuthenticationOptions {

  private final String exchangeProviderClassName;
  private final String userName = null;
  private final String password = null;

  /**
   * <p>
   * Provide the mandatory information to the XChange Session.<br/>
   * 
   * @param exchangeProviderClassName The exchange provider class name (e.g. "org.example.DemoProvider")
   */
  public AuthenticationOptions(String exchangeProviderClassName) {
    this.exchangeProviderClassName = exchangeProviderClassName;
  }

  public String getExchangeProviderClassName() {
    return exchangeProviderClassName;
  }

  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

}
