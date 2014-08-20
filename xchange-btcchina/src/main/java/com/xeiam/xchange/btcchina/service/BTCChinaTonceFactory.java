package com.xeiam.xchange.btcchina.service;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.btcchina.BTCChinaUtils;

public class BTCChinaTonceFactory implements SynchronizedValueFactory<Long> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Long createValue() {

    return BTCChinaUtils.getNonce();
  }

}
