package org.knowm.xchange.coinsetter.service.streaming.event;

import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterLevel;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterLevels;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterPair;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTicker;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTrade;
import org.knowm.xchange.coinsetter.dto.trade.CoinsetterOrderStatus;

/**
 * An abstract adapter class for receiving Coinsetter exchange events. The methods in this class are empty. This class exists as convenience for
 * creating listener objects.
 */
public abstract class CoinsetterExchangeAdapter implements CoinsetterExchangeListener {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onLast(CoinsetterTrade last) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onTicker(CoinsetterTicker ticker) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onDepth(CoinsetterPair[] depth) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onLevels(CoinsetterLevels levels) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onLevel(CoinsetterLevel level) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onOrderStatus(CoinsetterOrderStatus orderStatus) {

  }

}
