package com.xeiam.xchange;

/**
 *  <p>Parameter Object to provide the following to {@link Session}:</p>
 *  <ul>
 *  <li>Transfer of state to the Session</li>
 *  </ul>
 *
 * @since 0.0.1
 *         
 */
public class SessionOptions {

  private final String exchangeProviderClassName;
  
  /**
   * <p>Provide the mandatory information to the XChange Session.<br/>
   *
   * @param exchangeProviderClassName The exchange provider class name (e.g. "org.example.DemoProvider")
   */
  public SessionOptions(String exchangeProviderClassName) {
    this.exchangeProviderClassName=exchangeProviderClassName;
  }

  public String getExchangeProviderClassName() {
    return exchangeProviderClassName;
  }
}
