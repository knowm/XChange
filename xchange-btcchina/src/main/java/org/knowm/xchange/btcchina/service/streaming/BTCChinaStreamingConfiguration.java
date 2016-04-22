package org.knowm.xchange.btcchina.service.streaming;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;

public class BTCChinaStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final CurrencyPair[] marketDataCurrencyPairs;
  private final CurrencyPair[] grouporderCurrencyPairs;
  private final CurrencyPair[] orderFeedCurrencyPairs;

  /**
   * Indicates to subscribe account balance changes.
   */
  private final boolean subscribeAccountInfo;

  public BTCChinaStreamingConfiguration() {

    this(true, true, true, CurrencyPair.BTC_CNY, CurrencyPair.LTC_CNY, CurrencyPair.LTC_BTC);
  }

  /**
   * @param subscribeMarketData Subscribe with 'subscribe' method and listen on "ticker" and "trade" method to received and process market data
   *        including ticker data and trade data.
   * @param subscribeOrderFeed Subscribe with 'private' method and listen on "order" method to receive and process your own order data. Whenever the
   *        status of your own orders change, the server will push corresponding orders to your client side. This requires authentication with your
   *        access key and secret key, which is used the same way as our trade API.
   * @param currencyPairs could be one or more of {@link CurrencyPair#BTC_CNY}, {@link CurrencyPair#LTC_CNY}, {@link CurrencyPair#LTC_BTC}.
   */
  public BTCChinaStreamingConfiguration(boolean subscribeMarktData, boolean subscribeOrderFeed, CurrencyPair... currencyPairs) {

    this(subscribeMarktData, subscribeOrderFeed, true, currencyPairs);
  }

  public BTCChinaStreamingConfiguration(boolean subscribeMarktData, boolean subscribeOrderFeed, boolean subscribeAccountInfo,
      CurrencyPair... currencyPairs) {

    this(subscribeMarktData, true, subscribeOrderFeed, subscribeAccountInfo);
  }

  /**
   * @since <a href= "http://btcchina.org/websocket-api-market-data-documentation-en#websocket_api_v122" >WebSocket API v1.2.2</a>
   */
  public BTCChinaStreamingConfiguration(boolean subscribeMarktData, boolean subscribeGrouporder, boolean subscribeOrderFeed,
      boolean subscribeAccountInfo, CurrencyPair... currencyPairs) {

    this(subscribeMarktData ? currencyPairs : null, subscribeGrouporder ? currencyPairs : null, subscribeOrderFeed ? currencyPairs : null,
        subscribeAccountInfo);
  }

  public BTCChinaStreamingConfiguration(CurrencyPair[] marketDataCurrencyPairs, CurrencyPair[] orderFeedCurrencyPairs) {

    this(marketDataCurrencyPairs, orderFeedCurrencyPairs, true);
  }

  public BTCChinaStreamingConfiguration(CurrencyPair[] marketDataCurrencyPairs, CurrencyPair[] orderFeedCurrencyPairs, boolean subscribeAccountInfo) {

    this(marketDataCurrencyPairs, null, orderFeedCurrencyPairs, subscribeAccountInfo);
  }

  /**
   * @since <a href= "http://btcchina.org/websocket-api-market-data-documentation-en#websocket_api_v122" >WebSocket API v1.2.2</a>
   */
  public BTCChinaStreamingConfiguration(CurrencyPair[] marketDataCurrencyPairs, CurrencyPair[] grouporderCurrencyPairs,
      CurrencyPair[] orderFeedCurrencyPairs, boolean subscribeAccountInfo) {

    this.marketDataCurrencyPairs = marketDataCurrencyPairs == null ? new CurrencyPair[0] : marketDataCurrencyPairs;
    this.grouporderCurrencyPairs = grouporderCurrencyPairs == null ? new CurrencyPair[0] : grouporderCurrencyPairs;
    this.orderFeedCurrencyPairs = orderFeedCurrencyPairs == null ? new CurrencyPair[0] : orderFeedCurrencyPairs;
    this.subscribeAccountInfo = subscribeAccountInfo;
  }

  @Override
  public int getMaxReconnectAttempts() {

    return 0;
  }

  @Override
  public int getReconnectWaitTimeInMs() {

    return 0;
  }

  @Override
  public int getTimeoutInMs() {

    return 0;
  }

  @Override
  public boolean isEncryptedChannel() {

    return false;
  }

  @Override
  public boolean keepAlive() {

    return false;
  }

  public boolean isSubscribeAccountInfo() {

    return subscribeAccountInfo;
  }

  public CurrencyPair[] getMarketDataCurrencyPairs() {

    return marketDataCurrencyPairs;
  }

  /**
   * @since <a href= "http://btcchina.org/websocket-api-market-data-documentation-en#websocket_api_v122" >WebSocket API v1.2.2</a>
   */
  public CurrencyPair[] getGrouporderCurrencyPairs() {

    return grouporderCurrencyPairs;
  }

  public CurrencyPair[] getOrderFeedCurrencyPairs() {

    return orderFeedCurrencyPairs;
  }

}
