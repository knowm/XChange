package org.known.xchange.dsx;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.TimestampIncrementingNonceFactory;
import org.known.xchange.dsx.dto.meta.DSXMetaData;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Mikhail Wall
 */
public class DSXExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new TimestampIncrementingNonceFactory();
  private DSXMetaData dsxMetaData;

  @Override
  protected void initServices() {


  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return null;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    return null;
  }
}
