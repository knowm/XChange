package org.knowm.xchange.bitcoincharts;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Matija Mazi
 */
public class BitcoinChartsFactory {

  /**
   * private Constructor
   */
  private BitcoinChartsFactory() {

  }

  /**
   * Get a RestProxy for BitcoinCharts
   *
   * @return the Rest Proxy
   */
  public static BitcoinCharts createInstance() {

    return RestProxyFactory.createProxy(BitcoinCharts.class, "http://api.bitcoincharts.com");
  }
}
