package com.xeiam.xchange;

/**
 * <p>
 * Interface to provide the following the API
 * </p>
 * <ul>
 * <li>Access to parameters associated with caching of data by exchanges</li>
 * </ul>
 */
public interface CachedDataSession {

  /**
   * Some exchanges cache data, and only refresh it at a certain rate.
   * 
   * @return The maximum allowable refresh rate in seconds
   */
  public long getRefreshRate();

}
